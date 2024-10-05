package com.nm.dictionnary.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.dictionnary.SoaDictionnary;
import com.nm.dictionnary.SoaDictionnaryImpl;
import com.nm.dictionnary.constants.EnumDictionnaryDomainDefault;
import com.nm.dictionnary.converters.ConverterDictionnaryEntityDefault;
import com.nm.dictionnary.converters.ConverterDictionnaryEntryDefault;
import com.nm.dictionnary.converters.ConverterDictionnaryValueDefault;
import com.nm.dictionnary.daos.DaoDictionnary;
import com.nm.dictionnary.daos.DaoDictionnaryImpl;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationDictionnary {
	public static final String MODULE_NAME = "dictionnary";

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(EnumDictionnaryDomainDefault.class);
	}

	@Bean
	public ConverterDictionnaryEntityDefault converterDictionnaryEntityDefault() {
		return new ConverterDictionnaryEntityDefault();
	}

	@Bean
	public ConverterDictionnaryEntryDefault converterDictionnaryEntryDefault(DaoDictionnary dao) {
		ConverterDictionnaryEntryDefault c = new ConverterDictionnaryEntryDefault();
		c.setDaoDictionnary(dao);
		return c;
	}

	@Bean
	public ConverterDictionnaryValueDefault converterDictionnaryValueDefault() {
		return new ConverterDictionnaryValueDefault();
	}

	@Bean
	public SoaDictionnary SoaDictionnaryImpl(DtoConverterRegistry registry) {
		com.nm.dictionnary.SoaDictionnaryImpl s = new SoaDictionnaryImpl();
		s.setRegistry(registry);
		return s;
	}

	@Bean
	public DaoDictionnary DaoDictionnaryImpl(DatabaseTemplateFactory fac) {
		DaoDictionnaryImpl d = new DaoDictionnaryImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}
}
