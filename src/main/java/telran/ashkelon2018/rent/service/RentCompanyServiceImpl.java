package telran.ashkelon2018.rent.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.ashkelon2018.rent.config.RentCompanyConfig;
import telran.ashkelon2018.rent.dao.CarRepository;
import telran.ashkelon2018.rent.dao.DriverRepository;
import telran.ashkelon2018.rent.dao.ModelRepository;
import telran.ashkelon2018.rent.dao.RentRecordRepository;
import telran.ashkelon2018.rent.domain.Car;
import telran.ashkelon2018.rent.domain.Driver;
import telran.ashkelon2018.rent.domain.Model;
import telran.ashkelon2018.rent.domain.RentRecord;
import telran.ashkelon2018.rent.dto.CarDto;
import telran.ashkelon2018.rent.dto.CarResponseDto;
import telran.ashkelon2018.rent.dto.DateClearDto;
import telran.ashkelon2018.rent.dto.DriverDto;
import telran.ashkelon2018.rent.dto.ModelDto;
import telran.ashkelon2018.rent.dto.RentRecordDto;
import telran.ashkelon2018.rent.dto.RentRecordEditDto;
import telran.ashkelon2018.rent.dto.State;
import telran.ashkelon2018.rent.exceptions.CarExistsException;
import telran.ashkelon2018.rent.exceptions.CarInUseException;
import telran.ashkelon2018.rent.exceptions.CarNotRentedException;
import telran.ashkelon2018.rent.exceptions.DriverExistsException;
import telran.ashkelon2018.rent.exceptions.ModelExistsException;
import telran.ashkelon2018.rent.exceptions.NoCarException;
import telran.ashkelon2018.rent.exceptions.NoDriverException;
import telran.ashkelon2018.rent.exceptions.NoModelException;
import telran.ashkelon2018.rent.exceptions.WrongDateException;


@Service
public class RentCompanyServiceImpl implements RentCompanyService {

	@Autowired
	CarRepository carRepository;
	@Autowired
	DriverRepository driverRepository;
	@Autowired
	ModelRepository modelRepository;
	@Autowired
	RentRecordRepository rrRepository;
	@Autowired
	RentCompanyConfig config;

	@Override
	@Transactional
	public boolean addModel(ModelDto modelDto) {
		Model model = new Model(modelDto.getModelName(), modelDto.getGasTank(), modelDto.getCompany(), modelDto.getCountry(), modelDto.getPriceDay());
		if(modelRepository.existsById(modelDto.getModelName())) {
			throw new ModelExistsException("Model exists");
		}
		modelRepository.save(model);
		return true;
	}

	@Override
	@Transactional
	public boolean addCar(CarDto carDto) {
		Model model = modelRepository.findById(carDto.getModelName()).orElse(null);
		if (model == null) {
			throw new NoModelException("Model doesn't exist");
		}
		String regNumber = carDto.getRegNumber();
		Car car = new Car(regNumber, carDto.getColor(), model);
		if (carRepository.existsById(regNumber)) {
			throw new CarExistsException("Car exists");
		}
		carRepository.save(car);
		return true;
	}

	@Override
	@Transactional
	public boolean addDriver(DriverDto driverDto) {
		Long licenseId = driverDto.getLicenseId();
		Driver driver = new Driver(licenseId, driverDto.getName(), driverDto.getBirthYear(), driverDto.getPhone());
		if(driverRepository.existsById(licenseId)) {
			throw new DriverExistsException("Driver exists");
		}
		driverRepository.save(driver);
		return true;
	}

	@Override
	public ModelDto getModel(String modelName) {
		Model model = modelRepository.findById(modelName).orElse(null);
		if(model == null) {
			return null;
		}
		return ModelDto.builder()
				.modelName(modelName)
				.company(model.getCompany())
				.country(model.getCountry())
				.gasTank(model.getGasTank())
				.priceDay(model.getPriceDay())
				.build();
	}

	@Override
	public CarResponseDto getCar(String regNumber) {
		Car car = carRepository.findById(regNumber).orElse(null);
		if(car==null) {
			return null;
		}
		return convertToCarResponseDto(car);
	}
	
	private CarResponseDto convertToCarResponseDto(Car car) {
		return CarResponseDto.builder()
		.regNumber(car.getRegNumber())
		.color(car.getColor())
		.state(car.getState())
		.modelName(car.getModel().getModelName())
		.build();
	}

	@Override
	public DriverDto getDriver(long licenseId) {
		Driver driver = driverRepository.findById(licenseId).orElse(null);
		if(driver==null) {
			return null;
		}
		return convertToDriverDto(driver);
	}

	private DriverDto convertToDriverDto(Driver driver) {
		return DriverDto.builder()
				.licenseId(driver.getLicenseId())
				.name(driver.getName())
				.birthYear(driver.getBirthYear())
				.phone(driver.getPhone())
				.build();
	}

	@Override
	@Transactional
	public boolean rentCar(RentRecordDto rentRecordDto) {
		String regNumber = rentRecordDto.getCarNumber();
		long licenseId = rentRecordDto.getLicenseId();
		if(!driverRepository.existsById(licenseId)) {
			throw new NoDriverException("Driver doesn't exist");	
		}
		Car car = carRepository.findById(regNumber).orElse(null);
		if(car == null || car.isFlRemoved()) {
			throw new NoCarException("Car doesn't exist");
		}
		if(car.isInUse()) {
			throw new CarInUseException("Car is in use");
		}
		RentRecord rr = new RentRecord(licenseId, regNumber, rentRecordDto.getRentDate(), rentRecordDto.getRentDays());
		rrRepository.save(rr);
		setCarInUse(car);
		return true;
	}

	private void setCarInUse(Car car) {
		boolean inUseCurrent = car.isInUse();
		car.setInUse(!inUseCurrent);
		carRepository.save(car);
		
	}

	@Override
	@Transactional
	public boolean returnCar(RentRecordEditDto rentRecordEditDto) {
		String regNumber = rentRecordEditDto.getCarNumber();
		long licenseId = rentRecordEditDto.getLicenseId();
		LocalDate returnDate = rentRecordEditDto.getReturnDate();
		int gasTankPercent = rentRecordEditDto.getGasTankPercent();
		int damages = rentRecordEditDto.getDamages();
		RentRecord rr = getAllRecords()
				.filter(r -> r.getRegNumber().equals(regNumber))
				.filter(r -> r.getLicenseId() == licenseId)
				.filter(r -> r.getReturnDate() == null)
				.findFirst()
				.orElse(null);
		if(checkReturn(rr, licenseId, returnDate)) {
			rr.setDamages(damages);
			rr.setGasTankPercent(gasTankPercent);
			rr.setReturnDate(rentRecordEditDto.getReturnDate());
			rr.setCost(countCost(rr, gasTankPercent, returnDate));
			rrRepository.save(rr);
			Car car = carRepository.findById(regNumber).get();
			setCarInUse(car);
			setCarState(car, damages);
			carRepository.save(car);
		}
		return true;
	}

	private void setCarState(Car car, int damages) {
		if (damages > 0 && damages <= 10) {
			car.setState(State.GOOD);
		}
		if (damages > 10) {
			car.setState(State.BAD);
		}
		if (damages > 30) {
			car.setFlRemoved(true);
		}
		
	}

	private double countCost(RentRecord rr, int gasTankPercent, LocalDate returnDate) {
		Car car = carRepository.findById(rr.getRegNumber()).get();
		Model model = car.getModel();
		int gasTank = model.getGasTank();
		int priceDay = model.getPriceDay();
		int returnDays = (int) ChronoUnit.DAYS.between(rr.getRentDate(), returnDate);
		int delayDays = 0;
		double normalCost = rr.getRentDays() * priceDay;
		//find out if the car was returned with delay
		if (returnDays > rr.getRentDays()) {
			delayDays = returnDays - rr.getRentDays();
		}
		//find out if the car was returned before the term
		if(returnDays < rr.getRentDays()) {
			normalCost = returnDays * priceDay;	
		}
		double additionalCost = (gasTank - gasTankPercent * gasTank / 100.) * config.getGasPrice()
				+ delayDays * (priceDay + config.getFinePercent() * priceDay / 100.);
		return normalCost + additionalCost;
	}

	private boolean checkReturn(RentRecord rr, long licenseId, LocalDate returnDate) {
		if (driverRepository.findById(licenseId) == null) {
			throw new NoDriverException("Driver doesn't exist");
		}
		if (rr == null) {
			throw new CarNotRentedException("Car is not rented");
		}
		if (returnDate.isBefore(rr.getRentDate())) {
			throw new WrongDateException();
		}
		return true;
	}

	@Override
	@Transactional
	public boolean removeCar(String carNumber) {
		Car car = carRepository.findById(carNumber).orElse(null);
		if(car == null) {
			throw new NoCarException("Car doesn't exist");
		}
		if(car.isInUse()) {
			throw new CarInUseException("Car is in use");
		}
		car.setFlRemoved(true);
		carRepository.save(car);
		return true;
	}

	@Override
	@Transactional
	public List<CarResponseDto> clear(DateClearDto dateClearDto) {
		LocalDate currentDate = dateClearDto.getCurrentDate();
		int days = dateClearDto.getDays();
		LocalDate onDate = currentDate.minusDays(days);
		List<Car> cars = getAllRecords()
				.filter(r -> r.getReturnDate().isBefore(onDate))
				.map(r -> carRepository.findById(r.getRegNumber()).get())
				.filter(c -> c.isFlRemoved())
				.collect(Collectors.toList());
		carRepository.deleteAll(cars);
		return cars.stream().map(this::convertToCarResponseDto).collect(Collectors.toList());
	}

	@Override
	public List<DriverDto> getCarDrivers(String carNumber) {
		Car car = carRepository.findById(carNumber).orElseThrow(NoCarException::new);
		return car.getRentRecords()
				.stream()
				.map(r -> driverRepository.findById(r.getLicenseId()).get())
				.map(this::convertToDriverDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<CarResponseDto> getDriversCars(long licenseId) {
		Driver driver = driverRepository.findById(licenseId).orElseThrow(NoDriverException::new);
		return driver.getRentRecords()
				.stream()
				.map(r -> carRepository.findById(r.getRegNumber()).get())
				.map(this::convertToCarResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly=true)
	public Stream<CarResponseDto> getAllCars() {
		return carRepository.findAll().stream().map(this::convertToCarResponseDto);
	}

	@Override
	@Transactional(readOnly=true)
	public Stream<DriverDto> getAllDrivers() {
		return driverRepository.findAll().stream().map(this::convertToDriverDto);
	}

	@Override
	@Transactional(readOnly=true)
	public Stream<RentRecord> getAllRecords() {
		return rrRepository.findAll().stream();
	}
	
	@Override
	public Iterable<String> getMostPopularModelNames() {
		return rrRepository.findMostPopularModelName().stream().limit(3).collect(Collectors.toList());
	}

	@Override
	public double getModelProfit(String modelName) {
		return rrRepository.findModelProfit(modelName);
	}

	@Override
	public Iterable<String> getMostProfitModelNames() {
		return rrRepository.findMostProfitModelNames();
	}

}
