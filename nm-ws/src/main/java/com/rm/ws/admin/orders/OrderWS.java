package com.rm.ws.admin.orders;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rm.contract.clients.constants.ClientOptions;
import com.rm.contract.commons.constants.ModelOptions;
import com.rm.contract.orders.beans.old.CartBean;
import com.rm.contract.orders.beans.old.OrderFilterBean;
import com.rm.contract.orders.beans.old.OrderViewBean;
import com.rm.contract.orders.constants.OrderOptions;
import com.rm.contract.orders.constants.OrderStateType;
import com.rm.contract.orders.exceptions.InvalidCartException;
import com.rm.contract.prices.constants.OrderType;
import com.rm.soa.orders.SoaOrder;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("/orders/{lang}")
public class OrderWS {

	@Autowired
	private SoaOrder soaOrder;

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetch(@PathParam("lang") String lang, @QueryParam("restaurants") List<Long> ids, @QueryParam("from") Long from,
			@QueryParam("to") Long to, @QueryParam("reference") String reference, @QueryParam("priceFrom") Double priceFrom,
			@QueryParam("priceTo") Double priceTo, @QueryParam("client") Long client, @QueryParam("states") List<OrderStateType> states,
			@QueryParam("types") List<OrderType> orderTypes) throws JsonProcessingException, NoDataFoundException {
		OrderFilterBean filter = new OrderFilterBean();
		filter.setIdRestaurants(ids);
		filter.setFrom(from);
		filter.setTo(to);
		filter.setReference(reference);
		filter.setPriceFrom(priceFrom);
		filter.setPriceTo(priceTo);
		filter.setIdClient(client);
		filter.setStates(states);
		filter.setOrderTypes(orderTypes);
		filter.setCurrentLocale(lang);
		return Response.ok(soaOrder.fetch(filter, new ArrayList<ModelOptions>())).build();
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("lang") String lang, @PathParam("id") Long id) throws JsonProcessingException, NoDataFoundException {
		OrderFilterBean filter = new OrderFilterBean();
		filter.setIdOrder(id);
		filter.getOptions().add(OrderOptions.Cart);
		filter.getOptions().add(OrderOptions.NextStates);
		filter.getOptions().add(ClientOptions.LastAddress);
		filter.setCurrentLocale(lang);
		return Response.ok(soaOrder.fetch(filter, filter.getOptions()).iterator().next()).build();
	}

	@GET
	@Path("filter/{filter}/{limit}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetch(@PathParam("filter") String filter, @PathParam("limit") Long limit) throws JsonProcessingException, NoDataFoundException {
		OrderFilterBean filterB = new OrderFilterBean();
		filterB.setReference(filter);
		filterB.setCount(limit);
		return Response.ok(soaOrder.fetchReferences(filterB)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(@PathParam("lang") String lang, CartBean cartBean) throws JsonProcessingException, NoDataFoundException {
		try {
			return Response.ok(soaOrder.save(cartBean, lang)).build();
		} catch (InvalidCartException e) {
			return Response.status(400).entity(e.getCart()).build();
		}
	}
	
	@GET
	@Path("push/{id}/{state}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrderViewBean save(@PathParam("lang") String lang,@PathParam("id") Long id,@PathParam("state") OrderStateType state) throws NoDataFoundException  {
		OrderFilterBean filter = new OrderFilterBean();
		filter.getOptions().add(OrderOptions.Cart);
		filter.getOptions().add(OrderOptions.NextStates);
		filter.getOptions().add(ClientOptions.LastAddress);
		return soaOrder.pushState(id, state, lang, filter.getOptions());
	}

	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean delete(@PathParam("lang") String lang, @PathParam("id") Long id) {
		try {
			soaOrder.remove(id);
			return true;
		} catch (NoDataFoundException e) {
			return true;
		}
	}

}
