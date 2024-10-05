package com.rm.soa.statistics.transformers;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.transform.ResultTransformer;

import com.nm.app.stats.DimensionType;
import com.nm.app.stats.DimensionValue;
import com.nm.app.stats.MeasureType;
import com.rm.contract.statistics.beans.StatisticResultNodeBean;
import com.rm.contract.statistics.beans.StatisticResultNodeDimensionBean;
import com.rm.contract.statistics.beans.StatisticResultNodeMeasureBean;
import com.rm.contract.statistics.beans.StatisticsFilterBean;
import com.rm.contract.statistics.exceptions.NoDimensionGeneratorException;

/**
 * 
 * @author Nabil
 * 
 */
public class StatisticsResultTransformer implements ResultTransformer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final StatisticsFilterBean filter;

	public StatisticsResultTransformer(StatisticsFilterBean filter) throws NoDimensionGeneratorException {
		// this.context = resultBuilder.prepare(filter);
		this.filter = (filter);
	}

	// public Object transformTuple(Object[] tuple, String[] aliases) {
	// // Dimensions
	// Map<DimensionValue, Object> dimensions = new HashMap<DimensionValue,
	// Object>();
	// for (DimensionType type : context.getFilter().getDimensions().keySet()) {
	// DimensionValue dimVal = context.getFilter().getDimensions().get(type);
	// Object value = tuple[ArrayUtils.indexOf(aliases, type.getSqlName())];
	// dimensions.put(dimVal, value);
	// }
	// // Measures
	// Map<MeasureType, Object> measures = new HashMap<MeasureType, Object>();
	// for (MeasureType m : context.getFilter().getMeasureTypes()) {
	// Object value = tuple[ArrayUtils.indexOf(aliases, m.getSqlName())];
	// measures.put(m, value);
	// }
	// return resultBuilder.pushMeasure(context, dimensions, measures);
	// }
	public Object transformTuple(Object[] tuple, String[] aliases) {
		StatisticResultNodeBean node = new StatisticResultNodeBean();
		// Dimensions
		for (DimensionType type : filter.getDimensions().keySet()) {
			DimensionValue dimVal = filter.getDimensions().get(type);
			Object value = tuple[ArrayUtils.indexOf(aliases, type.getSqlName())];
			//
			StatisticResultNodeDimensionBean dim = new StatisticResultNodeDimensionBean();
			dim.setDimension(dimVal);
			dim.setType(dimVal.getType());
			dim.setValue(value);
			node.getDimensions().add(dim);
		}
		// Measures
		for (MeasureType m : filter.getMeasureTypes()) {
			Object value = tuple[ArrayUtils.indexOf(aliases, m.getSqlName())];
			//
			StatisticResultNodeMeasureBean dim = new StatisticResultNodeMeasureBean();
			dim.setDimension(m);
			dim.setValue(value);
			node.getMeasures().add(dim);
		}
		//
		return node;
	}

	public List<?> transformList(@SuppressWarnings("rawtypes") List collection) {
		return collection;
	}

	// public StatisticContextBean getContext() {
	// return context;
	// }

}
