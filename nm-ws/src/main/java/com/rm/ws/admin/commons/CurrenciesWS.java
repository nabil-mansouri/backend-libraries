package com.rm.ws.admin.commons;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rm.contract.commons.beans.CurrencyDto;
import com.rm.soa.commons.SoaDevise;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("commons/currencies")
public class CurrenciesWS {
	@Autowired
	private SoaDevise soaDevise;

	@GET
	@Path("selected")
	@Transactional
	public CurrencyDto getSelected() throws NoDataFoundException {
		return soaDevise.getDefault();
	}

	@POST
	@Path("selected")
	@Transactional
	public CurrencyDto setDefault(CurrencyDto form) throws NoDataFoundException {
		soaDevise.setDefault(form);
		return form;
	}
}
