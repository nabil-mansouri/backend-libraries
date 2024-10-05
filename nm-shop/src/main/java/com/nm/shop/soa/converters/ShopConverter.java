package com.nm.shop.soa.converters;

import com.nm.shop.dtos.ShopFormDto;
import com.nm.shop.dtos.ShopViewDto;
import com.nm.shop.model.Shop;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil
 *
 */
public interface ShopConverter {

	public ShopViewDto toDto(Shop resto, OptionsList options);

	public ShopFormDto toFormDto(Shop resto, OptionsList options) throws DtoConvertException;

	public Shop toEntity(ShopFormDto form, OptionsList options) throws DtoConvertException;

}