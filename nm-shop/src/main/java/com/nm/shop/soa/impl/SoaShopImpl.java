package com.nm.shop.soa.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.nm.cms.constants.CmsOptions;
import com.nm.shop.dao.DaoShop;
import com.nm.shop.dao.impl.ShopQueryBuilder;
import com.nm.shop.dtos.ShopFormDto;
import com.nm.shop.dtos.ShopViewDto;
import com.nm.shop.model.Shop;
import com.nm.shop.soa.SoaShop;
import com.nm.shop.soa.converters.ShopConverter;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public class SoaShopImpl implements SoaShop {

	//
	private DaoShop daoRestaurant;
	private ShopConverter restaurantConverter;

	public void setDaoRestaurant(DaoShop daoRestaurant) {
		this.daoRestaurant = daoRestaurant;
	}

	public void setRestaurantConverter(ShopConverter restaurantConverter) {
		this.restaurantConverter = restaurantConverter;
	}

	//
	public ShopFormDto createShop() throws DtoConvertException {
		ShopFormDto form = restaurantConverter.toFormDto(new Shop(), new OptionsList().withOption(CmsOptions.FullForm));
		return form;
	}

	public ShopFormDto editShop(Long id) throws NoDataFoundException, DtoConvertException {
		OptionsList options = new OptionsList().withOption(CmsOptions.FullForm);
		Shop resto = daoRestaurant.loadById(id);
		return restaurantConverter.toFormDto(resto, options);
	}

	public Collection<ShopViewDto> fetch(ShopQueryBuilder query, OptionsList options) {
		Collection<ShopViewDto> response = new ArrayList<ShopViewDto>();
		Collection<Shop> restos = daoRestaurant.find(query);
		for (Shop resto : restos) {
			response.add(restaurantConverter.toDto(resto, options));
		}
		return response;
	}

	public Shop removeShop(Long idresto) throws NoDataFoundException {
		Shop resto = daoRestaurant.loadById(idresto);
		daoRestaurant.delete(resto);
		return resto;
	}

	public ShopFormDto saveOrUpdate(ShopFormDto form, OptionsList options)
			throws NoDataFoundException, DtoConvertException {
		Shop resto = this.restaurantConverter.toEntity(form, options);
		daoRestaurant.saveOrUpdate(resto);
		form.setId(resto.getId());
		return form;
	}

}
