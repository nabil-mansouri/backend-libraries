package com.rm.soa.statistics.computers.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nm.app.stats.AbstractMeasureQueryBuilder;
import com.rm.contract.statistics.beans.StatisticContextBean;
import com.rm.contract.statistics.beans.StatisticResultNodeBean;
import com.rm.contract.statistics.exceptions.NoDimensionGeneratorException;
import com.rm.dao.statistics.impl.MeasureOrderQueryBuilder;
import com.rm.soa.statistics.computers.StatisticComputer;
import com.rm.soa.statistics.transformers.StatisticsResultTransformer;

/**
 * 
 * @author Nabil
 * 
 */
@Qualifier(StatisticComputer.StatisticComputer)
@Component(StatisticComputer.StatisticOrderComputer)
public class StatisticOrderComputerImpl implements StatisticComputer {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Transactional(readOnly = true)
	public void compute(StatisticContextBean context) throws NoDimensionGeneratorException {
		StatisticsResultTransformer tr = new StatisticsResultTransformer(context.getFilter());
		//
		AbstractMeasureQueryBuilder query = MeasureOrderQueryBuilder.get().withFilter(context.getFilter());
		if (query.hasLimit()) {
			Collection<?> objects = (hibernateTemplate.findByCriteria(query.getQuery().setResultTransformer(tr),
					query.getFirst(true).intValue(), query.getLimit().intValue()));
			for (Object o : objects) {
				context.getResults().add((StatisticResultNodeBean) o);
			}
		} else {
			Collection<?> objects = (hibernateTemplate.findByCriteria(query.getQuery().setResultTransformer(tr)));
			for (Object o : objects) {
				context.getResults().add((StatisticResultNodeBean) o);
			}
		}
	}
}
