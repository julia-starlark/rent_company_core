package telran.ashkelon2018.rent.service;

import java.util.List;
import java.util.stream.Stream;

import telran.ashkelon2018.rent.domain.RentRecord;
import telran.ashkelon2018.rent.dto.CarDto;
import telran.ashkelon2018.rent.dto.CarResponseDto;
import telran.ashkelon2018.rent.dto.CarsReturnCode;
import telran.ashkelon2018.rent.dto.DateClearDto;
import telran.ashkelon2018.rent.dto.DriverDto;
import telran.ashkelon2018.rent.dto.ModelDto;
import telran.ashkelon2018.rent.dto.RentRecordDto;
import telran.ashkelon2018.rent.dto.RentRecordEditDto;

public interface RentCompanyService {
	boolean addModel(ModelDto modelDto);

	boolean addCar(CarDto carDto);

	boolean addDriver(DriverDto driverDto);

	ModelDto getModel(String modelName);

	CarResponseDto getCar(String carNumber);

	DriverDto getDriver(long licenseId);

	boolean rentCar(RentRecordDto rentRecordDto);

	boolean returnCar(RentRecordEditDto rentRecordEditDto);

	boolean removeCar(String carNumber);

	List<CarResponseDto> clear(DateClearDto dateClearDto);

	List<DriverDto> getCarDrivers(String carNumber);

	List<CarResponseDto> getDriversCars(long licenseId);

	Stream<CarResponseDto> getAllCars();

	Stream<DriverDto> getAllDrivers();

	Stream<RentRecord> getAllRecords();

	Iterable<String> getMostPopularModelNames();

	double getModelProfit(String modelName);

	Iterable<String> getMostProfitModelNames();
}
