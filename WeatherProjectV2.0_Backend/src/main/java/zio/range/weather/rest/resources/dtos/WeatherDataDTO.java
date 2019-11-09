package zio.range.weather.rest.resources.dtos;

import java.io.Serializable;

/**
 *
 * @author Panos
 */
public class WeatherDataDTO  implements Serializable{
    
    private Double temperature;
    private Integer humidity;
    private Double atmPressure;

   

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getAtmPressure() {
        return atmPressure;
    }

    public void setAtmPressure(Double atmPressure) {
        this.atmPressure = atmPressure;
    }    
    
}
