package com.nm.tests.cms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class TestCrudCms {

	@Autowired
	private SoaLocale soaLocale;
	@Autowired
	private CmsContentsComposedFormConverter formConverter;
	//
	protected Log log = LogFactory.getLog(getClass());

	@Before
	public void setUp() throws Exception {
		Collection<LocaleFormDto> forms = new ArrayList<LocaleFormDto>();
		forms.add(new LocaleFormDto().setCode("FR"));
		forms.add(new LocaleFormDto().setCode("EN"));
		soaLocale.setSelectedLocales(forms);
		soaLocale.setDefaultLocale(new LocaleFormDto().setCode("EN"));
	}

	@Test
	@Transactional
	public void testShouldCreate() throws Exception {
		CmsDtoContentsComposedForm cms = formConverter.toDto(new CmsContentsComposed(),
				new OptionsList().withAddAll(Arrays.asList(CmsOptions.values())));
		Assert.assertEquals(2, cms.getContents().size());
		Assert.assertTrue(cms.createIfAbsent("EN").isSelected());
	}
}
