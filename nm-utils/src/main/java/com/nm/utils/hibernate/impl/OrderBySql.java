package com.nm.utils.hibernate.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;
/**
 * 
 * @author Nabil
 *
 */
public class OrderBySql extends Order {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sqlFormula;

	/**
	 * Constructor for Order.
	 * 
	 * @param sqlFormula
	 *            an SQL formula that will be appended to the resulting SQL
	 *            query
	 */
	protected OrderBySql(String sqlFormula,boolean b) {
		super(sqlFormula, b);
		this.sqlFormula = sqlFormula;
	}

	public String toString() {
		if(isAscending()){
			return sqlFormula +" ASC";
		}else{
			return sqlFormula +" DESC";
		}
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
		return sqlFormula;
	}

	/**
	 * Custom order
	 * 
	 * @param sqlFormula
	 *            an SQL formula that will be appended to the resulting SQL
	 *            query
	 * @return Order
	 */
	public static Order asc(String sqlFormula) {
		return new OrderBySql(sqlFormula,true);
	}
	
	public static Order desc(String sqlFormula) {
		return new OrderBySql(sqlFormula,false);
	}
}
