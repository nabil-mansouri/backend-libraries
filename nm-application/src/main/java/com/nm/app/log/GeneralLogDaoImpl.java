package com.nm.app.log;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;

import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class GeneralLogDaoImpl extends AbstractGenericDao<GeneralLog, Long>implements GeneralLogDao {

	public Class<GeneralLog> getClassName() {
		return GeneralLog.class;
	}

	public Long getId(GeneralLog bean) {
		return bean.getId();
	}

	protected List<Order> getOrders() {
		return Arrays.asList(Order.desc("createdAt"));
	}

	public void deleteAll(List<Long> criteria) {
		String sql = String.format("DELETE FROM GeneralLog WHERE id in (%s) ", StringUtils.join(criteria, ","));
		getHibernateTemplate().bulkUpdate(sql);
		getHibernateTemplate().flush();
	}
}
