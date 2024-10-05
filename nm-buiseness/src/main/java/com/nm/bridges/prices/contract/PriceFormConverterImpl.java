package com.nm.bridges.prices.contract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Objects;
import com.nm.bridges.SoaBridgeConfig;
import com.nm.bridges.prices.OrderType;
import com.nm.bridges.prices.PriceFormExtraOptions;
import com.nm.bridges.prices.constants.PriceFilterEnumExtra;
import com.nm.bridges.prices.constants.PriceFilterValueEnumExtra;
import com.nm.bridges.prices.dtos.CustomPriceFormDto;
import com.nm.bridges.prices.dtos.CustomPriceFormFilterDto;
import com.nm.bridges.prices.dtos.OrderTypeFormDto;
import com.nm.bridges.prices.dtos.PriceFormNodeDto;
import com.nm.bridges.prices.dtos.PriceFormNodeFilterDto;
import com.nm.bridges.prices.models.filters.PriceFilterOrderType;
import com.nm.bridges.prices.models.filters.PriceFilterRestaurant;
import com.nm.bridges.prices.models.filters.PriceFilterRestaurantList;
import com.nm.bridges.prices.models.filters.PriceValueFilterOrderType;
import com.nm.bridges.prices.models.filters.PriceValueFilterRestaurant;
import com.nm.bridges.prices.models.subject.PriceSubjectPart;
import com.nm.bridges.prices.models.subject.PriceSubjectProduct;
import com.nm.bridges.prices.models.subject.PriceSubjectProductPart;
import com.nm.prices.contract.PriceFormConverterDefault;
import com.nm.prices.dtos.constants.PriceFilterValueEnum;
import com.nm.prices.dtos.constants.PriceFormOptions;
import com.nm.prices.dtos.forms.PriceFormDto;
import com.nm.prices.dtos.forms.PriceFormFilterDto;
import com.nm.prices.model.Price;
import com.nm.prices.model.PriceComposed;
import com.nm.prices.model.PriceSimple;
import com.nm.prices.model.filter.PriceFilter;
import com.nm.prices.model.filter.PriceValueFilter;
import com.nm.prices.model.subject.PriceSubject;
import com.nm.prices.model.values.PriceValue;
import com.nm.prices.model.values.PriceValueSimple;
import com.nm.products.converter.ProductDefinitionGraphConverter;
import com.nm.products.dao.DaoProductDefinition;
import com.nm.products.dao.DaoProductDefinitionPart;
import com.nm.products.dao.impl.ProductDefinitionQueryBuilder;
import com.nm.products.dtos.views.ProductAsListDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.model.ProductDefinition;
import com.nm.products.soa.SoaProductDefinition;
import com.nm.shop.dao.DaoShop;
import com.nm.shop.dao.impl.ShopQueryBuilder;
import com.nm.shop.dtos.ShopViewDto;
import com.nm.shop.soa.SoaShop;
import com.nm.shop.soa.converters.ShopConverter;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author nabilmansouri
 *
 */
@Component
public class PriceFormConverterImpl extends PriceFormConverterDefault {

	@Autowired
	private DaoShop daoShop;
	@Autowired
	private DaoProductDefinitionPart daoPart;
	@Autowired
	private SoaShop soaShop;
	@Autowired
	private SoaProductDefinition soaProductDefinition;
	@Autowired
	private DaoProductDefinition daoProductDefinition;
	@Autowired
	private ShopConverter restaurantConverter;
	@Autowired
	private ProductDefinitionGraphConverter graphConverter;
	@Autowired
	private SoaBridgeConfig bridgeConfig;

	public PriceFormConverterImpl() {
	}

	private void _refreshSubject(CustomPriceFormDto form, ProductDefinition product, OptionsList options) {
		ProductAsListDto list = graphConverter.toDto(product, options);
		for (int i = 0; i < list.getNodes().size(); i++) {
			if (i == 0) {
				form.getRoot().setNode(list.node(i));
			} else {
				form.getNodes().add(new PriceFormNodeDto().setNode(list.node(i)).add(new PriceFormNodeFilterDto()));
			}
		}
	}

	private PriceFormNodeDto _toDtoValue(PriceFormNodeDto node, Price price, OptionsList options) {
		node.setEnable(true).clear();
		for (PriceValue value : price.getValues()) {
			PriceValueSimple s = (PriceValueSimple) value;
			PriceFormNodeFilterDto f = new PriceFormNodeFilterDto();
			f.setValue(s.getValue());
			f.setAllOrders(true);
			f.setAllRestaurants(true);
			node.getValues().add(f);
			for (PriceFilterValueEnum en : value.getFilter().keySet()) {
				PriceValueFilter valueFilter = value.getFilter().get(en);
				if (en.equals(PriceFilterValueEnumExtra.LimitOrderType)) {
					PriceValueFilterOrderType ordFilter = (PriceValueFilterOrderType) valueFilter;
					f.setAllOrders(false);
					f.setType(ordFilter.getOrderType());
				} else if (en.equals(PriceFilterValueEnumExtra.LimitRestaurant)) {
					PriceValueFilterRestaurant ordFilter = (PriceValueFilterRestaurant) valueFilter;
					f.setAllRestaurants(false);
					f.setRestaurant(restaurantConverter.toDto(ordFilter.getRestaurant(), options));
				}
			}
		}
		if (node.getValues().isEmpty()) {
			node.getValues().add(new PriceFormNodeFilterDto());
		}
		return node;
	}

	private Price _toEntityNode(Price price, CustomPriceFormDto form, PriceFormNodeDto node, OptionsList options)
			throws NoDataFoundException {
		switch (node.getNode().getType()) {
		case PART: {
			PriceSubjectPart subject = new PriceSubjectPart();
			subject.setPart(daoPart.loadById(node.getNode().getPart().getId()));
			price.setSubject(subject);
			break;
		}
		case PRODUCT: {
			PriceSubjectProduct subject = new PriceSubjectProduct();
			subject.setProduct(daoProductDefinition.loadById(node.getNode().getProduct().getId()));
			price.setSubject(subject);
			break;
		}
		case PRODUCT_PART: {
			PriceSubjectProductPart subject = new PriceSubjectProductPart();
			subject.setProduct(daoProductDefinition.loadById(node.getNode().getProduct().getId()));
			subject.setPart(daoPart.loadById(node.getNode().getPart().getId()));
			price.setSubject(subject);
			break;
		}
		case INGREDIENT:
		default:
			break;
		}
		price.getValues().clear();
		//
		for (PriceFormNodeFilterDto f : node.getValues()) {
			PriceValueSimple value = new PriceValueSimple();
			value.setValue(f.getValue());
			if (!f.isAllOrders()) {
				PriceValueFilterOrderType filter = new PriceValueFilterOrderType();
				filter.setType(PriceFilterValueEnumExtra.LimitOrderType);
				filter.setOrderType(f.getType());
				value.put(filter.getType(), filter);
			}
			if (!f.isAllRestaurants()) {
				PriceValueFilterRestaurant filter = new PriceValueFilterRestaurant();
				filter.setType(PriceFilterValueEnumExtra.LimitRestaurant);
				filter.setRestaurant(daoShop.load(f.getRestaurant().getId()));
				value.put(filter.getType(), filter);
			}
			price.add(value);
		}
		return price;
	}

	private Price _toEntityNode(PriceFormDto f, PriceFormNodeDto node, OptionsList options)
			throws NoDataFoundException {
		PriceSimple simple = new PriceSimple();
		CustomPriceFormDto form = (CustomPriceFormDto) f;
		_toEntityNode(simple, form, node, options);
		return simple;
	}

	@Override
	protected PriceFormDto innerToDto(Price price, OptionsList options) throws NoDataFoundException {
		CustomPriceFormDto form = new CustomPriceFormDto();
		try {
			bridgeConfig.hasOrderType();
		} catch (NoDataFoundException e) {
			form.setNoOrderType(true);
		}
		//
		_toDtoValue(form.getRoot(), price, options);
		if (options.contains(PriceFormExtraOptions.Products)) {
			form.getProducts().addAll(soaProductDefinition.fetch(ProductDefinitionQueryBuilder.get(), options));
		}
		form.setNoProducts(form.getProducts().isEmpty());
		return form;
	}

	@Override
	protected PriceFormDto innerToDto(PriceFormDto f, OptionsList options) throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) f;
		if (options.contains(PriceFormOptions.Subject)) {
			if (form.getProduct() != null) {
				ProductDefinition p = daoProductDefinition.get(form.getProduct().getId());
				_refreshSubject(form, p, options);
			}
			if (form.getRoot().getNode().getProduct() != null
					&& form.getRoot().getNode().getProduct().getId() != null) {
			}
		}
		return form;
	}

	@Override
	protected PriceFormDto innerToDtoChildren(Price child, PriceFormDto f, OptionsList options)
			throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) f;
		for (PriceFormNodeDto node : form.getNodes()) {
			switch (node.getNode().getType()) {
			case PART: {
				if (child.getSubject() instanceof PriceSubjectPart) {
					PriceSubjectPart subPart = (PriceSubjectPart) child.getSubject();
					if (Objects.equal(subPart.getPartId(), node.getNode().getPart().getId())) {
						_toDtoValue(node, child, options);
					}
				}
				break;
			}
			case PRODUCT_PART: {
				if (child.getSubject() instanceof PriceSubjectProductPart) {
					PriceSubjectProductPart subPart = (PriceSubjectProductPart) child.getSubject();
					if (Objects.equal(subPart.getPartId(), node.getNode().getPart().getId())
							&& Objects.equal(subPart.getProductId(), node.getNode().getProduct().getId())) {
						_toDtoValue(node, child, options);
					}
				}
				break;
			}
			case PRODUCT:
			case INGREDIENT:
				// DO NOTHING (could not be a child)
				break;
			}
		}
		return form;
	}

	@Override
	protected PriceFormFilterDto innerToDtoFilters(PriceFormFilterDto b, Collection<PriceFilter> filters,
			OptionsList options) throws NoDataFoundException {
		CustomPriceFormFilterDto bean = (CustomPriceFormFilterDto) b;
		Collection<ShopViewDto> allResto = soaShop.fetch(ShopQueryBuilder.get(), options);
		for (ShopViewDto r : allResto) {
			bean.getRestaurants().add(r);
		}
		for (OrderTypeFormDto o : bridgeConfig.getSelectedOrderTypes()) {
			bean.getTypes().add(new OrderTypeFormDto(o.getOrderType()));
		}
		//
		bean.setAllOrders(true);
		bean.setAllRestaurants(true);
		for (PriceFilter filter : filters) {
			if (filter.getType().equals(PriceFilterEnumExtra.LimitRestaurants)) {
				bean.setAllRestaurants(false);
				PriceFilterRestaurant fDate = (PriceFilterRestaurant) filter;
				for (PriceFilterRestaurantList rList : fDate.getList()) {
					for (ShopViewDto r : bean.getRestaurants()) {
						if (r.getId().equals(rList.getShop().getId())) {
							r.setSelected(true);
						}
					}
				}
			} else if (filter.getType().equals(PriceFilterEnumExtra.LimitOrderType)) {
				bean.setAllOrders(false);
				PriceFilterOrderType fDate = (PriceFilterOrderType) filter;
				for (OrderType o : fDate.getOrderType()) {
					for (OrderTypeFormDto r : bean.getTypes()) {
						if (r.getOrderType().equals(o)) {
							r.setSelected(true);
						}
					}
				}
			}
		}
		return bean;
	}

	@Override
	protected PriceFormDto innerToEntityValues(PriceComposed price, PriceFormDto f, OptionsList options)
			throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) f;
		_toEntityNode(price, form, form.getRoot(), options);
		for (PriceFormNodeDto node : form.getNodes()) {
			if (node.isEnable()) {
				price.addChildren(_toEntityNode(form, node, options));
			}
		}
		return f;
	}

	@Override
	protected PriceFormDto innerToDtoSubject(PriceFormDto f, PriceSubject subject, OptionsList options)
			throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) f;
		if (subject instanceof PriceSubjectProduct) {
			PriceSubjectProduct pSub = (PriceSubjectProduct) subject;
			form.setProduct(new ProductViewDto().setId(pSub.getProductId()));
			_refreshSubject(form, pSub.getProduct(), options);
		}
		return form;
	}

	@Override
	protected Collection<PriceFilter> innerToEntityFilter(PriceFormFilterDto f, OptionsList options)
			throws NoDataFoundException {
		CustomPriceFormFilterDto form = (CustomPriceFormFilterDto) f;
		Collection<PriceFilter> filters = new ArrayList<PriceFilter>();
		//
		if (!form.isAllOrders()) {
			PriceFilterOrderType filter = new PriceFilterOrderType();
			for (OrderTypeFormDto t : form.getTypes()) {
				if (t.isSelected()) {
					filter.getOrderType().add(t.getOrderType());
				}
			}
			filter.setType(PriceFilterEnumExtra.LimitOrderType);
			filters.add(filter);
		}
		if (!form.isAllRestaurants()) {
			Collection<Long> ids = new HashSet<Long>();
			for (ShopViewDto resto : form.getRestaurants()) {
				if (resto.isSelected()) {
					ids.add(resto.getId());
				}
			}
			PriceFilterRestaurant filter = new PriceFilterRestaurant();
			filter.addRestaurants(daoShop.findByIds(ids));
			filter.setType(PriceFilterEnumExtra.LimitRestaurants);
			filters.add(filter);
		}
		return filters;
	}
}
