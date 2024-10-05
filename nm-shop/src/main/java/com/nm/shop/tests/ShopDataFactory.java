package com.nm.shop.tests;

import org.json.JSONObject;
import org.springframework.util.Assert;

import com.nm.cms.dtos.CmsDtoContentsTextForm;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.geo.dtos.AddressDtoImpl;
import com.nm.geo.dtos.AddressDto;
import com.nm.shop.dao.DaoShop;
import com.nm.shop.dao.impl.ShopQueryBuilder;
import com.nm.shop.dtos.ShopFormDto;
import com.nm.shop.dtos.ShopViewDto;
import com.nm.shop.soa.SoaShop;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author nabilmansouri
 *
 */
public class ShopDataFactory {
	private SoaShop soaShop;
	private DaoShop daoShop;

	public void setDaoShop(DaoShop daoShop) {
		this.daoShop = daoShop;
	}

	public void setSoaShop(SoaShop soaShop) {
		this.soaShop = soaShop;
	}

	public ScenarioContext loadSafe() throws Exception {
		ScenarioContext context = new ScenarioContext();
		loadShops(context);
		return context;
	}

	public void loadShops(ScenarioContext context) throws Exception {
		{
			ShopFormDto prod = soaShop.createShop();
			prod.getCms().setImg(new AppDataDtoImpl("img/resto1.png"));
			prod.getCms().fetchByLang("fr").setDescription("Shop du parc")//
					.addKeyword("resto du parc").setName("Kebab du parc");
			createAdress(prod.getAddress());
			ShopFormDto result = soaShop.saveOrUpdate(prod, new OptionsList("fr"));
			Assert.notNull(result.getId());
			context.getAllShopsForm().put(ScenarioContext.RESTO1, result);

		}
		{
			ShopFormDto prod = soaShop.createShop();
			prod.getCms().setImg(new AppDataDtoImpl("img/resto1.png"));
			prod.getCms().fetchByLang("fr").setDescription("Shop de torcy")//
					.addKeyword("resto torcy").setName("Kebab de torcy");
			createAdress(prod.getAddress());
			ShopFormDto result = soaShop.saveOrUpdate(prod, new OptionsList("fr"));
			Assert.notNull(result.getId());
			context.getAllShopsForm().put(ScenarioContext.RESTO2, result);
		}
		daoShop.flush();
		daoShop.clear();
		for (String key : context.getAllShopsForm().keySet()) {
			Assert.notNull(context.getAllShopsForm().get(key).getId());
			ShopQueryBuilder query = ShopQueryBuilder.get().withId(context.getAllShopsForm().get(key).getId());
			ShopViewDto def = soaShop.fetch(query, new OptionsList("fr")).iterator().next();
			context.getAllShopsView().put(key, def);
			context.getAllShops().put(key, daoShop.get(def.getId()));
		}
	}

	public AddressDto createAdress() throws Exception {
		ShopFormDto r = soaShop.createShop();
		AddressDto form = r.getAddress();
		return form;
	}

	public AddressDto createAdress(AddressDtoImpl form) {
		form.setComplement("Complement...").setDetails(new JSONObject()).setGeocode("Geocode...").setName("Name...");
		form.getComponents().setCountry("Country...").setLatitude(1d).setLocality("City").setLongitude(1d)
				.setPostal("71200").setStreet("Street");
		return form;
	}

	public ShopFormDto createShop() throws Exception {
		ShopFormDto form = soaShop.createShop();
		for (CmsDtoContentsTextForm content : form.getCms().getContents()) {
			content.setName("PRODUCT");
			content.setDescription("DESC");
			content.addKeyword("KEY1").addKeyword("KEY2");
		}
		form.getCms().setImg(new AppDataDtoImpl(""));
		//
		createAdress(form.getAddress());
		return form;
	}
}
