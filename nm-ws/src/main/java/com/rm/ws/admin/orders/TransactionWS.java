package com.rm.ws.admin.orders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rm.contract.clients.constants.ClientOptions;
import com.rm.contract.commons.constants.ModelOptions;
import com.rm.contract.orders.beans.old.OrderViewBean;
import com.rm.contract.orders.constants.OrderOptions;
import com.rm.contract.paiment.beans.TransactionBean;
import com.rm.soa.orders.SoaOrder;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("/orders/transaction/{idOrder}/{lang}")
public class TransactionWS {

	@Autowired
	private SoaOrder soaOrder;

	@POST
	@Path("/push/cash")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderViewBean save(@PathParam("lang") String lang, @PathParam("idOrder") Long idOrder, TransactionBean transaction)
			throws NoDataFoundException, IOException {
		return soaOrder.pushCash(idOrder, transaction, lang);
	}

	@POST
	@Path("/return/cash")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderViewBean returnCash(@PathParam("lang") String lang, @PathParam("idOrder") Long idOrder, TransactionBean transaction)
			throws NoDataFoundException, IOException {
		return soaOrder.returnCash(idOrder, transaction, lang);
	}

	@POST
	@Path("/push/check")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderViewBean pushCheck(@PathParam("lang") String lang, @PathParam("idOrder") Long idOrder, TransactionBean transaction)
			throws NoDataFoundException, IOException {
		return soaOrder.pushCheck(idOrder, transaction, lang);
	}

	@POST
	@Path("/push/electronics")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderViewBean pushElectronics(@PathParam("lang") String lang, @PathParam("idOrder") Long idOrder, TransactionBean transaction)
			throws NoDataFoundException, IOException {
		return soaOrder.pushElectronics(idOrder, transaction, lang);
	}

	@GET
	@Path("/commit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderViewBean commit(@PathParam("lang") String lang, @PathParam("idOrder") Long idOrder) throws NoDataFoundException, IOException {
		return soaOrder.forceCommit(idOrder, lang);
	}

	@GET
	@Path("/rollback/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderViewBean rollback(@PathParam("lang") String lang, @PathParam("idOrder") Long idOrder) throws NoDataFoundException, IOException {
		return soaOrder.rollback(idOrder, lang);
	}

	@GET
	@Path("/refund")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderViewBean refund(@PathParam("lang") String lang, @PathParam("idOrder") Long idOrder) throws NoDataFoundException, IOException {
		Collection<ModelOptions> options = new ArrayList<ModelOptions>();
		options.add(OrderOptions.Cart);
		options.add(ClientOptions.LastAddress);
		return soaOrder.refund(idOrder, lang, options);
	}

	@POST
	@Path("/rollback")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderViewBean rollback(@PathParam("lang") String lang, @PathParam("idOrder") Long idOrder, TransactionBean bean)
			throws NoDataFoundException, IOException {
		return soaOrder.rollback(idOrder, bean.getTransactionId());
	}

}
