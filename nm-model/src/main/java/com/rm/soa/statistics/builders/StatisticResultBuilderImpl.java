package com.rm.soa.statistics.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.nm.app.stats.DimensionType;
import com.nm.app.stats.DimensionValue;
import com.nm.app.stats.MeasureType;
import com.rm.contract.statistics.beans.DimensionValueBean;
import com.rm.contract.statistics.beans.StatisticContextBean;
import com.rm.contract.statistics.beans.StatisticResultNodeBean;
import com.rm.contract.statistics.beans.StatisticResultNodeDimensionBean;
import com.rm.contract.statistics.beans.StatisticResultNodeMeasureBean;
import com.rm.contract.statistics.beans.StatisticsFilterBean;
import com.rm.contract.statistics.exceptions.NoDimensionComparatorException;
import com.rm.contract.statistics.exceptions.NoDimensionGeneratorException;
import com.rm.contract.statistics.exceptions.TooMuchDateException;
import com.rm.soa.statistics.comparators.DimensionComparatorFactory;
import com.rm.soa.statistics.generators.DimensionGeneratorFactory;

/**
 * 
 * @author Nabil
 * 
 */
@Component
public class StatisticResultBuilderImpl implements StatisticResultBuilder {
	@Autowired
	private DimensionGeneratorFactory dimensionGeneratorFactory;
	@Autowired
	private DimensionComparatorFactory comparatorFactory;
	protected Log log = LogFactory.getLog(getClass());

	private class PredicateFinder implements Predicate<StatisticResultNodeBean> {
		private final Map<DimensionValue, Object> dimensionsValues;

		public PredicateFinder(Map<DimensionValue, Object> pa) {
			this.dimensionsValues = new HashMap<DimensionValue, Object>(pa);
		}

		public boolean apply(StatisticResultNodeBean node) {
			if (dimensionsValues.size() != node.getDimensions().size()) {
				log.warn(String.format("Dimensions size are differents... %s / %s", dimensionsValues, node.getDimensions().size()));
				return false;
			}
			boolean isOk = true;
			Iterator<StatisticResultNodeDimensionBean> it = node.getDimensions().iterator();
			while (isOk && it.hasNext()) {
				StatisticResultNodeDimensionBean dim = it.next();
				Object val1 = dimensionsValues.get(dim.getDimension());
				try {
					isOk = comparatorFactory.generate(dim.getType(), val1, dim.getValue());
				} catch (NoDimensionComparatorException e) {
					log.warn(String.format("No dimension comparator for : %s", dim.getType()));
				}
			}
			return isOk;
		}
	}

	public StatisticContextBean prepare(StatisticsFilterBean filter) throws NoDimensionGeneratorException, TooMuchDateException {
		StatisticContextBean context = new StatisticContextBean(filter);
		List<Set<DimensionValueBean>> map = new ArrayList<Set<DimensionValueBean>>();
		for (DimensionType type : filter.getDimensions().keySet()) {
			DimensionValue value = filter.getDimensions().get(type);
			Set<DimensionValueBean> objects = dimensionGeneratorFactory.generateOrderedSetWithWrapper(type, value, filter);
			map.add(objects);
		}
		// produit cartesien
		Set<List<DimensionValueBean>> cartesian = Sets.cartesianProduct(map);
		for (List<DimensionValueBean> d : cartesian) {
			StatisticResultNodeBean node = new StatisticResultNodeBean();
			for (DimensionValueBean val : d) {
				StatisticResultNodeDimensionBean nodeDim = new StatisticResultNodeDimensionBean();
				nodeDim.setDimension(val.getDimensionValue());
				nodeDim.setType(val.getType());
				nodeDim.setValue(val.getValue());
				node.getDimensions().add(nodeDim);
			}
			context.getResults().add(node);
		}
		return context;
	}

	public StatisticResultNodeBean pushMeasure(StatisticContextBean context, Map<DimensionValue, Object> dimensions, Map<MeasureType, Object> measures) {
		try {
			StatisticResultNodeBean node = Iterables.find(context.getResults(), new PredicateFinder(dimensions));
			for (MeasureType t : measures.keySet()) {
				StatisticResultNodeMeasureBean meas = new StatisticResultNodeMeasureBean();
				meas.setDimension(t);
				meas.setValue(measures.get(t));
				node.getMeasures().add(meas);
			}
			return node;
		} catch (NoSuchElementException e) {
			log.warn(String.format("Statistic node not founded for : %s", dimensions));
			return new StatisticResultNodeBean();
		}
	}

}
