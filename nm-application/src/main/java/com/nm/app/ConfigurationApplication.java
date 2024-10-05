package com.nm.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.app.async.AsyncExecutorHelper;
import com.nm.app.currency.SoaDevise;
import com.nm.app.currency.SoaDeviseImpl;
import com.nm.app.draft.DaoDraft;
import com.nm.app.draft.DaoDraftImpl;
import com.nm.app.draft.Draft;
import com.nm.app.draft.SoaDraftImpl;
import com.nm.app.history.ConverterHistoryDefault;
import com.nm.app.history.History;
import com.nm.app.history.SoaHistory;
import com.nm.app.history.SoaHistoryImpl;
import com.nm.app.job.AppJob;
import com.nm.app.job.AppJobDaoImpl;
import com.nm.app.job.AppJobHistoryDaoImpl;
import com.nm.app.job.AppJobTypeDefault;
import com.nm.app.locale.SoaLocaleImpl;
import com.nm.app.log.GeneralLog;
import com.nm.app.log.GeneralLogConverter;
import com.nm.app.log.GeneralLogConverterImpl;
import com.nm.app.log.GeneralLogDao;
import com.nm.app.log.GeneralLogDaoImpl;
import com.nm.app.log.SoaGeneralLogService;
import com.nm.app.log.SoaGeneralLogServiceImpl;
import com.nm.app.mail.SoaEmailImpl;
import com.nm.app.triggers.Trigger;
import com.nm.app.triggers.TriggerEventEnum.TriggerEventEnumDefault;
import com.nm.config.SoaAppConfig;
import com.nm.config.SoaModuleConfig;
import com.nm.datas.SoaAppMemory;
import com.nm.utils.configurations.HibernateConfigPart;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationApplication {
	public static final String MODULE_NAME = "application";

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(AppJobTypeDefault.class);
		reg.put(TriggerEventEnumDefault.class);
	}

	@Bean
	public AsyncExecutorHelper asyncExecutorHelper(SoaAppMemory soa, SoaGeneralLogService log) {
		AsyncExecutorHelper p = new AsyncExecutorHelper();
		p.setLogService(log);
		p.setMemoryService(soa);
		return p;
	}

	@Bean
	public SoaDevise soaDeviseImpl(SoaModuleConfig soa) {
		SoaDeviseImpl p = new SoaDeviseImpl();
		p.setSoaModuleConfig(soa);
		return p;
	}

	@Bean
	public DaoDraftImpl daoDraftImpl(DatabaseTemplateFactory fac) {
		DaoDraftImpl p = new DaoDraftImpl();
		p.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return p;
	}

	@Bean
	public SoaDraftImpl soaDraftImpl(DaoDraft fac) {
		SoaDraftImpl p = new SoaDraftImpl();
		p.setDaoDraft(fac);
		return p;
	}

	@Bean
	public ConverterHistoryDefault converterHistoryDefault(DtoConverterRegistry fac) {
		ConverterHistoryDefault p = new ConverterHistoryDefault();
		p.setRegistry(fac);
		return p;
	}

	@Bean
	public SoaHistory soaHistoryImpl(DtoConverterRegistry fac) {
		SoaHistoryImpl p = new SoaHistoryImpl();
		p.setRegistry(fac);
		return p;
	}

	@Bean
	public HibernateConfigPart hibernateConfig() {
		HibernateConfigPart h = new HibernateConfigPart();
		h.setModuleName(MODULE_NAME);
		h.add(Draft.class);
		h.add(History.class);
		h.add(AppJob.class);
		h.add(GeneralLog.class);
		h.add(Trigger.class);
		return h;
	}

	@Bean
	public AppJobDaoImpl appJobDaoImpl(DatabaseTemplateFactory fac) {
		AppJobDaoImpl p = new AppJobDaoImpl();
		p.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return p;
	}

	@Bean
	public AppJobHistoryDaoImpl appJobHistoryDaoImpl(DatabaseTemplateFactory fac) {
		AppJobHistoryDaoImpl p = new AppJobHistoryDaoImpl();
		p.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return p;
	}

	@Bean
	public SoaLocaleImpl soaLocaleImpl(SoaModuleConfig fac) {
		SoaLocaleImpl p = new SoaLocaleImpl();
		p.setSoaModuleConfig(fac);
		return p;
	}

	@Bean
	public GeneralLogConverter generalLogConverterImpl() {
		return new GeneralLogConverterImpl();
	}

	@Bean
	public GeneralLogDaoImpl generalLogDaoImpl(DatabaseTemplateFactory fac) {
		GeneralLogDaoImpl p = new GeneralLogDaoImpl();
		p.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return p;
	}

	@Bean
	public SoaGeneralLogServiceImpl soaGeneralLogServiceImpl(GeneralLogDao logDao, //
			GeneralLogConverter log) {
		SoaGeneralLogServiceImpl p = new SoaGeneralLogServiceImpl();
		p.setGeneralLogDao(logDao);
		p.setLogConverter(log);
		return p;
	}

	@Bean
	public SoaEmailImpl soaEmailImpl(SoaAppConfig soa, //
			org.springframework.core.env.Environment env) {
		SoaEmailImpl p = new SoaEmailImpl();
		p.setConfigService(soa);
		p.setEnable(env.getProperty("email.enable", Boolean.class, true));
		return p;
	}

}
