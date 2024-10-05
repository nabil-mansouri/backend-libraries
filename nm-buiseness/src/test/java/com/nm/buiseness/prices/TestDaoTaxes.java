package com.nm.buiseness.prices;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH_TEST })
public class TestDaoTaxes {
	// @Autowired
	// private DaoTaxsDefinition daoTaxsDefinition;

	@Before
	public void globalSetUp() {
	}

	@After
	public void tearDown() {

	}

	@Test
	public void test() {

	}
	// @Test
	// @Transactional
	// public void testSaveProportionnel() throws NoDataFoundException {
	// TaxDefinition taxDefinition =
	// TaxDefinitionBuilder.get().withApplicabilities(TaxeApplicability.Total).withDenominateur(1d).withNominateur(1d)
	// .withTaxeType(TaxeType.Proportionnel).build();
	// daoTaxsDefinition.saveOrUpdate(taxDefinition);
	// TaxDefinition fetched =
	// daoTaxsDefinition.loadById(taxDefinition.getId());
	// Assert.assertNotNull(fetched);
	// assertEquals(1, fetched.getApplicabilities().size());
	// assertEquals(TaxeType.Proportionnel, fetched.getTaxeType());
	// }
	//
	// @Test
	// @Transactional
	// public void testSaveFixe() throws NoDataFoundException {
	// TaxDefinition taxDefinition =
	// TaxDefinitionBuilder.get().withApplicabilities(TaxeApplicability.Total).withNominateur(1d)
	// .withTaxeType(TaxeType.Fixe).build();
	// daoTaxsDefinition.saveOrUpdate(taxDefinition);
	// TaxDefinition fetched =
	// daoTaxsDefinition.loadById(taxDefinition.getId());
	// Assert.assertNotNull(fetched);
	// assertEquals(1, fetched.getApplicabilities().size());
	// assertEquals(TaxeType.Fixe, fetched.getTaxeType());
	// }
	//
	// @Test(expected = ConstraintViolationException.class)
	// @Transactional
	// public void testShouldFailFixe() throws NoDataFoundException {
	// TaxDefinition taxDefinition =
	// TaxDefinitionBuilder.get().withApplicabilities(TaxeApplicability.Total).withTaxeType(TaxeType.Fixe).build();
	// daoTaxsDefinition.saveOrUpdate(taxDefinition);
	// daoTaxsDefinition.flush();
	// }
	//
	//
	// @Test(expected = ConstraintViolationException.class)
	// @Transactional
	// public void testShouldFailNominateur() throws NoDataFoundException {
	// TaxDefinition taxDefinition =
	// TaxDefinitionBuilder.get().withApplicabilities(TaxeApplicability.Total).withDenominateur(1d)
	// .withTaxeType(TaxeType.Proportionnel).build();
	// daoTaxsDefinition.saveOrUpdate(taxDefinition);
	// daoTaxsDefinition.flush();
	// }

}
