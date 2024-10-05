package com.nm.tests.cms;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.app.locale.LocaleFormDto;
import com.nm.app.locale.SoaLocale;
import com.nm.cms.constants.CmsOptions;
import com.nm.cms.converters.CmsContentsComposedFormConverter;
import com.nm.cms.dtos.CmsDtoContentsComposedForm;
import com.nm.cms.models.CmsContentsComposed;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestCms.class)
public class TestCmsConverter {

	@Autowired
	private SoaLocale soaLocale;
	@Autowired
	private CmsContentsComposedFormConverter formConverter;
	//

	@Before
	public void globalSetUp() {
	}

	@Test
	@Transactional
	public void testShouldCreateValid() throws Exception {
		soaLocale.setDefaultLocale(new LocaleFormDto().setCode("FR"));
		soaLocale.setSelectedLocales(
				Arrays.asList(new LocaleFormDto().setCode("FR"), new LocaleFormDto().setCode("EN")));
		CmsDtoContentsComposedForm form = formConverter.toDto(new CmsContentsComposed(),
				new OptionsList().withOption(CmsOptions.FullForm));
		Assert.assertEquals(2, form.getContents().size());
		Assert.assertEquals(true, form.fetchByLang("FR").isSelected());
	}

	@Test
	@Transactional
	public void testShouldCreateInvalidValid() throws Exception {
		CmsDtoContentsComposedForm form = formConverter.toDto(new CmsContentsComposed(),
				new OptionsList().withOption(CmsOptions.FullForm));
		Assert.assertEquals(0, form.getContents().size());
		Assert.assertEquals(true, form.isNoDefaultLang());
		Assert.assertEquals(true, form.isNoSelectedLang());
	}
}
