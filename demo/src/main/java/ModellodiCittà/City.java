package ModellodiCittà;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="cities")
@JsonIgnoreProperties(ignoreUnknown=true)
public class City {
	
	@Id
	private Integer id;
	
	private String name;
	private String state;
	private String country;
	
	@Embedded
	private Coord coord;
	
	//Blocco getter e setter per "ID"
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
	
	//Blocco getter e setter per "coord"
	public Coord getCoord() {
		return coord;
	}
	
	public void setCoord(Coord coord) {
		this.coord = coord;
	}
	
	//Blocco "toString"
	@Override
	public String toString() {
		return "" + this.name;
	}
}