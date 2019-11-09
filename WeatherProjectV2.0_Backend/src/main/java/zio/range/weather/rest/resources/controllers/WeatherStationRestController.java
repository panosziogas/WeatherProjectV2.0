package zio.range.weather.rest.resources.controllers;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import zio.range.weather.data.model.WeatherData;
import zio.range.weather.data.service.WeatherDataService;
import zio.range.weather.rest.resources.dtos.WeatherDataDTO;

/**
 *
 * @author Panos
 */
@Dependent
public class WeatherStationRestController {
    
    @Inject
    private WeatherDataService weatherDataService;
    
    public void persistWeatherData(final WeatherDataDTO weatherData){
       WeatherData data = new WeatherData(weatherData.getTemperature(),weatherData.getHumidity(),weatherData.getAtmPressure());
       weatherDataService.createWeatherData(data);
    }
    
}
