package com.nm.tests.products;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.nm.app.locale.LocaleFormDto;
import com.nm.app.locale.SoaLocale;
import com.nm.products.constants.ProductNodeType;
import com.nm.products.converter.ProductDefinitionGraphConverter;
import com.nm.products.dao.DaoProductDefinition;
import com.nm.products.dtos.forms.IngredientFormDto;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.forms.ProductPartFormDto;
import com.nm.products.dtos.views.ProductAsListDto;
import com.nm.products.dtos.views.ProductNodeDto;
import com.nm.products.factory.ProductDataFactory;
import com.nm.products.soa.SoaProductDefinition;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestProducts.class)
public class TestProductGraph {

	@Autowired
	protected SoaProductDefinition soaProductDefinition;
	@Autowired
	protected SoaLocale soaLocale;
	@Autowired
	private ProductDefinitionGraphConverter graphConverter;
	@Autowired
	private DaoProductDefinition daoProductDefinition;
	//
	@Autowired
	private ProductDataFactory dataFactory;
	//
	private ProductFormDto sub;
	private IngredientFormDto ing;

	@Autowired
	public void setAppliContent(ConfigurableApplicationContext applicationContext) {
	}

	//
	@Before
	public void setUp() throws Exception {
		soaLocale.setSelectedLocales(Arrays.asList(new LocaleFormDto("fr"), new LocaleFormDto("en")));
		soaLocale.setDefaultLocale(new LocaleFormDto("fr"));
		// Ing for subproduct
		ing = dataFactory.createIngredien();
		soaProductDefinition.saveOrUpdate(ing, new OptionsList());
		// Product for parts
		sub = dataFactory.createProduct();
		sub.setHasIngredients(true);
		sub.getIngredients().iterator().next().setSelected(true);
		soaProductDefinition.saveOrUpdate(sub, new OptionsList());
	}

	@Test
	@Transactional
	public void testShouldConvertGraphToList() throws NoDataFoundException {
		ProductFormDto form1 = dataFactory.createProduct(3);
		Assert.assertEquals(1, form1.getProducts().size());
		Assert.assertEquals(3, form1.getParts().size());
		for (ProductPartFormDto p : form1.getParts()) {
			p.getProducts().put(form1.getProducts().keySet().iterator().next(), true);
		}
		soaProductDefinition.saveOrUpdate(form1, new OptionsList());
		ProductAsListDto list = graphConverter.toDto(daoProductDefinition.get(form1.getId()), new OptionsList());
		Assert.assertEquals((1 + 3 + 3 + 3), list.getNodes().size());
		Map<ProductNodeType, Integer> counts = new HashMap<ProductNodeType, Integer>();
		for (ProductNodeDto n : list.getNodes()) {
			counts.putIfAbsent(n.getType(), 0);
			counts.put(n.getType(), counts.get(n.getType()) + 1);
		}
		Assert.assertEquals(1, counts.get(ProductNodeType.PRODUCT).intValue());
		Assert.assertEquals(3, counts.get(ProductNodeType.PART).intValue());
		Assert.assertEquals(3, counts.get(ProductNodeType.PRODUCT_PART).intValue());
		Assert.assertEquals(3, counts.get(ProductNodeType.INGREDIENT).intValue());
	}

	@Test
	@Transactional
	public void testShouldConvertGraphToListWithOrder() throws NoDataFoundException {
		ProductFormDto form1 = dataFactory.createProduct(3);
		Assert.assertEquals(1, form1.getProducts().size());
		Assert.assertEquals(3, form1.getParts().size());
		for (ProductPartFormDto p : form1.getParts()) {
			p.getProducts().put(form1.getProducts().keySet().iterator().next(), true);
		}
		soaProductDefinition.saveOrUpdate(form1, new OptionsList());
		ProductAsListDto list = graphConverter.toDto(daoProductDefinition.get(form1.getId()), new OptionsList());
		// Position, Type, Id
		Table<Integer, ProductNodeType, Long> order = HashBasedTable.create();
		int cpt = 0;
		for (ProductNodeDto n : list.getNodes()) {
			Long id = null;
			switch (n.getType()) {
			case INGREDIENT:
				id = n.getIngredient().getId();
				break;
			case PART:
				id = n.getPart().getId();
				break;
			case PRODUCT:
			case PRODUCT_PART:
				id = n.getProduct().getId();
				break;
			}
			order.put(cpt, n.getType(), id);
			cpt++;
		}
		//
		Assert.assertEquals(form1.getId(), order.get(0, ProductNodeType.PRODUCT));
		Assert.assertNotNull(order.get(1, ProductNodeType.PART));
		Assert.assertEquals(sub.getId(), order.get(2, ProductNodeType.PRODUCT_PART));
		Assert.assertEquals(ing.getId(), order.get(3, ProductNodeType.INGREDIENT));
		Assert.assertNotNull(order.get(4, ProductNodeType.PART));
		Assert.assertEquals(sub.getId(), order.get(5, ProductNodeType.PRODUCT_PART));
		Assert.assertEquals(ing.getId(), order.get(6, ProductNodeType.INGREDIENT));
		Assert.assertNotNull(order.get(7, ProductNodeType.PART));
		Assert.assertEquals(sub.getId(), order.get(8, ProductNodeType.PRODUCT_PART));
		Assert.assertEquals(ing.getId(), order.get(9, ProductNodeType.INGREDIENT));
		// Fake
		Assert.assertNull(order.get(1, ProductNodeType.PRODUCT));
	}
}
