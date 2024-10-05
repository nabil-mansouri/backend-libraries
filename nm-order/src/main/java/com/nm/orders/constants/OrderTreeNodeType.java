package com.nm.orders.constants;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nm.utils.json.EnumJsonConverterIn;
import com.nm.utils.json.EnumJsonConverterOut;

/**
 * 
 * @author Nabil
 * 
 */
@JsonSerialize(using = EnumJsonConverterIn.class)
@JsonDeserialize(using = EnumJsonConverterOut.class)
public interface OrderTreeNodeType {
 
	public enum OrderTreeNodeTypeDefault implements OrderTreeNodeType {
		Product, Discount, Caracteristics;

	}
}