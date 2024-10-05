package com.nm.prices.soa.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nm.config.SoaModuleConfig;
import com.nm.config.constants.ModuleConfigKeyDefault;
import com.nm.config.dtos.ModuleConfigDto;
import com.nm.prices.contract.ContractPriceFilterViewModel;
import com.nm.prices.contract.ContractPriceViewDto;
import com.nm.prices.contract.PriceAdapterContract;
import com.nm.prices.dao.DaoPrice;
import com.nm.prices.dao.impl.PriceQueryBuilder;
import com.nm.prices.dao.impl.PriceValueQueryBuilder;
import com.nm.prices.dtos.constants.PriceSelector;
import com.nm.prices.dtos.filters.PriceFilterDto;
import com.nm.prices.dtos.forms.PriceFormDto;
import com.nm.prices.dtos.forms.PriceSelectorBean;
import com.nm.prices.model.Price;
import com.nm.prices.model.PriceComposed;
import com.nm.prices.soa.SoaPrice;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public class SoaPriceImpl implements SoaPrice {

	private DaoPrice daoPrice;
	private SoaModuleConfig soaModuleConfig;
	private PriceAdapterContract priceAdapter;
	//
	private ObjectMapper mapper = new ObjectMapper();

	public void setDaoPrice(DaoPrice daoPrice) {
		this.daoPrice = daoPrice;
	}

	public void setPriceAdapter(PriceAdapterContract priceAdapter) {
		this.priceAdapter = priceAdapter;
	}

	public void setSoaModuleConfig(SoaModuleConfig soaModuleConfig) {
		this.soaModuleConfig = soaModuleConfig;
	}

	public PriceFilterDto createFilter() {
		return priceAdapter.createFilter();
	}

	public PriceFormDto createPrice() throws NoDataFoundException {
		PriceFormDto form = priceAdapter.getFormConverter().toDto(new PriceComposed().setRoot(true),
				this.priceAdapter.getAllOptions());
		return form;
	}

	public PriceFormDto editPrice(Long id) throws NoDataFoundException {
		Price price = daoPrice.get(id);
		return priceAdapter.getFormConverter().toDto(price, this.priceAdapter.getAllOptions());
	}

	public Collection<ContractPriceViewDto> fetch(PriceFilterDto filter, OptionsList options)
			throws NoDataFoundException {
		Collection<ContractPriceFilterViewModel> prices = this.priceAdapter.fetch(filter);
		Collection<ContractPriceViewDto> dtos = priceAdapter.getViewConverter().toDto(prices, options);
		return dtos;
	}

	public Collection<ContractPriceViewDto> fetch(PriceQueryBuilder query, OptionsList options)
			throws NoDataFoundException {
		Collection<ContractPriceFilterViewModel> prices = this.priceAdapter.fetch(query);
		Collection<ContractPriceViewDto> dtos = priceAdapter.getViewConverter().toDto(prices, options);
		return dtos;
	}

	public Collection<ContractPriceViewDto> fetch(PriceValueQueryBuilder query, OptionsList options)
			throws NoDataFoundException {
		Collection<ContractPriceFilterViewModel> prices = this.priceAdapter.fetch(query);
		Collection<ContractPriceViewDto> dtos = priceAdapter.getViewConverter().toDto(prices, options);
		return dtos;
	}

	public Collection<ContractPriceViewDto> fetch(Long id, OptionsList options) throws NoDataFoundException {
		Collection<ContractPriceFilterViewModel> prices = this.priceAdapter.fetch(id);
		Collection<ContractPriceViewDto> dtos = priceAdapter.getViewConverter().toDto(prices, options);
		return dtos;
	}

	public PriceFormDto refresh(PriceFormDto form, OptionsList options) throws NoDataFoundException {
		return priceAdapter.getFormConverter().toDto(form, options);
	}

	public PriceFormDto saveOrUpdate(PriceFormDto bean, OptionsList options) throws NoDataFoundException {
		Price price = priceAdapter.getFormConverter().toEntity(bean, options);
		daoPrice.saveOrUpdate(price);
		bean.setId(price.getId());
		return bean;
	}

	public Price removePrice(Long id) {
		try {
			Price p = daoPrice.loadById(id);
			daoPrice.delete(p);
			return p;
		} catch (NoDataFoundException e) {
			// ALREADY DELETED
		}
		return null;
	}

	public Collection<PriceSelectorBean> getPriceSelectors() {
		try {
			ModuleConfigDto config = soaModuleConfig.fetch(ModuleConfigKeyDefault.PriceSelector);
			Collection<PriceSelector> lists = mapper.readValue(config.getPayload(),
					new TypeReference<List<PriceSelector>>() {
					});
			Collection<PriceSelectorBean> res = new ArrayList<PriceSelectorBean>();
			for (PriceSelector o : PriceSelector.values()) {
				res.add(new PriceSelectorBean(o).setSelected(lists.contains(o)));
			}
			return res;
		} catch (Exception e) {
			return new ArrayList<PriceSelectorBean>();
		}
	}

	public PriceSelector getPriceSelector() throws NoDataFoundException {
		Collection<PriceSelectorBean> p = getPriceSelectors();
		for (PriceSelectorBean o : p) {
			if (o.isSelected()) {
				return o.getSelector();
			}
		}
		throw new NoDataFoundException("Could not found any price selector");
	}

	public void setSelectedPriceSelectors(Collection<PriceSelector> form) {
		Collection<PriceSelectorBean> res = new ArrayList<PriceSelectorBean>();
		for (PriceSelector o : form) {
			res.add(new PriceSelectorBean(o).setSelected(true));
		}
		setSelectedPriceSelectorBeans(res);
	}

	public void setSelectedPriceSelectorBeans(Collection<PriceSelectorBean> form) {
		Collection<PriceSelector> t = new ArrayList<PriceSelector>();
		for (PriceSelectorBean tt : form) {
			if (tt.isSelected()) {
				t.add(tt.getSelector());
			}
		}
		try {
			soaModuleConfig.setText(ModuleConfigKeyDefault.PriceSelector, mapper.writeValueAsString(t));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
