package com.nm.paiments.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nm.paiments.computers.TransactionComputer;
import com.nm.paiments.computers.TransactionComputerImpl;
import com.nm.paiments.daos.DaoPayer;
import com.nm.paiments.daos.DaoPayerImpl;
import com.nm.paiments.daos.DaoPaymentMean;
import com.nm.paiments.daos.DaoPaymentMeanImpl;
import com.nm.paiments.daos.DaoTransaction;
import com.nm.paiments.daos.DaoTransactionImpl;
import com.nm.paiments.daos.DaoTransactionSubject;
import com.nm.paiments.daos.DaoTransactionSubjectImpl;
import com.nm.paiments.dtos.converters.PayerAnyConverterImpl;
import com.nm.paiments.dtos.converters.PayerSimpleConverterImpl;
import com.nm.paiments.dtos.converters.PaymentConverterImpl;
import com.nm.paiments.dtos.converters.PaymentMeanConverterImpl;
import com.nm.paiments.dtos.converters.TransactionConverterImpl;
import com.nm.paiments.dtos.converters.TransactionSubjectConverterImpl;
import com.nm.payment.finder.TransactionFinder;
import com.nm.payment.finder.TransactionFinderImpl;
import com.nm.payment.soa.SoaPaiment;
import com.nm.payment.soa.SoaPaimentImpl;
import com.nm.utils.db.DatabaseTemplateFactory;
import com.nm.utils.dtos.DtoConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Configuration
public class ConfigurationPaiment {
	public static final String MODULE_NAME = "payment";

	@Bean
	public TransactionComputerImpl transactionComputerImpl() {
		return new TransactionComputerImpl();
	}

	@Bean
	public DaoPayer daoPayerImpl(DatabaseTemplateFactory fac) {
		DaoPayerImpl d = new DaoPayerImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}

	@Bean
	public DaoPaymentMean daoPaymentMeanImpl(DatabaseTemplateFactory fac) {
		DaoPaymentMeanImpl d = new DaoPaymentMeanImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}

	@Bean
	public DaoTransactionImpl daoTransactionImpl(DatabaseTemplateFactory fac) {
		DaoTransactionImpl d = new DaoTransactionImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}

	@Bean
	public DaoTransactionSubject daoTransactionSubjectImpl(DatabaseTemplateFactory fac) {
		DaoTransactionSubjectImpl d = new DaoTransactionSubjectImpl();
		d.setHibernateTemplate(fac.hibernateResource(MODULE_NAME));
		return d;
	}

	@Bean
	public PayerAnyConverterImpl payerAnyConverterImpl(DaoPayer fac) {
		PayerAnyConverterImpl d = new PayerAnyConverterImpl();
		d.setDaoPayer(fac);
		return d;
	}

	@Bean
	public PaymentMeanConverterImpl paymentMeanConverterImpl(DaoPaymentMean fac) {
		PaymentMeanConverterImpl d = new PaymentMeanConverterImpl();
		d.setDaoMean(fac);
		return d;
	}

	@Bean
	public PayerSimpleConverterImpl payerSimpleConverterImpl(DaoPayer fac) {
		PayerSimpleConverterImpl d = new PayerSimpleConverterImpl();
		d.setDaoPayer(fac);
		return d;
	}

	@Bean
	public TransactionConverterImpl transactionConverterImpl(DaoTransaction fac, TransactionComputer c,
			DaoTransactionSubject dd) {
		TransactionConverterImpl d = new TransactionConverterImpl();
		d.setComputer(c);
		d.setDaoTransaction(fac);
		d.setSubjectDao(dd);
		return d;
	}

	@Bean
	public SoaPaiment soaPaimentImpl(DtoConverterRegistry dC, DaoTransaction dT, DaoTransactionSubject daoS,
			DaoPaymentMean daoPM) {
		SoaPaimentImpl d = new SoaPaimentImpl();
		d.setDaoPayment(daoPM);
		d.setDaoSubject(daoS);
		d.setDaoTransaction(dT);
		d.setRegistry(dC);
		return d;
	}

	@Bean
	public TransactionSubjectConverterImpl transactionSubjectConverterImpl(DaoTransactionSubject dd) {
		TransactionSubjectConverterImpl d = new TransactionSubjectConverterImpl();
		d.setDaoSubject(dd);
		return d;
	}

	@Bean
	public PaymentConverterImpl paymentConverterImpl() {
		return new PaymentConverterImpl();
	}

	@Bean
	public TransactionFinder transactionFinderImpl() {
		return new TransactionFinderImpl();
	}
}
