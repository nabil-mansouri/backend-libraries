package com.nm.shop.tests;

import java.util.HashMap;
import java.util.Map;

import com.nm.shop.dtos.ShopFormDto;
import com.nm.shop.dtos.ShopViewDto;
import com.nm.shop.model.Shop;

/**
 * 
 * @author nabilmansouri
 *
 */
public class ScenarioContext {

	private Map<String, ShopFormDto> allShopsForm = new HashMap<String, ShopFormDto>();
	private Map<String, ShopViewDto> allShopsView = new HashMap<String, ShopViewDto>();
	private Map<String, Shop> allShops = new HashMap<String, Shop>();
	public static final String RESTO1 = "RESTO1";
	public static final String RESTO2 = "RESTO2";

	public Map<String, ShopFormDto> getAllShopsForm() {
		return allShopsForm;
	}

	public Map<String, ShopViewDto> getAllShopsView() {
		return allShopsView;
	}

	public Map<String, Shop> getAllShops() {
		return allShops;
	}
}
