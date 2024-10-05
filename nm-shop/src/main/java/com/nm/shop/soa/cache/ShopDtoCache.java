package com.nm.shop.soa.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.nm.shop.dao.DaoShop;
import com.nm.shop.dtos.ShopViewDto;
import com.nm.shop.model.Shop;
import com.nm.shop.soa.converters.ShopConverter;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author nabilmansouri
 *
 */
public class ShopDtoCache extends ConcurrentHashMap<Long, ShopViewDto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ShopConverter converter;
	private final OptionsList options;
	private final DaoShop dao;

	public ShopDtoCache(ShopConverter converter, DaoShop dao) {
		this.converter = converter;
		this.options = new OptionsList();
		this.dao = dao;
	}

	public ShopDtoCache(ShopConverter converter, DaoShop dao, OptionsList options) {
		this.converter = converter;
		this.options = options;
		this.dao = dao;
	}

	protected void push(ShopViewDto r) {
		super.put(r.getId(), r);
	}

	protected ShopViewDto getSafe(Shop r) {
		if (exists(r)) {
			return super.get(r.getId());
		} else {
			ShopViewDto dto = converter.toDto(r, options);
			push(dto);
			return dto;
		}
	}

	protected ShopViewDto getSafe(ShopViewDto r) {
		if (exists(r)) {
			return super.get(r.getId());
		} else {
			push(r);
			return r;
		}
	}

	public ShopViewDto get(Long r) {
		return this.getSafe(dao.load(r));
	}

	public ShopViewDto get(Shop r) {
		return this.getSafe(r);
	}

	public ShopViewDto get(ShopViewDto r) {
		return this.getSafe(r);
	}

	protected boolean exists(Shop r) {
		return this.containsKey(r.getId());
	}

	protected boolean exists(ShopViewDto r) {
		return this.containsKey(r.getId());
	}
}
