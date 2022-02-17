package ModellodiCittà;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="cities")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DbCity {

	public DbCity() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String city_id;
	private String name;
	private String state;
	private String country;
	private String longitude;
	private String latitude;
	
	
	//Costruttore oggetto "DBCity"
	public DbCity(City city) {
		this.city_id = city.getId().toString();
		this.name = city.getName();
		this.state = city.getState();
		this.country = city.getCountry();
		Float f = city.getCoord().getLon();
		this.longitude = f.toString();
		f = city.getCoord().getLat();
		this.latitude = f.toString();
	}
	
	
	//Blocco getter e setter per "id"
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
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
	
	//Blocco getter e setter per "state"
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	//Blocco getter e setter per "country"
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	//Blocco getter e setter per "longitude"
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	//Blocco getter e setter per "latitude"
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
}