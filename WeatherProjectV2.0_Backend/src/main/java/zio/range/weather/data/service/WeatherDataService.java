package zio.range.weather.data.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import zio.range.weather.data.model.WeatherData;
import zio.range.weather.rest.resources.dtos.SearchOrder;

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

    public List<WeatherData> getWeatherDataResultsByFilter(final Date logDateFrom, final Date logDateUntil, final SearchOrder order, final Integer pageNumber, final Integer pageSize) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<WeatherData> cq = cb.createQuery(WeatherData.class);
        final Root<WeatherData> weatherDataRoot = cq.from(WeatherData.class);
        List<Predicate> predicateList = buildWeatherDataPredicatesByFilter(cb, weatherDataRoot, logDateFrom, logDateUntil);
        cq.where(predicateList.toArray(new Predicate[]{}));
        cq.distinct(true);
        cq.select(weatherDataRoot);
        cq.orderBy(weatherDataByFilterOrderType(cb, weatherDataRoot, order));
        final TypedQuery<WeatherData> qWeatherData = em.createQuery(cq);
        qWeatherData.setFirstResult(pageNumber);
        qWeatherData.setMaxResults(pageSize);
        return qWeatherData.getResultList();
    }
    
     public Long countWeatherDataResultsByFilter(final Date logDateFrom, final Date logDateUntil) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<WeatherData> weatherDataRoot = cq.from(WeatherData.class);
        cq.select(cb.countDistinct(weatherDataRoot.get("weatherDataId")));
        List<Predicate> predicateList = buildWeatherDataPredicatesByFilter(cb, weatherDataRoot, logDateFrom,logDateUntil);
        cq.where(predicateList.toArray(new Predicate[]{}));
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private List<Predicate> buildWeatherDataPredicatesByFilter(final CriteriaBuilder criteriaBuilder,
            final Root<WeatherData> weatherDataRoot, final Date logDateFrom, final Date logDateUntil) {
        final List<Predicate> predicates = new ArrayList<>();
        if (logDateFrom != null) {
            final Predicate logDateFromPredicate
                    = criteriaBuilder.greaterThanOrEqualTo(weatherDataRoot.get("logDate"), logDateFrom);
            predicates.add(logDateFromPredicate);
        }
        if (logDateUntil != null) {
            final Predicate logDateUntilPredicate
                    = criteriaBuilder.lessThanOrEqualTo(weatherDataRoot.get("logDate"), logDateUntil);
            predicates.add(logDateUntilPredicate);
        }
        return predicates;
    }

    private List<Order> weatherDataByFilterOrderType(CriteriaBuilder cb, Root<WeatherData> weatherDataRoot, SearchOrder sortOrder) {
        final List<Order> orderList = new ArrayList<>(2);
        if (sortOrder == null) {
            orderList.add(cb.desc(weatherDataRoot.get("logDate")));
            return orderList;
        }
        switch (sortOrder) {
            case ASC:
                orderList.add(cb.asc(weatherDataRoot.get("logDate")));
                return orderList;
            case DESC:
                orderList.add(cb.desc(weatherDataRoot.get("logDate")));
                return orderList;
            default:
                orderList.add(cb.desc(weatherDataRoot.get("logDate")));
                return orderList;
        }
    }

}
