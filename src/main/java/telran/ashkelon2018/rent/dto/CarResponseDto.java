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
public class CarResponseDto {
	private String regNumber;
	private String color;
	private State state;
	private String modelName;
	
}
