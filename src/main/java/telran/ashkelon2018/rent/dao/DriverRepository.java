package telran.ashkelon2018.rent.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.ashkelon2018.rent.domain.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {

}
