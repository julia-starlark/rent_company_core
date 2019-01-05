package telran.ashkelon2018.rent.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.ashkelon2018.rent.dto.State;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of= {"regNumber"})
@Entity
public class Car implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	String regNumber;
	String color;
	@Setter 
	State state;
	@ManyToOne
	Model model;
	@Setter 
	boolean inUse;
	@Setter 
	boolean flRemoved;
	@OneToMany(mappedBy="regNumber")
	Set<RentRecord> rentRecords;
	
	public Car(String regNumber, String color, Model model) {
		this.regNumber = regNumber;
		this.color = color;
		this.model = model;
		this.state = State.EXCELLENT;
	}
}
