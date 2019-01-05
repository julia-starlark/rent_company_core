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
@EqualsAndHashCode(of = { "modelName" })
@Entity
public class Model implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	String modelName;
	int gasTank;
	String company;
	String country;
	@Setter
	int priceDay;
	@OneToMany(mappedBy = "model")
	Set<Car> cars;

	public Model(String modelName, int gasTank, String company, String country, int priceDay) {
		this.modelName = modelName;
		this.gasTank = gasTank;
		this.company = company;
		this.country = country;
		this.priceDay = priceDay;
	}

}
