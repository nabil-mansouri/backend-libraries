package com.nm.buiseness.prices;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.app.locale.LocaleFormDto;
import com.nm.app.locale.SoaLocale;
import com.nm.bridges.SoaBridgeConfig;
import com.nm.bridges.prices.OrderType;
import com.nm.bridges.prices.dtos.CustomPriceFilterDto;
import com.nm.bridges.prices.dtos.CustomPriceFormDto;
import com.nm.bridges.prices.dtos.CustomPriceFormFilterDto;
import com.nm.bridges.prices.dtos.OrderTypeFormDto;
import com.nm.bridges.prices.dtos.PriceFormNodeDto;
import com.nm.bridges.prices.dtos.PriceFormNodeFilterDto;
import com.nm.bridges.prices.queries.CustomPriceSubjectQueryBuilder;
import com.nm.prices.dao.DaoPrice;
import com.nm.prices.dao.DaoPriceSubject;
import com.nm.prices.dao.DaoPriceValue;
import com.nm.prices.dtos.constants.PriceFormOptions;
import com.nm.prices.dtos.forms.PriceComputerResult;
import com.nm.prices.model.subject.PriceSubject;
import com.nm.prices.soa.SoaPrice;
import com.nm.prices.soa.computers.impl.PriceComputerFactory;
import com.nm.products.dao.DaoProductDefinition;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.forms.ProductPartFormDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.soa.SoaProductDefinition;
import com.nm.shop.dtos.ShopFormDto;
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
public class TestPriceComputer {

	@Autowired
	protected SoaProductDefinition soaProductDefinition;
	@Autowired
	protected SoaPrice soaPrice;
	@Autowired
	private SoaShop soaShop;
	@Autowired
	protected SoaLocale soaLocale;
	@Autowired
	private SoaBridgeConfig soaOrderType;
	@Autowired
	protected DaoPrice daoPrice;
	@Autowired
	protected DaoProductDefinition daoProduct;
	@Autowired
	private DaoPriceSubject daoSubject;
	@Autowired
	protected DaoPriceValue daoPriceValue;
	@Autowired
	private PriceComputerFactory computerFactory;
	//
	@Autowired
	private DataFactory dataFactory;
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
		// Restau
		ShopFormDto resto = dataFactory.createRestaurant();
		soaShop.saveOrUpdate(resto, new OptionsList());
		// Product for parts
		ProductFormDto sub = dataFactory.createProduct();
		soaProductDefinition.saveOrUpdate(sub, new OptionsList());
		//
		this.form = dataFactory.createProduct(3);
		for (ProductPartFormDto p : this.form.getParts()) {
			p.getProducts().put(this.form.getProducts().keySet().iterator().next(), true);
		}
		soaProductDefinition.saveOrUpdate(this.form, new OptionsList());
	}

	// TODO use restaurant filter to compute
	// TODO should compute real price (composed or not)
	// TODO should compute best price(!=lowest => because take cartRownode)
	@Test
	@Transactional
	public void testShouldComputeLowestPrice() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		ProductViewDto first = form.getProducts().get(1);
		form.setProduct(first);
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		CustomPriceFormFilterDto f = (CustomPriceFormFilterDto) form.getFilter();
		f.setAllOrders(false);
		for (OrderTypeFormDto t : f.getTypes()) {
			t.setSelected(true);
		}
		form.getRoot().clear();
		form.getRoot().add(new PriceFormNodeFilterDto(OrderType.Delivered, 5d));
		form.getRoot().add(new PriceFormNodeFilterDto(OrderType.InPlace, 10d));
		form.getRoot().add(new PriceFormNodeFilterDto(OrderType.OutPlace, 15d));
		Assert.assertFalse(form.getNodes().isEmpty());
		for (PriceFormNodeDto n : form.getNodes()) {
			n.setEnable(true).clear();
			n.getValues().add(new PriceFormNodeFilterDto(OrderType.Delivered, 5d));
			n.getValues().add(new PriceFormNodeFilterDto(OrderType.InPlace, 10d));
			n.getValues().add(new PriceFormNodeFilterDto(OrderType.OutPlace, 15d));
		}
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		PriceSubject subjetc = daoSubject.find(CustomPriceSubjectQueryBuilder.getProduct().withProduct(first.getId()))
				.iterator().next();
		PriceComputerResult r = computerFactory.buildLowestPrice(subjetc);
		Assert.assertEquals(5d, r.total(), 0d);
	}

	@Test
	@Transactional
	public void testShouldComputeLowestPriceUsingFilter() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		ProductViewDto first = form.getProducts().get(1);
		form.setProduct(first);
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		CustomPriceFormFilterDto f = (CustomPriceFormFilterDto) form.getFilter();
		f.setAllOrders(false);
		for (OrderTypeFormDto t : f.getTypes()) {
			t.setSelected(true);
		}
		form.getRoot().clear();
		form.getRoot().add(new PriceFormNodeFilterDto(OrderType.Delivered, 5d));
		form.getRoot().add(new PriceFormNodeFilterDto(OrderType.InPlace, 10d));
		form.getRoot().add(new PriceFormNodeFilterDto(OrderType.OutPlace, 15d));
		Assert.assertFalse(form.getNodes().isEmpty());
		for (PriceFormNodeDto n : form.getNodes()) {
			n.setEnable(true).clear();
			n.getValues().add(new PriceFormNodeFilterDto(OrderType.Delivered, 5d));
			n.getValues().add(new PriceFormNodeFilterDto(OrderType.InPlace, 10d));
			n.getValues().add(new PriceFormNodeFilterDto(OrderType.OutPlace, 15d));
		}
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		PriceSubject subjetc = daoSubject.find(CustomPriceSubjectQueryBuilder.getProduct().withProduct(first.getId()))
				.iterator().next();
		CustomPriceFilterDto filter = new CustomPriceFilterDto();
		filter.setOrderType(OrderType.InPlace);
		PriceComputerResult r = computerFactory.buildLowestPrice(subjetc, filter);
		Assert.assertEquals(10d, r.total(), 0d);
	}

	@Test
	@Transactional
	public void testShouldComputeMaxPrice() throws NoDataFoundException {
		CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
		ProductViewDto first = form.getProducts().get(1);
		form.setProduct(first);
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		CustomPriceFormFilterDto f = (CustomPriceFormFilterDto) form.getFilter();
		f.setAllOrders(false);
		for (OrderTypeFormDto t : f.getTypes()) {
			t.setSelected(true);
		}
		form.getRoot().clear();
		form.getRoot().add(new PriceFormNodeFilterDto(OrderType.Delivered, 5d));
		form.getRoot().add(new PriceFormNodeFilterDto(OrderType.InPlace, 10d));
		form.getRoot().add(new PriceFormNodeFilterDto(OrderType.OutPlace, 15d));
		Assert.assertFalse(form.getNodes().isEmpty());
		for (PriceFormNodeDto n : form.getNodes()) {
			n.setEnable(true).clear();
			n.getValues().add(new PriceFormNodeFilterDto(OrderType.Delivered, 5d));
			n.getValues().add(new PriceFormNodeFilterDto(OrderType.InPlace, 10d));
			n.getValues().add(new PriceFormNodeFilterDto(OrderType.OutPlace, 15d));
		}
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		PriceSubject subjetc = daoSubject.find(CustomPriceSubjectQueryBuilder.getProduct().withProduct(first.getId()))
				.iterator().next();
		PriceComputerResult r = computerFactory.buildMaxPrice(subjetc);
		double max = 15d + (form.getNodes().size() * 15d);
		Assert.assertEquals(max, r.total(), 0d);
	}

}
