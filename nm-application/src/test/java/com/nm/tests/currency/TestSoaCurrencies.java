package com.nm.tests.currency;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.app.currency.CurrencyDto;
import com.nm.app.currency.SoaDevise;
import com.nm.tests.ConfigurationTestApplication;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestApplication.class)
public class TestSoaCurrencies {

	@Autowired
	private SoaDevise soaDevise;
	//
	protected Log log = LogFactory.getLog(getClass());

	@Test
	@Transactional
	public void testSaveDefault() throws Exception {
		soaDevise.setDefault(new CurrencyDto().setCode("EN"));
		CurrencyDto test = soaDevise.getDefault();
		Assert.assertEquals("EN", test.getCode());
	}
}
