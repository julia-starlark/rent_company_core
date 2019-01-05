package telran.ashkelon2018.rent.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.rent.domain.RentRecord;
import telran.ashkelon2018.rent.dto.CarDto;
import telran.ashkelon2018.rent.dto.CarResponseDto;
import telran.ashkelon2018.rent.dto.DateClearDto;
import telran.ashkelon2018.rent.dto.DriverDto;
import telran.ashkelon2018.rent.dto.ModelDto;
import telran.ashkelon2018.rent.dto.RentRecordDto;
import telran.ashkelon2018.rent.dto.RentRecordEditDto;
import telran.ashkelon2018.rent.service.RentCompanyService;

@RestController
public class CarsController {
	@Autowired
	RentCompanyService service;

	@PostMapping("/model")
	public boolean addModel(@RequestBody ModelDto modelDto) {
		return service.addModel(modelDto);
	}

	@PostMapping("/car")
	public boolean addCar(@RequestBody CarDto carDto) {
		return service.addCar(carDto);
	}

	@PostMapping("/driver")
	public boolean addDriver(@RequestBody DriverDto driverDto) {
		return service.addDriver(driverDto);
	}

	@GetMapping("/model/{modelName}")
	public ModelDto getModel(@PathVariable String modelName) {
		return service.getModel(modelName);
	}
	
	@GetMapping("/car/{carNumber}")
	public CarResponseDto getCar(@PathVariable String carNumber) {
		return service.getCar(carNumber);
	}

	@GetMapping("/driver/{licenseId}")
	public DriverDto getDriver(@PathVariable long licenseId) {
		return service.getDriver(licenseId);
	}

	@PostMapping("/record")
	public boolean rentCar(@RequestBody RentRecordDto rentRecordDto) {
		return service.rentCar(rentRecordDto);
	}

	@PutMapping("/record")
	public boolean returnCar(@RequestBody RentRecordEditDto rentRecordEditDto) {
		return service.returnCar(rentRecordEditDto);
	}

	@DeleteMapping("/car/{carNumber}")
	public boolean removeCar(@PathVariable String carNumber) {
		return service.removeCar(carNumber);
	}

	@DeleteMapping("/cars")
	public List<CarResponseDto> clear(@RequestBody DateClearDto dateClearDto){
		return service.clear(dateClearDto);
	}

	@GetMapping("/drivers/car/{carNumber}")
	public List<DriverDto> getCarDrivers(@PathVariable String carNumber){
		return service.getCarDrivers(carNumber);
	}

	@GetMapping("cars/driver/{licenseId}")
	public List<CarResponseDto> getDriversCars(@PathVariable long licenseId){
		return service.getDriversCars(licenseId);
	}

	@GetMapping("/cars")
	public Iterable<CarResponseDto> getAllCars(){
		return service.getAllCars().collect(Collectors.toList());
	}

	@GetMapping("/drivers")
	public Iterable<DriverDto> getAllDrivers(){
		return service.getAllDrivers().collect(Collectors.toList());
	}

	@GetMapping("/records")
	public Iterable<RentRecord> getAllRecords(){
		return service.getAllRecords().collect(Collectors.toList());
	}

	@GetMapping("/models/popularity")
	public Iterable<String> getMostPopularModelNames(){
		return service.getMostPopularModelNames();
	}

	@GetMapping("/model/{modelName}/profit")
	public double getModelProfit(@PathVariable String modelName) {
		return service.getModelProfit(modelName);
	}

	@GetMapping("/models/profit")
	public Iterable<String> getMostProfitModelNames(){
		return service.getMostProfitModelNames();
	}
}
