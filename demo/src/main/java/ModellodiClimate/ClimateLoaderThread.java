package ModellodiClimate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ClimateLoaderThread implements Runnable{
	private boolean doStop = false;
	
	private int timeToSleep = 1000;
	
	private ClimateService climateService;
	private String city;
	private Integer sleepInterval;
	private String sleepIntervalType;
	private Boolean seed;
	
	
	//Costruttore oggetto "ClimateLoaderThread"
	public ClimateLoaderThread(
			ClimateService ClimateService,
			String city, Integer sleepInterval, String sleepIntervalType, Boolean seed) {
		
		this.climateService = climateService;
		this.city = city;
		this.sleepInterval = sleepInterval;
		this.sleepIntervalType = sleepIntervalType;
		this.seed = seed;
		
		/*switch(this.sleepIntervalType) {
			case "hours":*/
				this.setSleepHours(this.sleepInterval);
			/*	break;
			case "days":
				this.setSleepDays(this.sleepInterval);
				break;
		 	case "weeks":
		 		this.setSleepWeeks(this.sleepInterval);
			default:				
				this.setSleepMinutes(this.sleepInterval);
		}*/
		
		
	}

	public synchronized void doStop() {
        this.doStop = true;
        System.out.println("thread stopped...");
    }

    
	/** 
	 * @return boolean
	 */
	private synchronized boolean keepRunning() {
        return this.doStop == false;
    }
	
	@Override
	public void run() {
		Date currentDate = new Date();
		// Prendiamo la data dal calendario
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        // manipulate date
        c.add(Calendar.MONTH, -1);
        Date seedingDate = c.getTime();
        
		while(this.keepRunning()) {
			try {
				List<DbClimate> fbf;
				if (null == this.seed || false == this.seed) {
					 fbf = this.climateService.getClimateFor(this.city);
				}else{
					fbf = this.climateService.getClimateFor(this.city, this.seed, seedingDate);
					c.setTime(seedingDate);
			        // manipulate date
			        c.add(Calendar.HOUR, 1);
			        seedingDate = c.getTime();
			        if (seedingDate.after(currentDate)) this.doStop();
				}
				
				this.climateService.saveDbClimateList(fbf);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				this.doSleep();	
			}
		}
		
	}
	
	
	//Blocco getter e setter per "timeToSleep"...
	public int getTimeToSleep() {
		return this.timeToSleep;
	}
/*
	//... in millisecondi, @param milliseconds
	public int setSleepMilliseconds(int milliseconds) {			//Metodo
		this.timeToSleep =  milliseconds;						//ritenuto
		return this.timeToSleep;								//inutile
	}
	
	//...in secondi, @param seconds				
	public int setSleepSeconds(int seconds) {							//Metodo
		this.timeToSleep =  seconds * this.setSleepMilliseconds(1000);	//ritenuto
		return this.timeToSleep;										//inutile
	}
*/
	
	//...in minuti, @param minutes
	public int setSleepMinutes(int minutes) {							//Metodo
		this.timeToSleep =  minutes * this.setSleepMinutes(60);			//ritenuto
		return this.timeToSleep;										//inutile
	}

	
	//...in ore, @param hours
	public int setSleepHours(int hours) {
		this.timeToSleep =  hours * this.setSleepMinutes(60);
		return this.timeToSleep;
	}
	
	//...in giorni, @param days
	public int setSleepDays(int days) {
		this.timeToSleep =  days * this.setSleepHours(24);
		return this.timeToSleep;
	}
	

	 //...in settimane, @param weeks
	 public void setSleepWeeks(int weeks){
	 	this.timeToSleep= weeks*this.setSleepDays(7);
	 }
	
//	private functions	
	private void doSleep() {
		try {
			Thread.sleep(this.timeToSleep);
		} catch (InterruptedException e) {
			this.doStop();
			e.printStackTrace();
		}
	}
	
}