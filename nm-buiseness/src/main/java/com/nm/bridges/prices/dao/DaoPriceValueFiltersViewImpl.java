package com.nm.bridges.prices.dao;

import org.springframework.stereotype.Repository;

import com.nm.bridges.prices.models.filters.PriceValueFiltersView;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
@Repository
public class DaoPriceValueFiltersViewImpl extends AbstractGenericDao<PriceValueFiltersView, Long>
		implements DaoPriceValueFiltersView {

	@Override
	protected Class<PriceValueFiltersView> getClassName() {
		return PriceValueFiltersView.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

}
