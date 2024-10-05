package com.nm.shop.soa;

import java.util.Collection;

import com.nm.shop.dao.impl.ShopQueryBuilder;
import com.nm.shop.dtos.ShopFormDto;
import com.nm.shop.dtos.ShopViewDto;
import com.nm.shop.model.Shop;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaShop {

	public ShopFormDto createShop() throws DtoConvertException;

	public ShopFormDto editShop(Long id) throws NoDataFoundException, DtoConvertException;

	public Collection<ShopViewDto> fetch(ShopQueryBuilder query, OptionsList options);

	public Shop removeShop(Long idResto) throws NoDataFoundException;

	public ShopFormDto saveOrUpdate(ShopFormDto resto, OptionsList options)
			throws NoDataFoundException, DtoConvertException;

}
