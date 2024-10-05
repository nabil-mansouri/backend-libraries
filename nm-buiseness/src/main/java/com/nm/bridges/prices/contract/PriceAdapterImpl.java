package com.nm.bridges.prices.contract;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nm.bridges.SoaBridgeConfig;
import com.nm.bridges.prices.PriceFormExtraOptions;
import com.nm.bridges.prices.computer.PriceComputerBuilderImpl;
import com.nm.bridges.prices.dao.DaoPriceValueFiltersView;
import com.nm.bridges.prices.dtos.CustomPriceFilterDto;
import com.nm.bridges.prices.dtos.OrderTypeFormDto;
import com.nm.bridges.prices.models.filters.PriceValueFiltersView;
import com.nm.bridges.prices.queries.CustomPriceQueryBuilder;
import com.nm.bridges.prices.queries.CustomPriceSubjectQueryBuilder;
import com.nm.bridges.prices.queries.CustomPriceValueQueryBuilder;
import com.nm.bridges.prices.queries.PriceValueFilterViewQueryBuilder;
import com.nm.prices.contract.ContractPriceFilterViewModel;
import com.nm.prices.contract.PriceAdapterContract;
import com.nm.prices.contract.PriceFormConverter;
import com.nm.prices.contract.PriceViewConverter;
import com.nm.prices.dao.impl.PriceQueryBuilder;
import com.nm.prices.dao.impl.PriceSubjectQueryBuilder;
import com.nm.prices.dao.impl.PriceValueQueryBuilder;
import com.nm.prices.dtos.constants.PriceFormOptions;
import com.nm.prices.dtos.filters.PriceFilterDto;
import com.nm.prices.soa.computers.PriceComputerFilterBuilder;
import com.nm.products.dao.impl.ProductDefinitionQueryBuilder;
import com.nm.products.soa.SoaProductDefinition;
import com.nm.shop.dao.impl.ShopQueryBuilder;
import com.nm.shop.soa.SoaShop;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author nabilmansouri
 *
 */
@Component
public class PriceAdapterImpl implements PriceAdapterContract {
	@Autowired
	private SoaShop soaRestaurant;
	@Autowired
	private SoaProductDefinition soaProductDefinition;
	@Autowired
	private SoaBridgeConfig soaOrderType;
	@Autowired
	private DaoPriceValueFiltersView daoView;
	@Autowired
	private PriceFormConverter formConverter;
	@Autowired
	private PriceViewConverter viewConverter;

	public PriceFormConverter getFormConverter() {
		return formConverter;
	}

	public PriceViewConverter getViewConverter() {
		return viewConverter;
	}

	public OptionsList getAllOptions() {
		return new OptionsList().withOption(PriceFormOptions.values()).withOption(PriceFormExtraOptions.values());
	}

	public PriceFilterDto createFilter() {
		OptionsList options = new OptionsList();
		CustomPriceFilterDto filter = new CustomPriceFilterDto();
		filter.getAllProducts().addAll(soaProductDefinition.fetch(ProductDefinitionQueryBuilder.get(), options));
		filter.getAllRestaurants().addAll(soaRestaurant.fetch(ShopQueryBuilder.get(), options));
		for (OrderTypeFormDto o : soaOrderType.getSelectedOrderTypes()) {
			if (o.isSelected()) {
				filter.getAllTypes().add(o.getOrderType());
			}
		}
		return filter;
	}

	public PriceQueryBuilder buildQuery() {
		return new CustomPriceQueryBuilder();
	}

	public PriceSubjectQueryBuilder buildSubjectQuery() {
		return CustomPriceSubjectQueryBuilder.get();
	}

	public PriceAdapterImpl() {
	}

	public PriceValueQueryBuilder buildValueQuery() {
		return CustomPriceValueQueryBuilder.get();
	}

	public PriceComputerFilterBuilder buildComputer() {
		return new PriceComputerBuilderImpl();
	}

	public Collection<ContractPriceFilterViewModel> fetch(PriceQueryBuilder query) {
		Collection<PriceValueFiltersView> prices = daoView
				.find(PriceValueFilterViewQueryBuilder.get().withRoot(true).withPrice(query));
		return new ArrayList<ContractPriceFilterViewModel>(prices);
	}

	public Collection<ContractPriceFilterViewModel> fetch(PriceValueQueryBuilder query) {
		Collection<PriceValueFiltersView> prices = daoView
				.find(PriceValueFilterViewQueryBuilder.get().withRoot(true).withValue(query));
		return new ArrayList<ContractPriceFilterViewModel>(prices);
	}

	public Collection<ContractPriceFilterViewModel> fetch(PriceFilterDto filter) {
		PriceValueFilterViewQueryBuilder query = PriceValueFilterViewQueryBuilder.get().withFilter(filter)
				.withRoot(true);
		Collection<PriceValueFiltersView> prices = daoView.find(query);
		return new ArrayList<ContractPriceFilterViewModel>(prices);
	}

	public Collection<ContractPriceFilterViewModel> fetch(Long id) {
		Collection<PriceValueFiltersView> prices = daoView
				.find(PriceValueFilterViewQueryBuilder.get().withRoot(true).withPrice(id));
		return new ArrayList<ContractPriceFilterViewModel>(prices);
	}
}
