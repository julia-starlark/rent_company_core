package telran.ashkelon2018.rent.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@NoArgsConstructor
public class CarNotRentedException extends RuntimeException {

	public CarNotRentedException(String string) {
	 super(string);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

}
