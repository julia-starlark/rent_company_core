package telran.ashkelon2018.rent.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateClearDto {
	@JsonFormat(pattern="yyyy-MM-dd") 
	private LocalDate currentDate;
	private int days;
}
