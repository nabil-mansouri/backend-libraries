package com.nm.tests.products;

import java.util.Arrays;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nm.app.locale.LocaleFormDto;
import com.nm.app.locale.SoaLocale;
import com.nm.products.dtos.forms.IngredientFormDto;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.views.IngredientViewDto;
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
public class TestCrudIngredient {

	@Autowired
	private SoaProductDefinition soaProductDefinition;
	@Autowired
	private SoaLocale soaLocale;
	//
	private IngredientFormDto form;
	@Autowired
	private ProductDataFactory dataFactory;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	public void setAppliContent(ConfigurableApplicationContext applicationContext) {
	}

	//
	@Before
	public void setUp() {
		soaLocale.setSelectedLocales(Arrays.asList(new LocaleFormDto("fr"), new LocaleFormDto("en")));
		soaLocale.setDefaultLocale(new LocaleFormDto("fr"));
		this.form = dataFactory.createIngredien();
	}

	@Test
	@Transactional
	public void testShouldCreateIngredient() throws NoDataFoundException {
		Assert.assertEquals(2, form.getCms().getContents().size());
	}

	@Test
	@Transactional
	public void testDeleteIngredientWithProduct() throws NoDataFoundException {
		IngredientViewDto ing1 = soaProductDefinition.saveOrUpdate(form, new OptionsList());
		ProductFormDto product = dataFactory.createProduct();
		IngredientViewDto ing2 = product.getIngredients().iterator().next();
		Assert.assertEquals(ing1.getId(), ing2.getId());
		ing2.setSelected(true);
		soaProductDefinition.saveOrUpdate(product, new OptionsList());
		soaProductDefinition.removeIngredient(ing1.getId());
		product = soaProductDefinition.editProduct(product.getId());
		Assert.assertEquals(0, product.getIngredients().size());
	}

	@Test
	@Transactional
	public void testShouldSaveIngredient() throws NoDataFoundException {
		IngredientViewDto view = soaProductDefinition.saveOrUpdate(form, new OptionsList());
		Assert.assertNotNull(view.getId());
	}

	@Test
	@Transactional
	public void testShouldTransformJson() throws Exception {
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		String value = mapper.writeValueAsString(form);
		IngredientFormDto clone = mapper.readValue(value, IngredientFormDto.class);
		Assert.assertEquals(clone.getId(), form.getId());
	}
}
