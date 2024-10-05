package com.rm.buiseness.products;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rm.buiseness.commons.TestScenarios;
import com.rm.buiseness.commons.TestUrlUtils;
import com.rm.contract.products.forms.ProductCheckerContext;
import com.rm.contract.products.forms.ProductCheckerContextIngredientSelection;
import com.rm.contract.products.forms.ProductCheckerContextProductSelection;
import com.rm.contract.products.views.IngredientViewDto;
import com.rm.contract.products.views.ProductViewDto;
import com.rm.soa.products.checker.ProductCheckerProcessor;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestProductChecker {

	@Autowired
	private ProductCheckerProcessor processor;
	@Autowired
	private ApplicationContext applicationContext;
	private TestScenarios test;

	//
	@Before
	public void setUp() throws Exception {
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext)
				.getBeanFactory();
		test = beanFactory.createBean(TestScenarios.class);
		test.testCreate();
	}

	@Test
	@Transactional
	public void testCheckOk() throws NoDataFoundException {
		ProductViewDto menu = test.getAllProductsView().get(TestScenarios.MENU_MOYEN);
		IngredientViewDto oig = test.getAllIngredients().get(TestScenarios.OIGNON);
//		for (ProductPartViewDto part : menu.parts()) {
//			part.setSelected(part.getProducts().get(0));
//		}
		menu.parts().get(0).getSelected().getExcluded().add(oig);
		ProductCheckerContext context = processor.process(menu, menu);
		assertEquals(false, context.isInError());
		assertEquals(false, context.isInWarning());
	}

	@Test
	@Transactional
	public void testCheckPartMandatory() throws NoDataFoundException {
		ProductViewDto menu = test.getAllProductsView().get(TestScenarios.MENU_MOYEN);
//		for (ProductPartViewDto part : menu.parts()) {
//			part.setSelected(part.getProducts().get(0));
//		}
		menu.parts().get(0).setSelected(null);
		//
		ProductCheckerContext context = processor.process(menu, menu);
		assertEquals(true, context.isInError());
		assertEquals(1, context.getPartsMandatory().size());
		assertEquals(0, context.getProductBadSelection().size());
		assertEquals(false, context.isInWarning());
	}

	@Test
	@Transactional
	public void testCheckBadProductSelection() throws NoDataFoundException {
		ProductViewDto menu = test.getAllProductsView().get(TestScenarios.MENU_MOYEN);
//		for (ProductPartViewDto part : menu.parts()) {
//			part.setSelected(part.getProducts().get(1));
//		}
		menu.parts().get(0).setSelected(menu.parts().get(1).getSelected());
		//
		ProductCheckerContext context = processor.process(menu, menu);
		assertEquals(true, context.isInError());
		assertEquals(0, context.getPartsMandatory().size());
		assertEquals(1, context.getProductBadSelection().size());
		assertEquals(false, context.isInWarning());
		ProductCheckerContextProductSelection prodBad = context.getProductBadSelection().iterator().next();
		assertEquals(menu.parts().get(1).getSelected().getId(), prodBad.getProduct().getId());
		assertEquals(menu.parts().get(0).getId(), prodBad.getPart().getId());
		// Correction
		assertNull(menu.parts().get(0).getSelected());
	}

	@Test
	@Transactional
	public void testCheckBadIngredientSelection() throws NoDataFoundException {
		ProductViewDto menu = test.getAllProductsView().get(TestScenarios.MENU_MOYEN);
		IngredientViewDto ing = test.getAllIngredients().get(TestScenarios.SALADE);
		IngredientViewDto oig = test.getAllIngredients().get(TestScenarios.OIGNON);
//		for (ProductPartViewDto part : menu.parts()) {
//			part.setSelected(part.getProducts().get(1));
//		}
		menu.parts().get(0).getSelected().getExcluded().add(ing);
		menu.parts().get(0).getSelected().getExcluded().add(oig);
		//
		ProductCheckerContext context = processor.process(menu, menu);
		assertEquals(false, context.isInError());
		assertEquals(true, context.isInWarning());
		assertEquals(1, context.getIngredientBadSelection().size());
		ProductCheckerContextIngredientSelection ingBad = context.getIngredientBadSelection().iterator().next();
		assertEquals(menu.parts().get(0).getSelected().getId(), ingBad.getProduct().getId());
		assertEquals(ing.getId(), ingBad.getIngredient().getId());
		// Correction
		assertEquals(1, menu.parts().get(0).getSelected().getExcluded().size());
		assertEquals(oig.getId(), menu.parts().get(0).getSelected().getExcluded().get(0).getId());
	}

	@Test
	@Transactional
	public void testCheckPartAbsent() throws NoDataFoundException {
		ProductViewDto original = test.getAllProductsView().get(TestScenarios.MENU_MOYEN);
		ProductViewDto copy = SerializationUtils.clone(original);
//		for (ProductPartViewDto part : copy.parts()) {
//			part.setSelected(part.getProducts().get(0));
//		}
//		copy.removeChild(copy.parts().get(0));
		assertTrue(copy.parts().size() < original.parts().size());
		//
		ProductCheckerContext context = processor.process(original, copy);
		assertEquals(true, context.isInError());
		assertEquals(false, context.isInWarning());
		// Sandwich et 2 sauces
		assertEquals(3, context.getPartsAbsent().size());
	}

	@Test
	@Transactional
	public void testCheckPartNoMoreExists() throws NoDataFoundException {
		ProductViewDto original = test.getAllProductsView().get(TestScenarios.MENU_MOYEN);
		ProductViewDto copy = SerializationUtils.clone(original);
//		original.removeChild(original.parts().get(1));
//		for (ProductPartViewDto part : copy.parts()) {
//			part.setSelected(part.getProducts().get(0));
//		}
		assertTrue(copy.parts().size() > original.parts().size());
		//
		ProductCheckerContext context = processor.process(original, copy);
		assertEquals(false, context.isInError());
		assertEquals(true, context.isInWarning());
		// Accompagnement
		assertEquals(1, context.getNotExistsPartAnyMore().size());
		// Correction
		assertEquals(copy.parts().size(), original.parts().size());
	}

	@Test
	@Transactional
	public void testBadPartCardinality() throws NoDataFoundException {
		ProductViewDto original = test.getAllProductsView().get(TestScenarios.MENU_MOYEN);
		ProductViewDto copy = SerializationUtils.clone(original);
		// copy.add(copy.parts().get(0));
		// for (ProductPartViewDto part : copy.parts()) {
		// part.setSelected(part.getProducts().get(0));
		// }
		assertTrue(copy.parts().size() > original.parts().size());
		//
		ProductCheckerContext context = processor.process(original, copy);
		assertEquals(false, context.isInError());
		assertEquals(true, context.isInWarning());
		// Accompagnement
		assertEquals(3, context.getBadPartCardinality().size());
		// Correction
		assertEquals(original.parts().size(), copy.parts().size());
	}
}
