package com.nm.shop.soa.converters;

import com.nm.cms.dtos.CmsDtoContentsComposedForm;
import com.nm.cms.dtos.CmsDtoContentsComposedView;
import com.nm.cms.models.CmsContentsComposed;
import com.nm.geo.dtos.AddressDtoImpl;
import com.nm.geo.models.Address;
import com.nm.geo.models.AddressSubjectSimple;
import com.nm.shop.constants.ShopOptions;
import com.nm.shop.dao.DaoShop;
import com.nm.shop.dtos.ShopFormDto;
import com.nm.shop.dtos.ShopViewDto;
import com.nm.shop.model.Shop;
import com.nm.shop.soa.checker.ShopChecker;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

import javassist.NotFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public class ShopConverterImpl implements ShopConverter {
	private ShopChecker restaurantChecker;
	private DaoShop daoRestaurant;
	private DtoConverterRegistry registry;
	private DtoConverter<CmsDtoContentsComposedForm, CmsContentsComposed> convertersF;
	private DtoConverter<CmsDtoContentsComposedView, CmsContentsComposed> convertersV;

	public void setDaoRestaurant(DaoShop daoRestaurant) {
		this.daoRestaurant = daoRestaurant;
	}

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public void setRestaurantChecker(ShopChecker restaurantChecker) {
		this.restaurantChecker = restaurantChecker;
	}

	private DtoConverter<CmsDtoContentsComposedView, CmsContentsComposed> getConverterV() throws NotFoundException {
		if (this.convertersV == null) {
			convertersV = registry.search(CmsDtoContentsComposedView.class, CmsContentsComposed.class);
		}
		return this.convertersV;
	}

	private DtoConverter<CmsDtoContentsComposedForm, CmsContentsComposed> getConverterF() throws NotFoundException {
		if (this.convertersF == null) {
			convertersF = registry.search(CmsDtoContentsComposedForm.class, CmsContentsComposed.class);
		}
		return this.convertersF;
	}

	public ShopViewDto toDto(Shop resto, OptionsList options) {
		try {
			ShopViewDto view = new ShopViewDto();
			view.setId(resto.getId());
			view.setCms(getConverterV().toDto(resto.getContent(), options));
			view.setCreatedAt(resto.getCreated());
			//
			if (options.contains(ShopOptions.States)) {
				restaurantChecker.convert(view, view.getState());
			}
			return view;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public Shop toEntity(ShopFormDto form, OptionsList options) throws DtoConvertException {
		try {
			Shop resto = new Shop();
			if (form.getId() != null) {
				resto = daoRestaurant.get(form.getId());
			}
			//
			resto.setContent(getConverterF().toEntity(form.getCms(), options));
			DtoConverter<AddressDtoImpl, Address> converter = registry.search(AddressDtoImpl.class, Address.class);
			resto.setAddress(converter.toEntity(form.getAddress(), options));
			// TODO use subject? this is a fix
			if (resto.getAddress().getSubject() == null) {
				AddressSubjectSimple s = new AddressSubjectSimple();
				AbstractGenericDao.get(AddressSubjectSimple.class).save(s);
				resto.getAddress().setSubject(s);
			}
			return resto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public ShopFormDto toFormDto(Shop resto, OptionsList options) throws DtoConvertException {
		try {
			DtoConverter<AddressDtoImpl, Address> converter = registry.search(AddressDtoImpl.class, Address.class);
			ShopFormDto form = new ShopFormDto();
			form.setId(resto.getId());
			form.setCms(getConverterF().toDto(resto.getContent(), options));
			form.setAddress(converter.toDto(resto.getAddress(), options));
			if (resto.getId() == null) {
				form.setIsnew(true);
			}
			return form;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
