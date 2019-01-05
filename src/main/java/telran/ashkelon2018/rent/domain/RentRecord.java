package telran.ashkelon2018.rent.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = { "licenseId", "regNumber", "rentDate" })
@Entity
@Setter
@IdClass(RentRecordPK.class)
@Table(name="records")
public class RentRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	long licenseId;
	@Id
	String regNumber;
	@Id
	LocalDate rentDate;
	LocalDate returnDate;
	int gasTankPercent;
	int rentDays;
	double cost;
	int damages;

	public RentRecord(long licenseId, String regNumber, LocalDate rentDate, int rentDays) {
		this.licenseId = licenseId;
		this.regNumber = regNumber;
		this.rentDate = rentDate;
		this.rentDays = rentDays;
	}

}
