package telran.ashkelon2018.rent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarEditDto {
	private State state;
	private boolean inUse;
	private boolean flRemoved;
}
