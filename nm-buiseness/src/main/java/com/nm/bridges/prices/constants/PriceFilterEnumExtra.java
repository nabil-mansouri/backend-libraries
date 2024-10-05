package com.nm.bridges.prices.constants;

import com.nm.prices.dtos.constants.PriceFilterEnum;
import com.nm.utils.json.EnumJsonConvertAnnotation;

/**
 * 
 * @author nabilmansouri
 *
 */
@EnumJsonConvertAnnotation()
public enum PriceFilterEnumExtra implements PriceFilterEnum {
	LimitRestaurants, LimitOrderType;

}
