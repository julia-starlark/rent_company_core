package telran.ashkelon2018.rent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@Configuration
@ManagedResource
public class RentCompanyConfig {
	@Value("${finePercent}")
	int finePercent;
	@Value("${gasPrice}")
	int gasPrice;
	
	@ManagedAttribute
	public int getFinePercent() {
		return finePercent;
	}
	@ManagedAttribute
	public void setFinePercent(int finePercent) {
		this.finePercent = finePercent;
	}
	@ManagedAttribute
	public int getGasPrice() {
		return gasPrice;
	}
	@ManagedAttribute
	public void setGasPrice(int gasPrice) {
		this.gasPrice = gasPrice;
	}
}
