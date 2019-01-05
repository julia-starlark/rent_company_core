package telran.ashkelon2018.rent.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of= {"licenseId"})
@Entity
public class Driver implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	long licenseId;
	String name;
	int birthYear;
	@Setter String phone;
	@OneToMany(mappedBy="licenseId")
	Set<RentRecord> rentRecords;
	
	public Driver(long licenseId, String name, int birthYear, String phone) {
		this.licenseId = licenseId;
		this.name = name;
		this.birthYear = birthYear;
		this.phone = phone;
	}
}
