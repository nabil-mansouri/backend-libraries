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

import com.rm.contract.prices.forms.OrderTypeFormDto;
import com.rm.soa.commons.SoaOrderType;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("commons/ordertype")
public class OrderTypeWS {
	@Autowired
	private SoaOrderType soaOrderType;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<OrderTypeFormDto> create() throws NoDataFoundException {
		return soaOrderType.getOrderTypes();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<OrderTypeFormDto> update(Collection<OrderTypeFormDto> bean) throws NoDataFoundException {
		soaOrderType.setSelectedOrderTypesBean(bean);
		return bean;
	}

}
