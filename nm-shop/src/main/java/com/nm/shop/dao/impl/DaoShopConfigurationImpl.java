package com.nm.shop.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nm.shop.constants.ShopConfigType;
import com.nm.shop.dao.DaoShopConfiguration;
import com.nm.shop.model.ShopConfiguration;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoShopConfigurationImpl extends AbstractGenericDao<ShopConfiguration, Long>
		implements DaoShopConfiguration {

	@Override
	protected Class<ShopConfiguration> getClassName() {
		return ShopConfiguration.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	@SuppressWarnings("unchecked")
	public List<ShopConfiguration> findByType(ShopConfigType type) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ShopConfiguration.class);
		criteria.add(Restrictions.eq("type", type));
		return (List<ShopConfiguration>) getHibernateTemplate().findByCriteria(criteria);
	}
}
