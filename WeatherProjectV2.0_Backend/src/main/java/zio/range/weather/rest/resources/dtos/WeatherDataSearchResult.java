package zio.range.weather.rest.resources.dtos;

import java.util.List;

/**
 *
 * @author Panos
 */
public class WeatherDataSearchResult {

    private List<WeatherDataSearchResultDTO> weatherData;
    private Boolean hasMorePages;
    private Long totalResults;

    public WeatherDataSearchResult() {
    }    
    

    public WeatherDataSearchResult(List<WeatherDataSearchResultDTO> weatherData, Boolean hasMorePages, Long totalResults) {
        this.weatherData = weatherData;
        this.hasMorePages = hasMorePages;
        this.totalResults = totalResults;
    }

    public List<WeatherDataSearchResultDTO> getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(List<WeatherDataSearchResultDTO> weatherData) {
        this.weatherData = weatherData;
    }

    public Boolean getHasMorePages() {
        return hasMorePages;
    }

    public void setHasMorePages(Boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    
    
}
