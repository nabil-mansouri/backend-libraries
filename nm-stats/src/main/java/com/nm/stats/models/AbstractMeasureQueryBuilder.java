package com.nm.stats.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import com.nm.stats.constants.DimensionTypeDefaut;
import com.nm.stats.constants.DimensionValue;
import com.nm.stats.constants.DimensionValuePeriod;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public abstract class AbstractMeasureQueryBuilder extends AbstractQueryBuilder<AbstractMeasureQueryBuilder> {
	// TODO all old values
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected final DetachedCriteria criteria;
	protected Collection<Order> orders = new ArrayList<Order>();
	protected ProjectionList projections = Projections.projectionList();

	protected AbstractMeasureQueryBuilder(Class<?> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	public DetachedCriteria getQuery() {
		criteria.setProjection(projections);
		for (Order o : orders) {
			criteria.addOrder(o);
		}
		return this.criteria;
	}

	// public AbstractMeasureQueryBuilder withAvgOrderPrice() {
	// this.projections.add(Projections.alias(Projections.avg("orderTotal"),
	// MeasureType.AvgOrderPrice.getSqlName()));
	// return this;
	// }

	public AbstractMeasureQueryBuilder withByDay(Collection<Long> idDates) {
		createAlias("days", "days", JoinType.FULL_JOIN);
		this.projections.add(Projections.groupProperty("days.date"), DimensionTypeDefaut.Period.getSqlName());
		if (!idDates.isEmpty()) {
			this.criteria.add(Restrictions.in("days.id", idDates));
		}
		return this;
	}

	public AbstractMeasureQueryBuilder withByHour(Collection<Long> idDates) {
		createAlias("hours", "hours", JoinType.FULL_JOIN);
		this.projections.add(Projections.groupProperty("hours.date"), DimensionTypeDefaut.Period.getSqlName());
		if (!idDates.isEmpty()) {
			this.criteria.add(Restrictions.in("hours.id", idDates));
		}
		return this;
	}

	public AbstractMeasureQueryBuilder withByMinute(Collection<Long> idDates) {
		createAlias("minutes", "minutes", JoinType.FULL_JOIN);
		this.projections.add(Projections.groupProperty("minutes.date"), DimensionTypeDefaut.Period.getSqlName());
		if (!idDates.isEmpty()) {
			this.criteria.add(Restrictions.in("minutes.id", idDates));
		}
		return this;
	}

	public AbstractMeasureQueryBuilder withByMonth(Collection<Long> idDates) {
		createAlias("months", "months", JoinType.FULL_JOIN);
		this.projections.add(Projections.groupProperty("months.date"), DimensionTypeDefaut.Period.getSqlName());
		if (!idDates.isEmpty()) {
			this.criteria.add(Restrictions.in("months.id", idDates));
		}
		return this;
	}

	public AbstractMeasureQueryBuilder withByWeek(Collection<Long> idDates) {
		createAlias("weeks", "weeks", JoinType.FULL_JOIN);
		this.projections.add(Projections.groupProperty("weeks.date"), DimensionTypeDefaut.Period.getSqlName());
		if (!idDates.isEmpty()) {
			this.criteria.add(Restrictions.in("weeks.id", idDates));
		}
		return this;
	}

	public AbstractMeasureQueryBuilder withByYear(Collection<Long> idDates) {
		createAlias("years", "years", JoinType.FULL_JOIN);
		this.projections.add(Projections.groupProperty("years.date"), DimensionTypeDefaut.Period.getSqlName());
		if (!idDates.isEmpty()) {
			this.criteria.add(Restrictions.in("years.id", idDates));
		}
		return this;
	}

	// public AbstractMeasureQueryBuilder withByDayTransaction() {
	// this.projections.add(Projections.groupProperty("transactionday"),
	// DimensionTypeDefaut.PeriodTransaction.getSqlName());
	// return this;
	// }
	//
	// public AbstractMeasureQueryBuilder withByHourTransaction() {
	// this.projections.add(Projections.groupProperty("transactionhour"),
	// DimensionTypeDefaut.PeriodTransaction.getSqlName());
	// return this;
	// }
	//
	// public AbstractMeasureQueryBuilder withByMinuteTransaction() {
	// this.projections.add(Projections.groupProperty("transactionminute"),
	// DimensionTypeDefaut.PeriodTransaction.getSqlName());
	// return this;
	// }
	//
	// public AbstractMeasureQueryBuilder withByMonthTransaction() {
	// this.projections.add(Projections.groupProperty("transactionmonth"),
	// DimensionTypeDefaut.PeriodTransaction.getSqlName());
	// return this;
	// }

	// public AbstractMeasureQueryBuilder withByWeekTransaction() {
	// this.projections.add(Projections.groupProperty("transactionweek"),
	// DimensionTypeDefaut.PeriodTransaction.getSqlName());
	// return this;
	// }

	// public AbstractMeasureQueryBuilder withByYearTransaction() {
	// this.projections.add(Projections.groupProperty("transactionyear"),
	// DimensionTypeDefaut.PeriodTransaction.getSqlName());
	// return this;
	// }

	// public AbstractMeasureQueryBuilder withClient(Client client) {
	// this.criteria
	// .add(Restrictions.or(Restrictions.eq("idClient", client.getId()),
	// Restrictions.isNull("idClient")));
	// return this;
	// }

	// public AbstractMeasureQueryBuilder withByOrder() {
	// this.projections.add(Projections.groupProperty("idOrder"),
	// DimensionTypeDefaut.Order.getSqlName());
	// return this;
	// }

	// public AbstractMeasureQueryBuilder withFilter(StatisticsFilterBean
	// filter) {
	// if (filter.getFrom() != null) {
	// this.withFrom(filter.getFrom(), filter.getDimensions());
	// }
	// if (filter.getTo() != null) {
	// this.withTo(filter.getTo(), filter.getDimensions());
	// }
	// for (DimensionTypeDefaut dim : filter.getDimensions().keySet()) {
	// DimensionValue val = filter.getDimensions().get(dim);
	// switch (dim) {
	// case Period: {
	// DimensionValuePeriod period = (DimensionValuePeriod) val;
	// switch (period) {
	// case Minute:
	// withByMinute(filter.getIdDates());
	// break;
	// case Hour:
	// withByHour(filter.getIdDates());
	// break;
	// case Day:
	// withByDay(filter.getIdDates());
	// break;
	// case Month:
	// withByMonth(filter.getIdDates());
	// break;
	// case Week:
	// withByWeek(filter.getIdDates());
	// break;
	// case Year:
	// withByYear(filter.getIdDates());
	// break;
	// }
	// }
	// break;
	// case PeriodTransaction: {
	// DimensionPeriodTransationValue period = (DimensionPeriodTransationValue)
	// val;
	// switch (period) {
	// case Minute:
	// withByMinuteTransaction();
	// break;
	// case Hour:
	// withByHourTransaction();
	// break;
	// case Day:
	// withByDayTransaction();
	// break;
	// case Month:
	// withByMonthTransaction();
	// break;
	// case Week:
	// withByWeekTransaction();
	// break;
	// case Year:
	// withByYearTransaction();
	// break;
	// }
	// }
	// break;
	// case Product:
	// // No product here
	// break;
	// case Order:
	// withByOrder();
	// break;
	// }
	// }
	// //
	// for (MeasureType type : filter.getMeasureTypes()) {
	// switch (type) {
	// case CountProduct:
	// case AvgProductPrice:
	// case TotalAmountProduct:
	// // No product here
	// break;
	// case CountOrder:
	// withNbOrder();
	// break;
	// case TotalAmountOrder:
	// withSumTotalOrder();
	// break;
	// case AvgOrderPrice:
	// withAvgOrderPrice();
	// break;
	//
	// }
	// }
	// if (filter.getOrders() != null) {
	// withOrdersBy(filter.getOrders());
	// }
	// if (filter.getLimit() != null) {
	// withLimit(filter.getLimit());
	// }
	// return this;
	// }

	public AbstractMeasureQueryBuilder withFrom(Date from, Map<DimensionTypeDefaut, DimensionValue> dimensions) {
		if (dimensions.containsKey(DimensionTypeDefaut.Period)) {
			DimensionValuePeriod period = (DimensionValuePeriod) dimensions.get(DimensionTypeDefaut.Period);
			switch (period) {
			case Minute:
				createAlias("minutes", "minutes", JoinType.FULL_JOIN);
				this.criteria.add(Restrictions.ge("minutes.date", from));
				break;
			case Hour:
				createAlias("hours", "hours", JoinType.FULL_JOIN);
				this.criteria.add(Restrictions.ge("hours.date", from));
				break;
			case Day:
				createAlias("days", "days", JoinType.FULL_JOIN);
				this.criteria.add(Restrictions.ge("days.date", from));
				break;
			case Month:
				createAlias("months", "months", JoinType.FULL_JOIN);
				this.criteria.add(Restrictions.ge("months.date", from));
				break;
			case Week:
				createAlias("weeks", "weeks", JoinType.FULL_JOIN);
				this.criteria.add(Restrictions.ge("weeks.date", from));
				break;
			case Year:
				createAlias("years", "years", JoinType.FULL_JOIN);
				this.criteria.add(Restrictions.ge("years.date", from));
				break;
			}
		}
		// else if
		// (dimensions.containsKey(DimensionTypeDefaut.PeriodTransaction)) {
		// DimensionPeriodTransationValue period =
		// (DimensionPeriodTransationValue) dimensions
		// .get(DimensionTypeDefaut.PeriodTransaction);
		// switch (period) {
		// case Minute:
		// this.criteria.add(Restrictions.ge("transactionminute", from));
		// break;
		// case Hour:
		// this.criteria.add(Restrictions.ge("transactionhour", from));
		// break;
		// case Day:
		// this.criteria.add(Restrictions.ge("transactionday", from));
		// break;
		// case Month:
		// this.criteria.add(Restrictions.ge("transactionmonth", from));
		// break;
		// case Week:
		// this.criteria.add(Restrictions.ge("transactionweek", from));
		// break;
		// case Year:
		// this.criteria.add(Restrictions.ge("transactionyear", from));
		// break;
		// }
		// }
		else {
			this.criteria.add(Restrictions.ge("transactionminute", from));
		}
		return this;
	}

	// public AbstractMeasureQueryBuilder withNbOrder() {
	// this.projections.add(Projections.alias(Projections.count("idOrder"),
	// MeasureType.CountOrder.getSqlName()));
	// return this;
	// }

	// public AbstractMeasureQueryBuilder
	// withOrdersBy(Collection<StatisticOrderBean> orders) {
	// for (StatisticOrderBean ord : orders) {
	// switch (ord.getType()) {
	// case Days:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc("days.date"));
	// } else {
	// this.orders.add(Order.desc("days.date"));
	// }
	// break;
	// case Hours:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc("hours.date"));
	// } else {
	// this.orders.add(Order.desc("hours.date"));
	// }
	// break;
	// case Minutes:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc("minutes.date"));
	// } else {
	// this.orders.add(Order.desc("minutes.date"));
	// }
	// break;
	// case Months:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc("months.date"));
	// } else {
	// this.orders.add(Order.desc("months.date"));
	// }
	// break;
	// case OrderTotalPrice:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc(MeasureType.TotalAmountOrder.getSqlName()));
	// } else {
	// this.orders.add(Order.desc(MeasureType.TotalAmountOrder.getSqlName()));
	// }
	// break;
	// case OrderTotalCount:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc(MeasureType.CountOrder.getSqlName()));
	// } else {
	// this.orders.add(Order.desc(MeasureType.CountOrder.getSqlName()));
	// }
	// break;
	// case ProductTotalCount:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc(MeasureType.CountProduct.getSqlName()));
	// } else {
	// this.orders.add(Order.desc(MeasureType.CountProduct.getSqlName()));
	// }
	// break;
	// case ProductTotalPrice:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc(MeasureType.TotalAmountProduct.getSqlName()));
	// } else {
	// this.orders.add(Order.desc(MeasureType.TotalAmountProduct.getSqlName()));
	// }
	// break;
	// case TransactionDays:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc("transactionday"));
	// } else {
	// this.orders.add(Order.desc("transactionday"));
	// }
	// break;
	// case TransactionHours:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc("transactionhour"));
	// } else {
	// this.orders.add(Order.desc("transactionhour"));
	// }
	// break;
	// case TransactionMinutes:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc("transactionminute"));
	// } else {
	// this.orders.add(Order.desc("transactionminute"));
	// }
	// break;
	// case TransactionMonths:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc("transactionmonth"));
	// } else {
	// this.orders.add(Order.desc("transactionmonth"));
	// }
	// break;
	// case TransactionWeeks:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc("transactionweek"));
	// } else {
	// this.orders.add(Order.desc("transactionweek"));
	// }
	// break;
	// case TransactionYears:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc("transactionyear"));
	// } else {
	// this.orders.add(Order.desc("transactionyear"));
	// }
	// break;
	// case Weeks:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc("weeks.date"));
	// } else {
	// this.orders.add(Order.desc("weeks.date"));
	// }
	// break;
	// case Years:
	// if (ord.getDirection().equals(StatisticOrderDirection.Asc)) {
	// this.orders.add(Order.asc("years.date"));
	// } else {
	// this.orders.add(Order.desc("years.date"));
	// }
	// break;
	// }
	// }
	// return this;
	// }

	// public AbstractMeasureQueryBuilder withOrderType(OrderType orderType) {
	// this.criteria.add(Restrictions.or(Restrictions.eq("orderType",
	// orderType), Restrictions.isNull("orderType")));
	// return this;
	// }
	//
	// public AbstractMeasureQueryBuilder withRestaurant(Restaurant restaurant)
	// {
	// this.criteria
	// .add(Restrictions.or(Restrictions.eq("idResto", restaurant.getId()),
	// Restrictions.isNull("idResto")));
	// return this;
	// }

	// public AbstractMeasureQueryBuilder withSumTotalOrder() {
	// this.projections
	// .add(Projections.alias(Projections.sum("orderTotal"),
	// MeasureType.TotalAmountOrder.getSqlName()));
	// return this;
	// }

	public AbstractMeasureQueryBuilder withTo(Date from, Map<DimensionTypeDefaut, DimensionValue> dimensions) {
		if (dimensions.containsKey(DimensionTypeDefaut.Period)) {
			DimensionValuePeriod period = (DimensionValuePeriod) dimensions.get(DimensionTypeDefaut.Period);
			switch (period) {
			case Minute:
				createAlias("minutes", "minutes", JoinType.FULL_JOIN);
				this.criteria.add(Restrictions.le("minutes.date", from));
				break;
			case Hour:
				createAlias("hours", "hours", JoinType.FULL_JOIN);
				this.criteria.add(Restrictions.le("hours.date", from));
				break;
			case Day:
				createAlias("days", "days", JoinType.FULL_JOIN);
				this.criteria.add(Restrictions.le("days.date", from));
				break;
			case Month:
				createAlias("months", "months", JoinType.FULL_JOIN);
				this.criteria.add(Restrictions.le("months.date", from));
				break;
			case Week:
				createAlias("weeks", "weeks", JoinType.FULL_JOIN);
				this.criteria.add(Restrictions.le("weeks.date", from));
				break;
			case Year:
				createAlias("years", "years", JoinType.FULL_JOIN);
				this.criteria.add(Restrictions.le("years.date", from));
				break;
			}
		}
		// TODO
		// else if
		// (dimensions.containsKey(DimensionTypeDefaut.PeriodTransaction)) {
		// DimensionPeriodTransationValue period =
		// (DimensionPeriodTransationValue) dimensions
		// .get(DimensionTypeDefaut.PeriodTransaction);
		// switch (period) {
		// case Minute:
		// this.criteria.add(Restrictions.le("transactionminute", from));
		// break;
		// case Hour:
		// this.criteria.add(Restrictions.le("transactionhour", from));
		// break;
		// case Day:
		// this.criteria.add(Restrictions.le("transactionday", from));
		// break;
		// case Month:
		// this.criteria.add(Restrictions.le("transactionmonth", from));
		// break;
		// case Week:
		// this.criteria.add(Restrictions.le("transactionweek", from));
		// break;
		// case Year:
		// this.criteria.add(Restrictions.le("transactionyear", from));
		// break;
		// }
		// } else {
		// this.criteria.add(Restrictions.le("transactionminute", from));
		// }
		return this;
	}
}
