package com.nm.bridges.prices.constants;

import com.nm.prices.dtos.constants.PriceFilterValueEnum;
import com.nm.utils.json.EnumJsonConvertAnnotation;

/**
 * 
 * @author nabilmansouri
 *
 */
@EnumJsonConvertAnnotation()
public enum PriceFilterValueEnumExtra implements PriceFilterValueEnum {
	LimitRestaurant, LimitOrderType;
	 

}
