package telran.ashkelon2018.rent.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.ashkelon2018.rent.domain.Model;

public interface ModelRepository extends JpaRepository<Model, String> {

}
