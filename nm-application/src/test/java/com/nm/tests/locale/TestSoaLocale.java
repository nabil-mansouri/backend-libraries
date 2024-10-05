package com.nm.tests.locale;

import java.util.ArrayList;
import java.util.Collection;

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

import com.nm.app.locale.LocaleFormDto;
import com.nm.app.locale.SoaLocale;
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
public class TestSoaLocale {

	@Autowired
	private SoaLocale soaLocale;
	//
	protected Log log = LogFactory.getLog(getClass());

	@Test
	@Transactional
	public void testSaveSelected() throws Exception {
		Collection<LocaleFormDto> forms = new ArrayList<LocaleFormDto>();
		forms.add(new LocaleFormDto().setCode("FR"));
		forms.add(new LocaleFormDto().setCode("EN"));
		soaLocale.setSelectedLocales(forms);
		Collection<LocaleFormDto> test = soaLocale.getSelectedLocales();
		Assert.assertEquals(2, test.size());
		Assert.assertEquals("FR", test.iterator().next().getCode());
	}

	@Test
	@Transactional
	public void testSaveDefault() throws Exception {
		soaLocale.setDefaultLocale(new LocaleFormDto().setCode("EN"));
		LocaleFormDto test = soaLocale.getDefaultLocale();
		Assert.assertEquals("EN", test.getCode());
	}
}
