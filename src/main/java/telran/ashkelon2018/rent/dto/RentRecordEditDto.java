package telran.ashkelon2018.rent.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class RentRecordEditDto {
	private long licenseId;
	private String carNumber;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate returnDate;
	private int gasTankPercent;
	private int damages;
}
