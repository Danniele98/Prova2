package ModellodiClimate;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


//import lombok.AllArgsConstructor;
//import lombok.Data;

@Data
//@AllArgsConstructor
@Embeddable
@JsonIgnoreProperties(ignoreUnknown=true)
public class ClimateMain {

	double temp;
	double feels_like;
	double temp_min;
	double temp_max;
	
	//Costruttore
	public ClimateMain(double temp, double feels_like, double temp_min, double temp_max) {
		super();
		this.temp = temp;
		this.feels_like = feels_like;
		this.temp_min = temp_min;
		this.temp_max = temp_max;
	}
	
	//Blocco getter e setter per "temp"
	public double getTemp() {
		return temp;
	}
	public void setTemp(double temp) {
		this.temp = temp;
	}
	
	//Blocco getter e setter per "feels_like"
	public double getFeels_like() {
		return feels_like;
	}
	public void setFeels_like(double feels_like) {
		this.feels_like = feels_like;
	}
	
	//Blocco getter e setter per "temp_min"
	public double getTemp_min() {
		return temp_min;
	}
	public void setTemp_min(double temp_min) {
		this.temp_min = temp_min;
	}
	
	//Blocco getter e setter per "
	public double getTemp_max() {
		return temp_max;
	}
	public void setTemp_max(double temp_max) {
		this.temp_max = temp_max;
	}
	
}