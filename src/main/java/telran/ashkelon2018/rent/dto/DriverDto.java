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
public class DriverDto {
	private long licenseId;
	private String name;
	private int birthYear;
	private String phone;
}
