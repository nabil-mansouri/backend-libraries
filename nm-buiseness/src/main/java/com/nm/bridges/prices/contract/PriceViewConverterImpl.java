package com.nm.bridges.prices.contract;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nm.app.currency.SoaDevise;
import com.nm.bridges.prices.dtos.PriceViewDto;
import com.nm.bridges.prices.models.filters.PriceValueFiltersView;
import com.nm.bridges.prices.models.subject.PriceSubjectProduct;
import com.nm.prices.contract.ContractPriceFilterViewModel;
import com.nm.prices.contract.ContractPriceViewDto;
import com.nm.prices.contract.PriceFormConverter;
import com.nm.prices.contract.PriceViewConverter;
import com.nm.prices.model.PriceComposed;
import com.nm.prices.model.values.PriceValueSimple;
import com.nm.products.converter.ProductDefinitionViewConverter;
import com.nm.shop.dao.DaoShop;
import com.nm.shop.soa.cache.ShopDtoCache;
import com.nm.shop.soa.converters.ShopConverter;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
public class PriceViewConverterImpl implements PriceViewConverter {
	protected static Log log = LogFactory.getLog(PriceFormConverter.class);
	@Autowired
	private ShopConverter converter;
	@Autowired
	private ProductDefinitionViewConverter productConverter;
	@Autowired
	private SoaDevise soaDevise;
	@Autowired
	private DaoShop daoShop;

	public Collection<ContractPriceViewDto> toDto(Collection<ContractPriceFilterViewModel> view, OptionsList options)
			throws NoDataFoundException {
		Collection<ContractPriceViewDto> all = new ArrayList<ContractPriceViewDto>();
		final ShopDtoCache restoCache = new ShopDtoCache(converter, daoShop, options);
		for (ContractPriceFilterViewModel v : view) {
			all.add(_toDto(v, options, restoCache));
		}
		return all;
	}

	public ContractPriceViewDto toDto(ContractPriceFilterViewModel view, OptionsList options) throws NoDataFoundException {
		final ShopDtoCache restoCache = new ShopDtoCache(converter, daoShop, options);
		return _toDto(view, options, restoCache);
	}

	private ContractPriceViewDto _toDto(ContractPriceFilterViewModel v, OptionsList options, ShopDtoCache cache)
			throws NoDataFoundException {
		PriceValueFiltersView view = (PriceValueFiltersView) v;
		PriceValueSimple s = (PriceValueSimple) view.getValue();
		final PriceViewDto base = new PriceViewDto();
		base.setValue(s.getValue());
		base.setId(view.getIdPrice());
		base.setCurrency(soaDevise.getDefault());
		if (view.getPrice() instanceof PriceComposed) {
			PriceComposed c = (PriceComposed) view.getPrice();
			base.setCountSupplements((long) c.getChildren().size());
		} else {
			base.setCountSupplements(0l);
		}
		if (view.getSubject() instanceof PriceSubjectProduct) {
			PriceSubjectProduct pSub = (PriceSubjectProduct) view.getSubject();
			base.setProduct(productConverter.toDto(pSub.getProduct(), options));
		}
		//
		base.getFilter().setAllOrders(view.getOrderType() == null);
		base.getFilter().setType(view.getOrderType());
		base.getFilter().setAllRestaurants(view.getIdResto() == null);
		if (view.getIdResto() != null) {
			base.getFilter().setRestaurant(cache.get(view.getIdResto()));
		}
		base.getFilter().setHasFrom(view.getFrom() != null);
		base.getFilter().setFrom(view.getFrom());
		base.getFilter().setHasTo(view.getTo() != null);
		base.getFilter().setTo(view.getTo());
		return base;
	}
}
