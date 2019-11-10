package zio.range.weather.rest.resources.dtos;

import java.util.Date;

/**
 *
 * @author Panos
 */
public class WeatherDataSearchFilter {
    
    private Date logDateFrom;
    private Date logDateUntil;
    private SearchOrder searchOrder;
    private Integer pageNumber;
    private Integer pageSize;

    public WeatherDataSearchFilter() {
    }
    
    

    public Date getLogDateFrom() {
        return logDateFrom;
    }

    public void setLogDateFrom(Date logDateFrom) {
        this.logDateFrom = logDateFrom;
    }

    public Date getLogDateUntil() {
        return logDateUntil;
    }

    public void setLogDateUntil(Date logDateUntil) {
        this.logDateUntil = logDateUntil;
    }

    public SearchOrder getSearchOrder() {
        return searchOrder;
    }

    public void setSearchOrder(SearchOrder searchOrder) {
        this.searchOrder = searchOrder;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    
    
    
}
