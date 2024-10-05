package com.rm.ws.admin.commons;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rm.contract.commons.ModuleConfigDto;
import com.rm.soa.commons.SoaModuleConfig;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("module/configs")
public class ModuleConfigWS {
	@Autowired
	private SoaModuleConfig soaModuleConfig;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ModuleConfigDto> get() {
		return soaModuleConfig.getConfigs();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleConfigDto update(ModuleConfigDto bean) throws NoDataFoundException {
		return soaModuleConfig.saveOrUpdate(bean);
	}

}
