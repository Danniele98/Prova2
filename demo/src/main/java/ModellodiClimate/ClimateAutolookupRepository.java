package ModellodiClimate;

import org.springframework.data.repository.CrudRepository;

/*Il seguente metodo verrà usato per gestire i dati di tipo "double" da inserire in DBForecast presenti nel file html. 
 *I dati in "double" saranno le temperature!
 * */

public interface ClimateAutolookupRepository extends CrudRepository<DbClimate, Integer>{

}