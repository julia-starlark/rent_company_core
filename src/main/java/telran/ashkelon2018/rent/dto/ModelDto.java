package telran.ashkelon2018.rent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelDto {
	private String modelName;
	private int gasTank;
	private String company;
	private String country;
	private int priceDay;
}
