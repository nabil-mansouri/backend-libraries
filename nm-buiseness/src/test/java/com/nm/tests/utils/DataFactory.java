package com.nm.tests.utils;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nm.cms.dtos.CmsDtoContentsTextForm;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.geo.dtos.AddressDtoImpl;
import com.nm.geo.dtos.AdressDto;
import com.nm.products.dtos.forms.CategoryFormDto;
import com.nm.products.dtos.forms.IngredientFormDto;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.forms.ProductPartFormDto;
import com.nm.products.soa.SoaProductDefinition;
import com.nm.shop.dtos.ShopFormDto;
import com.nm.shop.soa.SoaShop;

/**
 * 
 * @author nabilmansouri
 *
 */
@Component
public class DataFactory {
	@Autowired
	protected SoaShop soaRestaurant;
	@Autowired
	private SoaProductDefinition soaProductDefinition;

	public IngredientFormDto createIngredien() {
		IngredientFormDto form = soaProductDefinition.createIngredient();
		for (CmsDtoContentsTextForm content : form.getCms().getContents()) {
			content.setName("ING");
			content.setDescription("DESC");
			content.addKeyword("KEY1").addKeyword("KEY2");
		}
		form.getCms().setImg(new AppDataDtoImpl(""));
		return form;
	}

	public ProductFormDto createProduct() {
		ProductFormDto form = soaProductDefinition.createProduct();
		for (CmsDtoContentsTextForm content : form.getCms().getContents()) {
			content.setName("PRODUCT");
			content.setDescription("DESC");
			content.addKeyword("KEY1").addKeyword("KEY2");
		}
		form.getCms().setImg(new AppDataDtoImpl(""));
		return form;
	}

	public ProductFormDto createProduct(int nbParts) {
		ProductFormDto form = soaProductDefinition.createProduct();
		form.getParts().clear();
		if (nbParts > 0) {
			form.setHasProducts(true);
		}
		for (CmsDtoContentsTextForm content : form.getCms().getContents()) {
			content.setName("PRODUCT");
			content.setDescription("DESC");
			content.addKeyword("KEY1").addKeyword("KEY2");
		}
		form.getCms().setImg(new AppDataDtoImpl(""));
		for (int i = 0; i < nbParts; i++) {
			ProductPartFormDto p = new ProductPartFormDto();
			p.setName("PART" + i);
			form.getParts().add(p);
		}
		return form;
	}

	public CategoryFormDto createCategory() {
		return createCategory(null);
	}

	public CategoryFormDto createCategory(Long parent) {
		CategoryFormDto form = soaProductDefinition.createCategory();
		if (parent != null) {
			form = soaProductDefinition.createCategory(parent);
		}
		for (CmsDtoContentsTextForm content : form.getCms().getContents()) {
			content.setName("CAT");
			content.setDescription("DESC");
			content.addKeyword("KEY1").addKeyword("KEY2");
		}
		form.getCms().setImg(new AppDataDtoImpl(""));
		return form;
	}

	public AdressDto createAdress() throws Exception {
		ShopFormDto r = soaRestaurant.createShop();
		AdressDto form = r.getAddress();
		return form;
	}

	public AdressDto createAdress(AddressDtoImpl form) {
		form.setComplement("Complement...").setDetails(new JSONObject()).setGeocode("Geocode...").setName("Name...");
		form.getComponents().setCountry("Country...").setLatitude(1d).setLocality("City").setLongitude(1d)
				.setPostal("71200").setStreet("Street");
		return form;
	}

	public ShopFormDto createRestaurant() throws Exception {
		ShopFormDto form = soaRestaurant.createShop();
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
