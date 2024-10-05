package com.nm.cms.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.app.locale.SoaLocale;
import com.nm.cms.constants.CmsPartType.CmsPartTypeDefault;
import com.nm.cms.constants.CmsTableHeader.CmsTableHeaderDefault;
import com.nm.cms.converters.CmsContentsComposedBridgeConverter;
import com.nm.cms.converters.CmsContentsComposedFormConverter;
import com.nm.cms.converters.CmsContentsComposedViewConverter;
import com.nm.cms.converters.CmsContentsIndexedTableConverter;
import com.nm.cms.converters.CmsContentsPrimitiveConverter;
import com.nm.cms.converters.CmsContentsTableConverter;
import com.nm.cms.converters.CmsContentsTableFileConverter;
import com.nm.cms.converters.CmsConverterImpl;
import com.nm.cms.soa.SoaCms;
import com.nm.cms.soa.SoaCmsImpl;
import com.nm.datas.SoaAppData;
import com.nm.datas.daos.DaoAppData;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationCms {
	public static final String MODULE_NAME = "cms";

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(CmsPartTypeDefault.class);
		reg.put(CmsTableHeaderDefault.class);
	}

	@Bean
	public CmsContentsComposedBridgeConverter cmsContentsComposedBridgeConverter(SoaLocale soa) {
		CmsContentsComposedBridgeConverter c = new CmsContentsComposedBridgeConverter();
		c.setSoaLocale(soa);
		return c;
	}

	@Bean
	public CmsContentsComposedFormConverter cmsContentsComposedFormConverter(SoaLocale soa, DaoAppData dao,
			SoaAppData soaApp) {
		CmsContentsComposedFormConverter c = new CmsContentsComposedFormConverter();
		c.setDaoAppData(dao);
		c.setSoaAppData(soaApp);
		c.setSoaLocale(soa);
		return c;
	}

	@Bean
	public CmsContentsComposedViewConverter cmsContentsComposedViewConverter(SoaLocale soa) {
		CmsContentsComposedViewConverter c = new CmsContentsComposedViewConverter();
		c.setSoaLocale(soa);
		return c;
	}

	@Bean
	public CmsContentsIndexedTableConverter cmsContentsIndexedTableConverter() {
		return new CmsContentsIndexedTableConverter();
	}

	@Bean
	public CmsContentsPrimitiveConverter cmsContentsPrimitiveConverter() {
		return new CmsContentsPrimitiveConverter();
	}

	@Bean
	public CmsContentsTableConverter cmsContentsTableConverter() {
		return new CmsContentsTableConverter();
	}

	@Bean
	public CmsContentsTableFileConverter cmsContentsTableFileConverter() {
		return new CmsContentsTableFileConverter();
	}

	@Bean
	public CmsConverterImpl cmsConverterImpl() {
		return new CmsConverterImpl();
	}

	@Bean
	public SoaCms SoaCmsImpl(DtoConverterRegistry r) {
		SoaCmsImpl s = new SoaCmsImpl();
		s.setRegistry(r);
		return s;
	}
}
