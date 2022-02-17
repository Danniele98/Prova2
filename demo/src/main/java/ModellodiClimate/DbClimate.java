package ModellodiClimate;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="climates")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DbClimate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private String city_id;	
	private String name;	
	private String country;
	
//  @DateTimeFormat(pattern = "Y-m-d H:i:s") // yyyy-MM-dd			//Questo non l'ho commentato io
	@Column(name="climate_date", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
    private Date climate_date;
	

	//Dati numerici presenti nel file html (essi sono presenti nella voce "Main" del file in questione)
	private double temp;
	private double feels_like;
	private double temp_min;
	private double temp_max;
	//Dati stringa presenti nel file html (essi sono presenti nella voce "Weather" del file in questione)
	 private String description;
	 
	 //Blocco getter e setter per "id"
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	//Blocco getter e setter per "city_id"
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	
	//Blocco getter e setter per "name"
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//Blocco getter e setter per "country"
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	//Blocco getter e setter per "climate_date"
	public Date getClimate_date() {
		return climate_date;
	}
	public void setClimate_date(Date climate_date) {
		this.climate_date = climate_date;
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
	
	//Blocco getter e setter per "temp_max"
	public double getTemp_max() {
		return temp_max;
	}
	public void setTemp_max(double temp_max) {
		this.temp_max = temp_max;
	}
	
	//Blocco getter e setter per "description"
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	

}