package com.nm.datas.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.datas.SoaAppData;
import com.nm.datas.SoaAppDataImpl;
import com.nm.datas.SoaAppMemoryImpl;
import com.nm.datas.constants.AppDataDestination.AppDataDestinationDefault;
import com.nm.datas.constants.FolderType.FolderTypeDefault;
import com.nm.datas.constants.ModuleConfigKeyData;
import com.nm.datas.converters.ConverterAppDataDtoImpl;
import com.nm.datas.daos.DaoAppData;
import com.nm.datas.daos.DaoAppDataImpl;
import com.nm.datas.daos.DaoAppMemory;
import com.nm.datas.daos.DaoAppMemoryImpl;
import com.nm.datas.processors.AppDataProcessorDB;
import com.nm.datas.processors.AppDataProcessorFileSystem;
import com.nm.datas.processors.AppDataProcessorURL;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationDatas {
	public static final String MODULE_NAME = "datas";

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(AppDataDestinationDefault.class);
		reg.put(FolderTypeDefault.class);
		reg.put(ModuleConfigKeyData.class);
	}

	@Bean
	public ConverterAppDataDtoImpl converterAppDataDtoImpl() {
		return new ConverterAppDataDtoImpl();
	}

	@Bean
	public DaoAppDataImpl daoAppDataImpl(DatabaseTemplateFactory fac) {
		com.nm.datas.daos.DaoAppDataImpl d = new DaoAppDataImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}

	@Bean
	public DaoAppMemoryImpl daoAppMemoryImpl(DatabaseTemplateFactory fac) {
		DaoAppMemoryImpl d = new DaoAppMemoryImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}

	@Bean
	public AppDataProcessorDB appDataProcessorDB() {
		return new AppDataProcessorDB();
	}

	@Bean
	public AppDataProcessorFileSystem appDataProcessorFileSystem() {
		return new AppDataProcessorFileSystem();
	}

	@Bean
	public AppDataProcessorURL appDataProcessorURL() {
		return new AppDataProcessorURL();
	}

	@Bean
	public SoaAppData SoaAppDataImpl(DaoAppData dao, DtoConverterRegistry r) {
		SoaAppDataImpl s = new SoaAppDataImpl();
		s.setAppDataDao(dao);
		s.setRegistry(r);
		return s;
	}

	@Bean
	public SoaAppMemoryImpl SoaAppMemoryImpl(DaoAppMemory dao) {
		SoaAppMemoryImpl s = new SoaAppMemoryImpl();
		s.setAppMemoryDao(dao);
		return s;
	}
}
