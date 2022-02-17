package ModellodiClimate;

import javax.persistence.Embedded;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="forecasts")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Climate {
	
	@Id
	Long id;
	String name;
	
	@Embedded
	ClimateMain main;
	
	@Embedded
	ClimateWeather weather;

	
	//Blocco getter e setter per "ID"
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	//Blocco getter e setter per "name"
	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	
	//Blocco getter e setter per "main"							//Metodo
	public ClimateMain getMain() {								//ritenuto
		return main;											//superfluo
	}
	
	public void setMain(ClimateMain main) {
		this.main = main;
	}
	

	//Blocco getter e setter per "ForecastWeather"
	public ClimateWeather getWeather() {
		return weather;
	}
	
	public void setWeather(ClimateWeather weather) {
		this.weather=weather;
	}

}