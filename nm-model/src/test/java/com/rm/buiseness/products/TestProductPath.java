package com.rm.buiseness.products;

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
import com.rm.soa.products.SoaProductDefinition;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestProductPath {

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	protected SoaProductDefinition soaProductDefinition;
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
	public void testPaths() throws NoDataFoundException {
		// final GraphPathBuilder path = soaProductDefinition.getPathBuilder();
		// ProductViewDto menu =
		// test.getAllProducts().get(TestScenarios.MENU_MOYEN);
		// GraphIteratorBuilder.buildDeep().iterate(menu, new IteratorListener()
		// {
		//
		// public boolean stop() {
		// return false;
		// }
		//
		// public boolean onFounded(AbstractGraph node) {
		// System.out.println("Path: " + path);
		// System.out.println("Product Level " + node.level());
		// System.out.println(node);
		// assertEquals(path.size(), node.level() + 1);
		// return true;
		// }
		//
		// public boolean doSetParent() {
		// return true;
		// }
		// }, path);
	}
}
