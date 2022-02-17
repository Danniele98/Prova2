package ModellodiClimate;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//import lombok.AllArgsConstructor;
//import lombok.Data;

@Data
@AllArgsConstructor
@Embeddable
@JsonIgnoreProperties(ignoreUnknown=true)
public class ClimateWeather {
	private String description;
	
	
	//Costruttore
	public ClimateWeather(String description) {
		super();
		this.description = description;
	}

	//Getter e setter per "description"
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
