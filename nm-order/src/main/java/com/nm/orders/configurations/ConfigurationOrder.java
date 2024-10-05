package com.nm.orders.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.nm.orders.constants.OrderTreeNodeType.OrderTreeNodeTypeDefault;
import com.nm.orders.dao.DaoOrderAccount;
import com.nm.orders.dao.DaoOrderAccountImpl;
import com.nm.orders.dao.DaoOrderState;
import com.nm.orders.dao.DaoOrderStateImpl;
import com.nm.orders.dtos.converters.OrderAccountConverterImpl;
import com.nm.orders.dtos.converters.OrderContextConverterImpl;
import com.nm.orders.dtos.converters.OrderConverterImpl;
import com.nm.orders.dtos.converters.OrderDetailsSimpleConverterImpl;
import com.nm.orders.dtos.converters.OrderDetailsTreeConverterImpl;
import com.nm.orders.dtos.converters.TransactionSubjectOrderConverterImpl;
import com.nm.orders.soa.SoaOrder;
import com.nm.orders.soa.SoaOrderImpl;
import com.nm.paiments.daos.DaoTransactionSubject;
import com.nm.payment.soa.SoaPaiment;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
@Import(ConfigurationOrderCriterias.class)
public class ConfigurationOrder {
	public static final String MODULE_NAME = "order";

	@Autowired
	public void setReg(EnumJsonConverterRegistry reg) {
		reg.put(OrderTreeNodeTypeDefault.class);
	}

	@Bean
	public DaoOrderAccount daoOrderAccountImpl(DatabaseTemplateFactory fac) {
		DaoOrderAccountImpl d = new DaoOrderAccountImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}

	@Bean
	public DaoOrderState daoOrderStateImpl(DatabaseTemplateFactory fac) {
		DaoOrderStateImpl d = new DaoOrderStateImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}

	@Bean
	public OrderAccountConverterImpl orderAccountConverterImpl(DaoOrderAccount dao) {
		OrderAccountConverterImpl d = new OrderAccountConverterImpl();
		d.setDaoOrderAccount(dao);
		return d;
	}

	@Bean
	public OrderContextConverterImpl orderContextConverterImpl() {
		return new OrderContextConverterImpl();
	}

	@Bean
	public OrderConverterImpl orderConverterImpl() {
		return new OrderConverterImpl();
	}

	@Bean
	public OrderDetailsSimpleConverterImpl orderDetailsSimpleConverterImpl() {
		return new OrderDetailsSimpleConverterImpl();
	}

	@Bean
	public OrderDetailsTreeConverterImpl orderDetailsTreeConverterImpl() {
		return new OrderDetailsTreeConverterImpl();
	}

	@Bean
	public TransactionSubjectOrderConverterImpl transactionSubjectOrderConverterImpl() {
		return new TransactionSubjectOrderConverterImpl();
	}

	@Bean
	public SoaOrder soaOrderImpl(DaoOrderAccount daO, DtoConverterRegistry dto, DaoTransactionSubject daoT,
			SoaPaiment soa) {
		SoaOrderImpl soaO = new SoaOrderImpl();
		soaO.setDaoAccount(daO);
		soaO.setDaoTransactionSubject(daoT);
		soaO.setRegistry(dto);
		soaO.setSoaPaiment(soa);
		return soaO;
	}

}
