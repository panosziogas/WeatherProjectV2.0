package zio.range.weather.rest.resources.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import zio.range.weather.data.model.WeatherData;
import zio.range.weather.data.service.WeatherDataService;
import zio.range.weather.rest.resources.dtos.SearchOrder;
import zio.range.weather.rest.resources.dtos.WeatherDataDTO;
import zio.range.weather.rest.resources.dtos.WeatherDataSearchFilter;
import zio.range.weather.rest.resources.dtos.WeatherDataSearchResult;
import zio.range.weather.rest.resources.dtos.WeatherDataSearchResultDTO;

/**
 *
 * @author Panos
 */
@Dependent
public class WeatherStationRestController {

    @Inject
    private WeatherDataService weatherDataService;

    public void persistWeatherData(final WeatherDataDTO weatherData) {
        WeatherData data = new WeatherData(weatherData.getTemperature(), weatherData.getHumidity(), weatherData.getAtmPressure());
        weatherDataService.createWeatherData(data);
    }

    public WeatherDataSearchResult searchWeatherData(WeatherDataSearchFilter dataSearchFilter) {
        WeatherDataSearchResult result = new WeatherDataSearchResult();
        List<WeatherData> weatherDataResult = weatherDataService.getWeatherDataResultsByFilter(dataSearchFilter.getLogDateFrom(),
                dataSearchFilter.getLogDateUntil(), dataSearchFilter.getSearchOrder(), dataSearchFilter.getPageNumber(), dataSearchFilter.getPageSize());
        if (weatherDataResult.isEmpty()) {
            result.setTotalResults(Long.MIN_VALUE);
            result.setHasMorePages(false);
            return result;
        } else {
            long count = weatherDataService.countWeatherDataResultsByFilter(dataSearchFilter.getLogDateFrom(), dataSearchFilter.getLogDateUntil());
            boolean hasMore = dataSearchFilter.getPageSize() < count;
            List<WeatherDataSearchResultDTO> mappedDataList = new ArrayList<>();
            for (WeatherData weatherData : weatherDataResult) {
                mappedDataList.add(new WeatherDataSearchResultDTO(weatherData.getTemperature(), weatherData.getHumidity(), weatherData.getAtmPressure(),weatherData.getLogDate()));
            }
            result.setTotalResults(count);
            result.setHasMorePages(hasMore);
            result.setWeatherData(mappedDataList);
            return result;
        }      
    }

}
