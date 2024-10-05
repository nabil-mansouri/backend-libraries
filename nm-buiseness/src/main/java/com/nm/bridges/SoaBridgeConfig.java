package com.nm.bridges;

import java.util.Collection;

import com.nm.bridges.prices.OrderType;
import com.nm.bridges.prices.dtos.OrderTypeFormDto;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaBridgeConfig {
	public Collection<OrderTypeFormDto> getOrderTypes();

	public Collection<OrderTypeFormDto> getSelectedOrderTypes();

	public void hasOrderType() throws NoDataFoundException;

	public void setSelectedOrderTypes(Collection<OrderType> form);

	public void setSelectedOrderTypesBean(Collection<OrderTypeFormDto> form);
}
