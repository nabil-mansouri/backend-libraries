package com.nm.buiseness.prices;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nm.app.currency.CurrencyDto;
import com.nm.app.currency.SoaDevise;
import com.nm.app.locale.LocaleFormDto;
import com.nm.app.locale.SoaLocale;
import com.nm.bridges.SoaBridgeConfig;
import com.nm.bridges.prices.OrderType;
import com.nm.bridges.prices.constants.PriceFilterEnumExtra;
import com.nm.bridges.prices.constants.PriceFilterValueEnumExtra;
import com.nm.bridges.prices.dtos.CustomPriceFilterDto;
import com.nm.bridges.prices.dtos.CustomPriceFormDto;
import com.nm.bridges.prices.dtos.CustomPriceFormFilterDto;
import com.nm.bridges.prices.dtos.OrderTypeFormDto;
import com.nm.bridges.prices.dtos.PriceFormNodeDto;
import com.nm.bridges.prices.dtos.PriceFormNodeFilterDto;
import com.nm.bridges.prices.queries.CustomPriceFilterQueryBuilder;
import com.nm.bridges.prices.queries.CustomPriceQueryBuilder;
import com.nm.bridges.prices.queries.CustomPriceValueFilterQueryBuilder;
import com.nm.bridges.prices.queries.CustomPriceValueQueryBuilder;
import com.nm.prices.dao.DaoPrice;
import com.nm.prices.dao.DaoPriceValue;
import com.nm.prices.dtos.constants.PriceFormOptions;
import com.nm.prices.model.Price;
import com.nm.prices.model.values.PriceValue;
import com.nm.prices.soa.SoaPrice;
import com.nm.products.constants.ProductType;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.forms.ProductPartFormDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.soa.SoaProductDefinition;
import com.nm.shop.dtos.ShopFormDto;
import com.nm.shop.dtos.ShopViewDto;
import com.nm.shop.soa.SoaShop;
import com.nm.tests.utils.DataFactory;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH_TEST })
public class TestCrudPrice {

	@Autowired
	protected SoaProductDefinition soaProductDefinition;
	@Autowired
	private SoaPrice soaPrice;
	@Autowired
	private SoaShop soaRestaurant;
	@Autowired
	protected SoaLocale soaLocale;
	@Autowired
	private DaoPrice daoPrice;
	@Autowired
	private DaoPriceValue daoPriceValue;
	//
	@Autowired
	private SoaBridgeConfig soaOrderType;
	@Autowired
	private DataFactory dataFactory;
	@Autowired
	private SoaDevise soaDevise;
	private ProductFormDto form;

	@Autowired
	public void setAppliContent(ConfigurableApplicationContext applicationContext) {
	}

	//
	@Before
	public void setUp() throws Exception {
		soaOrderType.setSelectedOrderTypes(Arrays.asList(OrderType.values()));
		soaLocale.setSelectedLocales(Arrays.asList(new LocaleFormDto("fr"), new LocaleFormDto("en")));
		soaLocale.setDefaultLocale(new LocaleFormDto("fr"));
		soaDevise.setDefault(new CurrencyDto("EUR"));
		// Restau
		ShopFormDto resto = dataFactory.createRestaurant();
		soaRestaurant.saveOrUpdate(resto, new OptionsList());
		// Product for parts
		ProductFormDto sub = dataFactory.createProduct();
		soaProductDefinition.saveOrUpdate(sub, new OptionsList());
		//
		this.form = dataFactory.createProduct(3);
		for (ProductPartFormDto p : this.form.getParts()) {
			p.getProducts().put(this.form.getProducts().keySet().iterator().next(), true);
		}
		soaProductDefinition.saveOrUpdate(this.form, new OptionsList());
		daoPrice.flush();
	}

	@Test
	@Transactional
	public void testShouldCreatePrice() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		CustomPriceFormFilterDto filter = (CustomPriceFormFilterDto) form.getFilter();
		Assert.assertFalse(form.getFilter().isHasFrom());
		Assert.assertFalse(form.getFilter().isHasTo());
		Assert.assertTrue(filter.isAllRestaurants());
		Assert.assertTrue(filter.isAllOrders());
		Assert.assertEquals(2, form.getProducts().size());
		Assert.assertEquals(1, filter.getRestaurants().size());
		Assert.assertEquals(OrderType.values().length, filter.getTypes().size());
	}

	@Test
	@Transactional
	public void testShouldCreatePriceWith1OrderTypes() throws NoDataFoundException {
		soaOrderType.setSelectedOrderTypes(Arrays.asList(OrderType.Delivered));
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		CustomPriceFormFilterDto filter = (CustomPriceFormFilterDto) form.getFilter();
		Assert.assertEquals(1, filter.getTypes().size());
	}

	@Test
	@Transactional
	public void testShouldEnableOthersParts() throws NoDataFoundException {
		daoPrice.flush();
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		form.setProduct(form.getProducts().get(1));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		Assert.assertFalse(form.getNodes().isEmpty());
		for (PriceFormNodeDto n : form.getNodes()) {
			n.clear().setEnable(true);
			n.add(new PriceFormNodeFilterDto(2.0));
		}
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		daoPrice.clear();
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		Assert.assertFalse(form.getNodes().isEmpty());
		for (PriceFormNodeDto n : form.getNodes()) {
			Assert.assertTrue(n.isEnable());
			Assert.assertEquals(1, n.getValues().size());
			for (PriceFormNodeFilterDto f : n.getValues()) {
				Assert.assertEquals(2.0, f.getValue(), 0d);
			}
		}
	}

	@Test
	@Transactional
	public void testShouldFindByOrderTypeFilter() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		form.setProduct(form.getProducts().get(0));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		Collection<Price> prices = daoPrice.find(CustomPriceQueryBuilder.get().withFilterOrNotExists(
				CustomPriceFilterQueryBuilder.getOrder().withOrderType(OrderType.InPlace),
				PriceFilterEnumExtra.LimitOrderType));
		Assert.assertEquals(1, prices.size());
		//
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		CustomPriceFormFilterDto filter = ((CustomPriceFormFilterDto) form.getFilter());
		filter.setAllOrders(false);
		for (OrderTypeFormDto t : filter.getTypes()) {
			if (t.getOrderType().equals(OrderType.InPlace)) {
				t.setSelected(true);
			}
		}
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		prices = daoPrice.findAll();
		Assert.assertEquals(1, daoPriceValue.findAll().size());
		prices = daoPrice.find(CustomPriceQueryBuilder.get().withFilterOrNotExists(
				CustomPriceFilterQueryBuilder.getOrder().withOrderType(OrderType.OutPlace),
				PriceFilterEnumExtra.LimitOrderType));
		Assert.assertTrue(prices.isEmpty());
		prices = daoPrice.find(CustomPriceQueryBuilder.get().withFilterOrNotExists(
				CustomPriceFilterQueryBuilder.getOrder().withOrderType(OrderType.InPlace),
				PriceFilterEnumExtra.LimitOrderType));
		Assert.assertEquals(1, prices.size());
	}

	@Test
	@Transactional
	public void testShouldFindByOrderTypeFilterValue() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		form.setProduct(form.getProducts().get(0));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		Collection<PriceValue> prices = daoPriceValue.find(CustomPriceValueQueryBuilder.get().withFilterOrNotExists(
				CustomPriceValueFilterQueryBuilder.getOrderType().withOrderType(OrderType.OutPlace),
				PriceFilterValueEnumExtra.LimitOrderType));
		Assert.assertEquals(1, prices.size());
		//
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		CustomPriceFormFilterDto filter = ((CustomPriceFormFilterDto) form.getFilter());
		Assert.assertTrue(filter.isAllOrders());
		filter.setAllOrders(false);
		form.getRoot().getValues().clear();
		for (OrderTypeFormDto t : filter.getTypes()) {
			if (t.getOrderType().equals(OrderType.InPlace)) {
				t.setSelected(true);
				form.getRoot().add(new PriceFormNodeFilterDto(t.getOrderType(), (double) t.getOrderType().ordinal()));
			}
		}
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		daoPrice.clear();
		//
		prices = daoPriceValue.findAll();
		Assert.assertEquals(1, prices.size());
		prices = daoPriceValue.find(CustomPriceValueQueryBuilder.get().withFilterOrNotExists(
				CustomPriceValueFilterQueryBuilder.getOrderType().withOrderType(OrderType.OutPlace),
				PriceFilterValueEnumExtra.LimitOrderType));
		Assert.assertTrue(prices.isEmpty());
		prices = daoPriceValue.find(CustomPriceValueQueryBuilder.get().withFilterOrNotExists(
				CustomPriceValueFilterQueryBuilder.getOrderType().withOrderType(OrderType.InPlace),
				PriceFilterValueEnumExtra.LimitOrderType));
		Assert.assertEquals(1, prices.size());
	}

	@Test
	@Transactional
	public void testShouldFindByProduct() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		form.setProduct(form.getProducts().get(1));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		daoPrice.clear();
		CustomPriceFilterDto filter = new CustomPriceFilterDto();
		filter.setType(ProductType.Product);
		filter.getIdProducts().add(form.getProducts().get(1).getId());
		Assert.assertEquals(1, daoPrice.count(CustomPriceQueryBuilder.get().withFilter(filter).getQuery()).intValue());
		filter.getIdProducts().clear();
		filter.getIdProducts().add(form.getProducts().get(0).getId());
		Assert.assertEquals(0, daoPrice.count(CustomPriceQueryBuilder.get().withFilter(filter).getQuery()).intValue());
	}

	@Test
	@Transactional
	public void testShouldFindByRestaurantFilter() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		form.setProduct(form.getProducts().get(1));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		daoPrice.clear();
		CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
		ShopViewDto r = f.setAllRestaurants(false).getRestaurants().iterator().next();
		CustomPriceFilterDto filter = new CustomPriceFilterDto();
		filter.setIdRestaurant(r.getId());
		Assert.assertEquals(1, daoPrice.count(CustomPriceQueryBuilder.get().withFilter(filter).getQuery()).intValue());
		//
		f = ((CustomPriceFormFilterDto) form.getFilter());
		r = f.setAllRestaurants(false).getRestaurants().iterator().next();
		r.setSelected(true);
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		Assert.assertEquals(1, daoPrice.count(CustomPriceQueryBuilder.get().withFilter(filter).getQuery()).intValue());
	}

	@Test
	@Transactional
	public void testShouldFindByRestaurantsFilter() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
		ShopViewDto r = f.getRestaurants().iterator().next();

		form.setProduct(form.getProducts().get(0));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		Collection<Price> prices = daoPrice.find(CustomPriceQueryBuilder.get().withFilterOrNotExists(
				CustomPriceFilterQueryBuilder.getRestaurant().withRestaurant(r.getId()),
				PriceFilterEnumExtra.LimitRestaurants));
		Assert.assertEquals(1, prices.size());
		//
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		f = ((CustomPriceFormFilterDto) form.getFilter());
		r = f.setAllRestaurants(false).getRestaurants().iterator().next();
		r.setSelected(true);
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		prices = daoPrice.find(CustomPriceQueryBuilder.get().withFilterOrNotExists(
				CustomPriceFilterQueryBuilder.getRestaurant().withNotRestaurant(r.getId()),
				PriceFilterEnumExtra.LimitRestaurants));
		Assert.assertTrue(prices.isEmpty());
		prices = daoPrice.find(CustomPriceQueryBuilder.get().withFilterOrNotExists(
				CustomPriceFilterQueryBuilder.getRestaurant().withRestaurant(r.getId()),
				PriceFilterEnumExtra.LimitRestaurants));
		Assert.assertEquals(1, prices.size());
	}

	@Test
	@Transactional
	public void testShouldFindByRestaurantsFilterValue() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
		ShopViewDto r = f.getRestaurants().iterator().next();

		form.setProduct(form.getProducts().get(0));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		Collection<PriceValue> prices = daoPriceValue.find(CustomPriceValueQueryBuilder.get().withFilterOrNotExists(
				CustomPriceValueFilterQueryBuilder.getRestaurant().withRestaurant(r.getId()),
				PriceFilterValueEnumExtra.LimitRestaurant));
		Assert.assertEquals(1, prices.size());
		//
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		f = ((CustomPriceFormFilterDto) form.getFilter());
		Assert.assertFalse(f.getRestaurants().isEmpty());
		form.getRoot().getValues().clear();
		r = f.setAllRestaurants(false).getRestaurants().iterator().next();
		r.setSelected(true);
		form.getRoot().add(new PriceFormNodeFilterDto(r, 2d));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		prices = daoPriceValue.find(CustomPriceValueQueryBuilder.get().withFilterOrNotExists(
				CustomPriceValueFilterQueryBuilder.getRestaurant().withRestaurant(r.getId()),
				PriceFilterValueEnumExtra.LimitRestaurant));
		// Assert.assertEquals(1, prices.size());
		prices = daoPriceValue.find(CustomPriceValueQueryBuilder.get().withFilterOrNotExists(
				CustomPriceValueFilterQueryBuilder.getRestaurant().withNotRestaurant(r.getId()),
				PriceFilterValueEnumExtra.LimitRestaurant));
		// Assert.assertTrue(prices.isEmpty());
	}

	@Test
	@Transactional
	public void testShouldRemovePriceWithOrderTypeFilter() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
		form.setProduct(form.getProducts().get(0));
		f.setAllOrders(false).findBy(OrderType.InPlace).setSelected(true);
		form.getRoot().clear().add(new PriceFormNodeFilterDto(OrderType.InPlace, 2.0));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		soaPrice.removePrice(form.getId());
		daoPrice.flush();
		Assert.assertEquals(0, daoPrice.findAll().size());
	}

	@Test
	@Transactional
	public void testShouldRemovePriceWithRestaurantFilter() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
		f.setAllRestaurants(false).getRestaurants().iterator().next().setSelected(true);

		form.setProduct(form.getProducts().get(0));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		soaPrice.removePrice(form.getId());
		daoPrice.flush();
		Assert.assertEquals(0, daoPrice.findAll().size());
	}

	@Test
	@Transactional
	public void testShouldRemovePriceWithSubject() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		form.setProduct(form.getProducts().get(0));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		soaPrice.removePrice(form.getId());
		daoPrice.flush();
		Assert.assertEquals(0, daoPrice.findAll().size());
	}

	@Test
	@Transactional
	public void testShouldRemoveProductsAffectedToPrice() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		form.setProduct(form.getProducts().get(1));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		Assert.assertFalse(form.getNodes().isEmpty());
		for (PriceFormNodeDto n : form.getNodes()) {
			n.setEnable(true).clear();
			n.add(new PriceFormNodeFilterDto(2.0));
		}
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		soaProductDefinition.removeProduct(this.form.getId());
		daoPrice.flush();
	}

	@Test
	@Transactional
	public void testShouldRemoveRestaurantsAffectedToFilter() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		form.setProduct(form.getProducts().get(1));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
		ShopViewDto r = f.setAllRestaurants(false).getRestaurants().iterator().next();
		r.setSelected(true);
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		soaRestaurant.removeShop(r.getId());
		daoPrice.flush();
	}

	@Test
	@Transactional
	public void testShouldRemoveRestaurantsAffectedToFilterValue() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		form.setProduct(form.getProducts().get(1));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
		Assert.assertFalse(f.getRestaurants().isEmpty());
		Collection<ShopViewDto> restos = f.getRestaurants();
		for (ShopViewDto t : f.getRestaurants()) {
			t.setSelected(true);
			form.getRoot().add(new PriceFormNodeFilterDto(t, 2d));
		}
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		for (ShopViewDto r : restos) {
			soaRestaurant.removeShop(r.getId());
			daoPrice.flush();
		}
	}

	@Test
	@Transactional
	public void testShouldSaveOnlyOnce() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		ProductViewDto first = form.getProducts().get(0);
		form.setProduct(first);
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2.0));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		Long idBefore = form.getId();
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		Assert.assertEquals(idBefore, form.getId());
		Assert.assertEquals(1, daoPrice.findAll().size());
	}

	@Test
	@Transactional
	public void testShouldSaveSubject() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		ProductViewDto first = form.getProducts().get(0);
		form.setProduct(first);
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2.0));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		Assert.assertNotNull(form.getRoot().getNode().getProduct());
		Assert.assertEquals(first.getId(), form.getRoot().getNode().getProduct().getId());
		Assert.assertEquals(1, form.getRoot().getValues().size());
		for (PriceFormNodeFilterDto f : form.getRoot().getValues()) {
			Assert.assertEquals(2.0, f.getValue(), 0d);
		}
	}

	@Test
	@Transactional
	public void testShouldNotEnableOthersParts() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		form.setProduct(form.getProducts().get(1));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		Assert.assertFalse(form.getNodes().isEmpty());
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		Assert.assertFalse(form.getNodes().isEmpty());
		for (PriceFormNodeDto n : form.getNodes()) {
			Assert.assertFalse(n.isEnable());
		}
	}

	@Test
	@Transactional
	public void testShouldRefreshAndLoadProductComposedGraph() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		Assert.assertEquals(0, form.getNodes().size());
		Assert.assertNull(form.getRoot().getNode().getProduct());

		form.setProduct(form.getProducts().get(1));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		Assert.assertNotNull(form.getRoot().getNode().getProduct());
		Assert.assertEquals(6, form.getNodes().size());
	}

	@Test
	@Transactional
	public void testShouldRefreshAndLoadProductSimpleGraph() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		Assert.assertEquals(0, form.getNodes().size());
		Assert.assertNull(form.getRoot().getNode().getProduct());

		form.setProduct(form.getProducts().get(0));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		Assert.assertNotNull(form.getRoot().getNode().getProduct());
		Assert.assertEquals(0, form.getNodes().size());
	}

	@Test
	@Transactional
	public void testShouldRemovePartAndProductPartsAffectedToPrice() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();

		form.setProduct(form.getProducts().get(1));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		Assert.assertFalse(form.getNodes().isEmpty());
		for (PriceFormNodeDto n : form.getNodes()) {
			n.setEnable(true).clear();
			n.add(new PriceFormNodeFilterDto(2.0));
		}
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		this.form.setHasProducts(false);
		this.form.getParts().clear();
		soaProductDefinition.saveOrUpdate(this.form, new OptionsList());
		daoPrice.flush();
	}

	@Test
	@Transactional
	public void testShouldRemovePriceWithComposed() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();

		form.setProduct(form.getProducts().get(0));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		for (PriceFormNodeDto n : form.getNodes()) {
			n.setEnable(true).clear();
			n.add(new PriceFormNodeFilterDto(2.0));
		}
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		soaPrice.removePrice(form.getId());
		daoPrice.flush();
		Assert.assertEquals(0, daoPrice.findAll().size());
	}

	@Test
	@Transactional
	public void testShouldSaveWithOrderTypeFilter() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();

		form.setProduct(form.getProducts().get(0));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
		Assert.assertTrue(f.isAllOrders());
		f.setAllOrders(false);
		f.findBy(OrderType.InPlace).setSelected(true);
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		daoPrice.clear();
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		f = ((CustomPriceFormFilterDto) form.getFilter());
		Assert.assertFalse(f.isAllOrders());
		Assert.assertTrue(f.findBy(OrderType.InPlace).isSelected());
	}

	@Test
	@Transactional
	public void testShouldSaveWithOrderTypeValueFIlter() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();

		form.setProduct(form.getProducts().get(0));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
		Assert.assertTrue(f.isAllOrders());
		f.setAllOrders(false);
		form.getRoot().getValues().clear();
		for (OrderTypeFormDto t : f.getTypes()) {
			t.setSelected(true);
			form.getRoot().add(new PriceFormNodeFilterDto(t.getOrderType(), (double) t.getOrderType().ordinal()));
		}
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		daoPrice.clear();
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		Assert.assertFalse(f.isAllOrders());
		Assert.assertFalse(form.getRoot().getValues().isEmpty());
		Assert.assertEquals(f.getTypes().size(), form.getRoot().getValues().size());
		for (PriceFormNodeFilterDto o : form.getRoot().getValues()) {
			Assert.assertEquals(o.getType().ordinal(), o.getValue(), 0d);
		}
	}

	@Test
	@Transactional
	public void testShouldSaveWithRestaurantFilter() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();

		form.setProduct(form.getProducts().get(0));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
		Assert.assertTrue(f.isAllOrders());
		f.setAllRestaurants(false).getRestaurants().iterator().next().setSelected(true);
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		Assert.assertFalse(f.isAllRestaurants());
		Assert.assertTrue(f.getRestaurants().iterator().next().isSelected());
	}

	@Test
	@Transactional
	public void testShouldSaveWithRestaurantValueFilter() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();

		form.setProduct(form.getProducts().get(0));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
		Assert.assertTrue(f.isAllOrders());
		f.setAllRestaurants(false);
		form.getRoot().getValues().clear();
		Assert.assertFalse(f.getRestaurants().isEmpty());
		for (ShopViewDto t : f.getRestaurants()) {
			t.setSelected(true);
			form.getRoot().add(new PriceFormNodeFilterDto(t, 2d));
		}
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		daoPrice.clear();
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		Assert.assertFalse(f.isAllRestaurants());
		Assert.assertFalse(form.getRoot().getValues().isEmpty());
		Assert.assertEquals(f.getRestaurants().size(), form.getRoot().getValues().size());
		for (PriceFormNodeFilterDto o : form.getRoot().getValues()) {
			Assert.assertEquals(2d, o.getValue(), 0d);
			Assert.assertNotNull(o.getRestaurant());
			Assert.assertNotNull(o.getRestaurant().getId());
		}
	}

	@Test
	@Transactional
	public void testShouldTransformJson() throws Exception {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();

		form.setProduct(form.getProducts().get(0));
		form.getRoot().clear().add(new PriceFormNodeFilterDto(2d));
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		form = (CustomPriceFormDto) soaPrice.editPrice(form.getId());
		CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
		for (ShopViewDto t : f.getRestaurants()) {
			t.setSelected(true);
			form.getRoot().add(new PriceFormNodeFilterDto(t, 2d));
		}
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		daoPrice.clear();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerSubtypes(CustomPriceFormFilterDto.class);
		String val = mapper.writeValueAsString(form);
		System.out.println("***************************");
		System.out.println(val);
		System.out.println("***************************");
		CustomPriceFormDto clone = mapper.readValue(val, CustomPriceFormDto.class);
		Assert.assertEquals(form.getRoot().getValues().size(), clone.getRoot().getValues().size());
		Assert.assertEquals(form.getRoot().getValues().iterator().next().getValue(),
				clone.getRoot().getValues().iterator().next().getValue(), 0D);
		f = ((CustomPriceFormFilterDto) form.getFilter());
		CustomPriceFormFilterDto fClone = ((CustomPriceFormFilterDto) clone.getFilter());
		Assert.assertEquals(f.getRestaurants().size(), fClone.getRestaurants().size());
	}
}
