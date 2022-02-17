package Controller;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ModellodiClimate.DbClimate;
import Service.ClimateService;
import Statistics.ClimateStatistics;

/**
 * "/climate" rappresenta la radice delle operazioni con le previsioni meteo
 * è stato aggiunto per facilitare la definizione dei vari segmenti
 * a questo si aggiungono diversi segmenti in base alle necessità
 */

@RestController
@RequestMapping("/climate")
public class ClimateRestController {
	
	@Value("${meteoapi}")
	private String pwd;
	
	@Autowired
	ClimateService climateService; 
	
	
	/** 
	 * Richiede al sito openweathermap.org  le previsioni generiche per le città che rispettano i parametri dati.
	 * Nella successiva funzione chiamata si usa l'elenco delle città per estrarre il codice città delle città richieste.
	 * Usando il codice città si ottengono risultati più accurati.
	 * 
	 * @return List<DbClimate>
	 * @throws Exception
	 */
	@GetMapping("")
	// è identico al /climate
	public List<DbClimate> getClimateFor(
										@RequestParam(name="city", required=false) String city,
										@RequestParam(name="country", required=false, defaultValue="*") String countryorNull) throws Exception {
		return this.climateService.getClimateFor(city, countryorNull);
	}

	
	/** 
 	* Con questa chiamata si fa partire un thread che raccoglie le previzioni in base ai parametri dati
	* pwd - rappresenta la stringa password
	* city - rappresenta la città o le città per cui si fa la ricerca
	* 
	* country - restringe la ricerca della città al paese indicato 
	* per questi due parametri si possono usare i seguenti caratteri sostitutivi:
	* ! - sostituisce un carattere nella posizione corrente
	* * - sostituisce uno o più caratteri cominciando dalla posizione corrente. 
	* Si possono indicare più città o paesi divisi da virgole, senza spazi.
	* 
	* sleep - è il valore per il segmento di tempo in cui il thread si ferma.
	* type - rappresenta il tipo di valore sleep che può essere adottato: milliseconds, seconds, minutes, hours, days weeks
	 * @return String
	 */
	@GetMapping("/lookup")
	public String startClimateAutoLookup(
										@RequestParam(name="city", required=false) String city,
										@RequestParam(name="country", required=false, defaultValue="*") String countryorNull,
										@RequestParam(name="sleep", required=false, defaultValue="1") Integer sleepInterval,
										// possible values for type: milliseconds, seconds, minutes, hours, days, weeks
										@RequestParam(name="type", required=false, defaultValue="hours") String sleepIntervalType) {
		// city, countryorNull, sleepInterval, sleepIntervalType
		//if (null == this.pwd || !this.pwd.equals(pwd)) return "Cannot load the climate!";
		this.climateService.startAutolookupClimateFor(city, sleepInterval, sleepIntervalType);
		return "Climate autolookup started...";
	}
	
	
	
	/** 
	 * Ferma il thred della chiamata sopraindicata.
	 * 
	 * @return String
	 */
	@GetMapping("/lookup/stop")
	public String stopClimateAutoLookup() {
		this.climateService.stopAutolookupClimateFor();
		return "Climates autolookup stopped...";
	}
	
	
	
	/** 
	* Fa la medesima cosa della chiamata /lookup per scopi di test, creado dei dati fake per il climate
	* Questo per salvaguardare il numero limitato di chiamate a  openweathermap.org  	
	* @return String
	
	@GetMapping("/seed")
	public String startClimateSeeding(@RequestParam(name="city", required=false) String city,
									  @RequestParam(name="sleep", required=false, defaultValue="5") Integer sleepInterval,
									  // possible values for type: milliseconds, seconds, minutes, hours, days, weeks
									  @RequestParam(name="type", required=false, defaultValue="hours") String sleepIntervalType) {
		
		//  city, countryorNull, sleepInterval, sleepIntervalType
		this.climateService.startSeedingClimateFor(city, sleepInterval, sleepIntervalType);
		return "Climate seeding started...";
	} */
	
	
	/** 
	 * ferma la chiamata sopraindicata.
	 * 
	 * @return String
	
	@GetMapping("/seed/stop")
	public String stopClimateSeeding() {
		this.climateService.stopSeedingClimateFor();
		return "Climate seeding stopped...";
	} */
	
	
	/** 
	* questa chiamata fornisce dati statistici in base ai parametri indicati
	* I parametri "start" ed "end" rappresentano rispettivamente la data d'inizio e la data della fine per il segmento di ricerca
	* Devono essere indicati sotto forma "YYYY-MM-DD HH:mm:ss"
	* se non saranno indicati verranno impostati sulla data odierna dalla mezzanotte a mezzanotte
	* @return List<ForecastStatistics>
	* @throws Exception
	*/
	@GetMapping("/statistics")
	public List<ClimateStatistics> getClimateFor(
												 @RequestParam(name="city", required=false) String city,
												 @RequestParam(name="country", required=false, defaultValue="*") String countryorNull,
												 @RequestParam(name="start", required=false) String start_date,
												 @RequestParam(name="end", required=false) String end_date) throws Exception {
		if (null == start_date) start_date = this.GetNowString("start");
		if (null == end_date) end_date = this.GetNowString("end");
		
		return this.climateService.getStatisticsFor(city ,start_date, end_date);
	}

	
	/** 
	 * Restituisce una stringa con forma della data "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param timeSpan
	 * @return String
	 */
	private String GetNowString(String timeSpan) {
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(new Date()) + (timeSpan.equals("start") ? " 00:00:00":" 23:59:59");
	}
	
	
}