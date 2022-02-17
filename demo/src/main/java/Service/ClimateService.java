package Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ModellodiCittà.DbCity;
import ModellodiCittà.JpaCityRepository;
import ModellodiClimate.Climate;
import ModellodiClimate.ClimateAutolookupRepository;
import ModellodiClimate.ClimateLoaderThread;
import ModellodiClimate.ClimateLookupService;
import ModellodiClimate.DbClimate;
import Statistics.ClimateStatistics;


@Service
public class ClimateService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	JpaCityRepository jpaCityRepository;
	
	@Autowired
	private ClimateAutolookupRepository climateAutolookupRepository;
	
	@Autowired(required = true)
	private ClimateLookupService climateLookupService;
	
	private ClimateLoaderThread climateLoaderThread;

	public ClimateService(ClimateLookupService climateLookupService) {
		this.climateLookupService = climateLookupService;
	}
		
	
	/** 
		Da qui si inizia ad effetturare la procedura per ricavare i dati richiesti.
		Seguiranno diversi metodi che verranno man mano introdotti.
	 */
	public List<DbClimate> getClimateFor(
			String city,
			String countryorNull
			) throws Exception {
		 return this.getClimateFor(city, countryorNull, false, null);
	}
	
	
	/**
	 Questo primo metodo gestisce la richiesta da parte dell'utente 
	 Funzionamento:
	 1)Richiesta città da esaminare (fino a "String[] criteria");
	 2)Utilizza il metodo "GetClimate" per ottenere i dati voluti della città esaminata (primo "if");
	 3)Devo ancora capire questo "GetSeededClimate"(secondo "if");
	*/
	public List<DbClimate> getClimateFor(String city, String countryorNull, Boolean seed, Date seedingDate) throws Exception {
		try {
			if(null == city) throw new Exception("Must give at least a city name.");
			
			String[] criteria = {city, countryorNull};
			
			if (null == seed || false == seed) {
				return this.GetClimate(this.jpaCityRepository.findByName(criteria));
			}else {
				if(null == seedingDate) throw new Exception("Must provide a starting seeding date.");
				return this.GetSeededClimate(this.jpaCityRepository.findByName(criteria), seedingDate);
			}
		} catch(Exception e) {
			return null;
		}
	}

	
	/** 
	 Questo è un metodo del "dietro le quinte"...al momento credo che il metodo "GetClimateFor" sia il più importante e
	 che questo sia un metodo costruito per far funzionare correttamente il metodo indicato qui sopra
	 */
	private List<DbClimate> GetClimate(List<DbCity> cities) throws InterruptedException, ExecutionException{
		
		List<DbClimate> fcl = new ArrayList<DbClimate>();
		List<CompletableFuture<Climate>> cfl = new ArrayList<CompletableFuture<Climate>>();
		
		/*Se non ho capito male in questo "for each" vengono assegnate le voci del file html legato alla "ClimateLookupService"
		 ad ogni città presente nel DbCity;
		*/
		for(DbCity city : cities) {
			CompletableFuture<Climate> f = climateLookupService.getClimateForCity(city);
			cfl.add(f);
		}
		
		DbClimate fc; 
		Climate fcg;	
		for(CompletableFuture<Climate> f : cfl) {
			CompletableFuture.anyOf(f).join();
			Date d = new Date();
			fcg = f.get();
			
			//Da qui iniziamo ad immettere dati nel DBForecast;
			fc = new DbClimate();
			fc.setCity_id(fcg.getId().toString());
			fc.setName(fcg.getName());
			fc.setTemp(fcg.getMain().getTemp());
			fc.setFeels_like(fcg.getMain().getFeels_like());
			fc.setTemp_min(fcg.getMain().getTemp_min());
			fc.setTemp_max(fcg.getMain().getTemp_max());
			fc.setDescription(fcg.getWeather().getDescription());
			
			fcl.add(fc);
		}
	
		return fcl;
	}
	
	
	/** 							 
	 * Genera le previsioni fittizie (temperatura e condizioni meteorlogiche) per poter testare l’applicativo in fase di sviluppo.
	 * 
	 * @param cities
	 * @param seedingDate
	 * @return List<DbClimate>
	 
	private List<DbClimate> GetSeededClimate(List<DbCity> cities, Date seedingDate) {
		List<DbClimate> fcl = new ArrayList<DbClimate>();
		DbClimate fc;
		for(DbCity city : cities) {
			fc= new DbClimate();
			fc.setCity_id(city.getId().toString());
			fc.setName(city.getName());
			fc.setCountry(city.getCountry());
			fc.setForecast_date(seedingDate);
			
			fcl.add(fc);
		}
		return fcl;
	} */
	
	
	/** 
	 * Fa partire il processo automatico di recupero dei dati dal sito www.openweathermap.org
	 * 
	 * @param city
	 * @param sleepInterval
	 * @param sleepIntervalType
	 */
	public void startAutolookupClimateFor(String city, Integer sleepInterval, String sleepIntervalType) {
		this.stopAutolookupClimateFor();
		this.climateLoaderThread = new ClimateLoaderThread(this, city, sleepInterval, sleepIntervalType, false);
		Thread t = new Thread(this.climateLoaderThread);
		t.start();
		System.out.println("Begin autolookup forecast...");
	}
	 /**
	  * Ferma il processo automatico di recupero dei dati dal sito www.openweathermap.org
	  */
	public void stopAutolookupClimateFor() {
		try {
			if (null != this.climateLoaderThread) this.climateLoaderThread.doStop();
			System.out.println("End autolookup climate...");	
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Restituisce le previsioni fittizie (temperatura e condizioni meteorologiche) per poter testare l’applicativo in fase di sviluppo.
	 * Questa funzione viene appellata dal generatore automatico.
	 * 
	 * @param city
	 * @param sleepInterval
	 * @param sleepIntervalType
	 
	public void startSeedingClimateFor(String city, Integer sleepInterval, String sleepIntervalType) {
		this.stopAutolookupClimateFor();
		this.climateLoaderThread = new ClimateLoaderThread(this, city, sleepInterval, sleepIntervalType, true);
		Thread t = new Thread(this.climateLoaderThread);
		t.start();
		System.out.println("Begin seeding climate table...");
	} */
	
	/**
	 * Ferma il generatore automatico di dati fittizi.
	 
	public void stopSeedingClimateFor() {
		try {
			if (null != this.climateLoaderThread) this.climateLoaderThread.doStop();
			System.out.println("End seeding climates table...");	
		}catch(Exception e) {
			e.printStackTrace();
		}
	} */
	
	
	/** 
	 * Salva sia i dati reali che fittizi nel database
	 * 
	 * @param dbClimateList
	 */							 
	public void saveDbClimateList(List<DbClimate> dbClimateList) {
		this.climateAutolookupRepository.saveAll(dbClimateList);
		System.out.println("Climates list successfully saved!");
	}
	
	
	/** 
	 * Restituisce la lista con i dati statistici per le città indicate 
	 * 
	 * @param city
	 * @param start_date
	 * @param end_date
	 * @return List<ClimateStatistics>
	 * @throws Exception
	 */
	public List<ClimateStatistics> getStatisticsFor(String city,
													String start_date,
													String end_date) throws Exception {
		if(null == city) throw new Exception("Must give at least a city name.");
		String[] criteria = {city, start_date, end_date};
		return this.generate(criteria);
	}
	
	
	/** 
	 * Restituisce la lista con i dati statistici per le città indicate nel database.
	 * 
	 * @param criteria
	 * @return List<ClimateStatistics>
	 */
	private List<ClimateStatistics> generate(String[] criteria) {
		String city = criteria[0];
		String countryId = criteria[1];
		String start_date = criteria[2];
		String end_date = criteria[3];
		
		//Non capisco a cosa mi serve tutto il blocco che va dalla riga 251 alla riga 275... 
		//forse serve per l'inserimento di città con nome composto come "Caselle di Maltignano" che vede degli spazi al suo interno!
		city = "" + city.replace("!", "_").replace("*", "%");
		
		if (null == countryId) countryId= "*";
		countryId= "" + countryId.replace("!", "_").replace("*", "%");
		
		
		String cityWhere = "";			//Anche per la premessa di prima...
		String countryWhere = "";		//...a cosa mi servono questi?
		
		String[] cs = city.split(",");
		for(int i = 0; i < cs.length; i++){
			cityWhere +=  ((cityWhere.length() > 0) ? " OR " : "") + " c.name like :name" + i;
		}
		
		String[] cis = countryId.split(",");
		
		for(int i = 0; i < cis.length; i++){
			countryWhere +=  ((countryWhere.length() > 0) ? " OR " : "") + " c.country like :country" + i;
			
		}
			
		if (cityWhere.length() > 0) cityWhere = " and (" + cityWhere + " ) ";
		
		if (countryWhere.length() > 0) countryWhere = " and ( " + countryWhere + " ) ";
		
		String whereCondition = countryWhere + cityWhere; 
		
		StringBuilder sql = new StringBuilder("");
		String nl = "\n";
		sql.append(" select id, count(city_id) as row_n, country, city_id, name, " + nl);
		sql.append(" '"+ start_date +"' as start, '" + end_date + "' as end, " + nl);
		sql.append(" min(temp) as temp_min, max(temp) as temp_max, avg(temp) as temp_avg, (max(temp) - min(temp)) as temp_var, " + nl);
		//sql.append(" min(pressure) as min_pressure, max(pressure) as max_pressure, avg(pressure) as avg_pressure, (max(pressure) - min(pressure)) as var_pressure " + nl);
		sql.append(" FROM climates as c " + nl);
		sql.append(" where (c.climate_date BETWEEN '"+ start_date +"' AND '" + end_date + "')  " + nl);
		sql.append( whereCondition  + nl);
		sql.append(" group by c.city_id " + nl);
		sql.append(" order by c.name asc, c.city_id asc, c.climate_date desc " + nl);
		
		System.out.println(sql.toString());
		
		Query query = em.createNativeQuery(sql.toString());
		
		for(int i = 0; i < cis.length; i++){
			query.setParameter("country"+i, cis[i]);
		}
		
		for(int i = 0; i < cs.length; i++){
			query.setParameter("name"+i, cs[i]);
		}
		
		@SuppressWarnings("unchecked")
		List<Object[]> obj = query.getResultList();
		
		List<ClimateStatistics> fcs = new ArrayList<ClimateStatistics>();
		
		ClimateStatistics fs;
		for (Object [] o : obj) {
			fs = new ClimateStatistics();
			fs.setId((Integer) o[0]);
			fs.setRow_n((BigInteger) o[1]);
			fs.setCountryorNull((String) o[2]);
			fs.setCity_id((String) o[3]);
			fs.setName((String) o[4]);
			fs.setStart((String) o[5]);
			fs.setEnd((String) o[6]);
			fs.setTemp_min((Integer) o[7]);
			fs.setTemp_max((Integer) o[8]);
			fs.setTemp_avg((BigDecimal) o[9]);
			fs.setTemp_var((BigInteger) o[10]);
			fcs.add(fs);
		}
		
		return fcs;
	}
}