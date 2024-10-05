package com.rm.ws.admin.orders;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rm.contract.orders.beans.old.CartBean;
import com.rm.soa.carts.checkers.CartCheckerProcessor;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("/carts")
public class CartWS {

	@Autowired
	private CartCheckerProcessor processor;

	@POST
	@Path("{lang}/check")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CartBean check(@PathParam("lang") String lang, CartBean cartBean) {
		processor.process(cartBean, lang);
		cartBean.validate();
		return cartBean;
	}

}
