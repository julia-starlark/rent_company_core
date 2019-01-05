package telran.ashkelon2018.rent.domain;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RentRecordPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	long licenseId;
	String regNumber;
	LocalDate rentDate;
}
