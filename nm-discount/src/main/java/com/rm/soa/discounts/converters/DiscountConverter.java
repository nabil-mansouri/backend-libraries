package com.rm.soa.discounts.converters;

import java.util.Collection;

import com.rm.app.ModelOptions;
import com.rm.contract.discounts.beans.DiscountFormBean;
import com.rm.model.discounts.DiscountDefinition;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public interface DiscountConverter {

	public DiscountDefinition convert(DiscountDefinition discount, DiscountFormBean bean) throws NoDataFoundException;

	public DiscountFormBean convert(DiscountDefinition discount, String lang, Collection<ModelOptions> options) throws NoDataFoundException;

}