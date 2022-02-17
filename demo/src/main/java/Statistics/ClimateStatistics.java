package Statistics;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

//@Table(name="climates")
@Data
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
public class ClimateStatistics {
	
		
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;
	private BigInteger row_n;
	private String countryorNull;
	private String city_id;
	private String name;
	
//	@Column(name="start", columnDefinition="DATETIME")
//	@Temporal(TemporalType.TIMESTAMP)
	private String start;
	
//	@Column(name="end", columnDefinition="DATETIME")
//	@Temporal(TemporalType.TIMESTAMP)
	private String end;
	
	private double temp;
	private double temp_min;
	private double temp_max;
	private BigDecimal temp_avg;
	private BigInteger temp_var;
	private String description;
	
	//Blocco getter e setter per "id"
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	//Blocco getter e setter per "row_n"
	public BigInteger getRow_n() {
		return row_n;
	}
	public void setRow_n(BigInteger row_n) {
		this.row_n = row_n;
	}
	
	//Blocco getter e setter per "countryorNull"
	public String getCountryorNull() {
		return countryorNull;
	}
	public void setCountryorNull(String countryorNull) {
		this.countryorNull = countryorNull;
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
	
	//Blocco getter e setter per "start"
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	
	//Blocco getter e setter per "end"
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	//Blocco getter e setter per "temp"
	public double getTemp() {
		return temp;
	}
	
	public void setTemp(double temp) {
		this.temp=temp;
	}
	
	//Blocco getter e setter per "temp_min
	public double getTemp_min() {
		return temp_min;
	}
	public void setTemp_min(Integer temp_min) {
		this.temp_min = temp_min;
	}
	
	//Blocco getter e setter per "temp_max"
	public double getTemp_max() {
		return temp_max;
	}
	public void setTemp_max(Integer temp_max) {
		this.temp_max = temp_max;
	}
	
	//Blocco getter e setter per "temp_avg"
	public BigDecimal getTemp_avg() {
		return temp_avg;
	}
	public void setTemp_avg(BigDecimal temp_avg) {
		this.temp_avg = temp_avg;
	}
	
	//Blocco getter e setter per "temp_var"
	public BigInteger getTemp_var() {
		return temp_var;
	}
	public void setTemp_var(BigInteger temp_var) {
		this.temp_var = temp_var;
	}
	
	//Blocco getter e setter per "description"
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}