package com.rm.ws.admin.commons;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rm.contract.commons.beans.LocaleFormDto;
import com.rm.soa.commons.SoaLocale;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("commons/locales")
public class LocalesWS {
	@Autowired
	private SoaLocale soaLocale;

	@GET
	@Path("selected")
	@Transactional
	public Collection<LocaleFormDto> getSelected() {
		return soaLocale.getSelectedLocales();
	}

	@GET
	@Path("default")
	@Transactional
	public LocaleFormDto getDefault() throws NoDataFoundException {
		return soaLocale.getDefaultLocale();
	}

	@POST
	@Path("selected")
	@Transactional
	public Collection<LocaleFormDto> setSelected(Collection<LocaleFormDto> form) {
		soaLocale.setSelectedLocales(form);
		return form;
	}

	@POST
	@Path("default")
	@Transactional
	public LocaleFormDto setDefault(LocaleFormDto form) {
		soaLocale.setDefaultLocale(form);
		return form;
	}
}
