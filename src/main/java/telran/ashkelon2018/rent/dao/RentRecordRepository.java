package telran.ashkelon2018.rent.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.ashkelon2018.rent.domain.RentRecord;
import telran.ashkelon2018.rent.domain.RentRecordPK;

public interface RentRecordRepository extends JpaRepository<RentRecord, RentRecordPK> {

	@Query("select concat (m.modelName, '-', count(m.modelName)) from Car c join c.model m "
			+ "join c.rentRecords r group by m.modelName order by count(m.modelName) desc")
	List<String> findMostPopularModelName();

	@Query("select sum(r.cost) from RentRecord r join Car c on r.regNumber=c.regNumber join Model m on c.model = m where m.modelName = :modelName")
	double findModelProfit(@Param(value="modelName")String modelName);

	@Query("select concat (m.modelName, ' ', sum(r.cost)) from Model m join Car c on c.model = m "
			+ "join RentRecord r on r.regNumber=c.regNumber group by m.modelName order by sum(r.cost) desc")
	List<String> findMostProfitModelNames();
}
