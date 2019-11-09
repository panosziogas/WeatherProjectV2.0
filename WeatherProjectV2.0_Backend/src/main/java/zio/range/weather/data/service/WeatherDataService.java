package zio.range.weather.data.service;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import zio.range.weather.data.model.WeatherData;

/**
 *
 * @author Panos
 */
@Dependent
public class WeatherDataService {

    @Inject
    EntityManager em;
    
    @Transactional 
    public void createWeatherData(WeatherData data) {       
        em.persist(data);
    }

}
