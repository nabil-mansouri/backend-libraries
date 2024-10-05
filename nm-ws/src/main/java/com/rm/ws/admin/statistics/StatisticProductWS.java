package com.rm.ws.admin.statistics;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rm.contract.statistics.beans.StatisticOrderBean;
import com.rm.contract.statistics.beans.StatisticResultNodeBean;
import com.rm.contract.statistics.beans.StatisticsFilterBean;
import com.rm.contract.statistics.constants.DimensionPeriodTransationValue;
import com.rm.contract.statistics.constants.DimensionPeriodValue;
import com.rm.contract.statistics.constants.DimensionProductValue;
import com.rm.contract.statistics.constants.DimensionType;
import com.rm.contract.statistics.constants.MeasureType;
import com.rm.contract.statistics.constants.StatisticOrderDirection;
import com.rm.contract.statistics.constants.StatisticOrderType;
import com.rm.contract.statistics.exceptions.BadPeriodException;
import com.rm.contract.statistics.exceptions.NoDimensionGeneratorException;
import com.rm.contract.statistics.exceptions.TooMuchDateException;
import com.rm.soa.statistics.SoaStatistics;
import com.rm.soa.statistics.exceptions.BadStatisticFilterException;
import com.rm.soa.statistics.utils.DateComputer;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("/statistics/product")
public class StatisticProductWS {

	@Autowired
	private SoaStatistics soaStatistics;
	@Autowired
	private DateComputer dateComputer;

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<StatisticResultNodeBean> simple(@QueryParam("period") DimensionPeriodValue period, @QueryParam("from") Long from,
			@QueryParam("to") Long to, @QueryParam("products") List<Long> product, @QueryParam("measures") List<MeasureType> measures)
			throws NoDimensionGeneratorException, BadStatisticFilterException, BadPeriodException, TooMuchDateException {
		StatisticsFilterBean filter = new StatisticsFilterBean();
		filter.getDimensions().put(DimensionType.Period, period);
		filter.getMeasureTypes().addAll(measures);
		filter.getOrders().add(new StatisticOrderBean(StatisticOrderDirection.Asc, StatisticOrderType.get(period)));
		filter.setIdProduct(product);
		filter.setFrom(new Date(from));
		filter.setTo(new Date(to));
		return soaStatistics.productStat(filter);
	}

	@GET
	@Path("count")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<StatisticResultNodeBean> countByDate(@QueryParam("period") DimensionPeriodValue period, @QueryParam("from") Date from,
			@QueryParam("to") Date to, @QueryParam("products") List<Long> product) throws NoDimensionGeneratorException, BadStatisticFilterException,
			BadPeriodException, TooMuchDateException {
		StatisticsFilterBean filter = new StatisticsFilterBean();
		filter.getDimensions().put(DimensionType.Period, period);
		filter.getMeasureTypes().add(MeasureType.CountProduct);
		filter.getOrders().add(new StatisticOrderBean(StatisticOrderDirection.Asc, StatisticOrderType.get(period)));
		filter.setIdProduct(product);
		filter.setFrom(from);
		filter.setTo(to);
		return soaStatistics.productStat(filter);
	}

	@GET
	@Path("sum")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<StatisticResultNodeBean> sumByDate(@QueryParam("period") DimensionPeriodValue period, @QueryParam("from") Long from,
			@QueryParam("to") Long to, @QueryParam("products") List<Long> product) throws NoDimensionGeneratorException, BadStatisticFilterException,
			TooMuchDateException, BadPeriodException {
		StatisticsFilterBean filter = new StatisticsFilterBean();
		filter.getDimensions().put(DimensionType.Period, period);
		filter.getMeasureTypes().add(MeasureType.TotalAmountProduct);
		filter.getOrders().add(new StatisticOrderBean(StatisticOrderDirection.Asc, StatisticOrderType.get(period)));
		filter.setIdProduct((product));
		filter.setFrom(new Date(from));
		filter.setTo(new Date(to));
		return soaStatistics.productStat(filter);
	}

	@GET
	@Path("best/count")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<StatisticResultNodeBean> bestCount(@QueryParam("period") DimensionPeriodTransationValue period,
			@QueryParam("countTo") int countTo, @QueryParam("limit") Long limit) throws NoDimensionGeneratorException, BadStatisticFilterException,
			TooMuchDateException, BadPeriodException {
		StatisticsFilterBean filter = new StatisticsFilterBean();
		filter.getDimensions().put(DimensionType.Product, DimensionProductValue.Product);
		filter.getMeasureTypes().add(MeasureType.CountProduct);
		filter.getOrders().add(new StatisticOrderBean(StatisticOrderDirection.Desc, StatisticOrderType.ProductTotalCount));
		filter.setFrom(dateComputer.minus(new Date(), period, countTo));
		filter.setTo(new Date());
		filter.setLimit(limit);
		return soaStatistics.productStat(filter);
	}

	@GET
	@Path("best/sum")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<StatisticResultNodeBean> bestSumTotal(@QueryParam("period") DimensionPeriodTransationValue period,
			@QueryParam("countTo") int countTo, @QueryParam("limit") Long limit) throws NoDimensionGeneratorException, BadStatisticFilterException,
			TooMuchDateException, BadPeriodException {
		StatisticsFilterBean filter = new StatisticsFilterBean();
		filter.getDimensions().put(DimensionType.Product, DimensionProductValue.Product);
		filter.getMeasureTypes().add(MeasureType.TotalAmountProduct);
		filter.getOrders().add(new StatisticOrderBean(StatisticOrderDirection.Desc, StatisticOrderType.ProductTotalPrice));
		filter.setFrom(dateComputer.minus(new Date(), period, countTo));
		filter.setTo(new Date());
		filter.setLimit(limit);
		return soaStatistics.productStat(filter);
	}

}
