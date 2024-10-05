package com.rm.ws.admin.restaurants;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rm.contract.commons.beans.OptionsList;
import com.rm.contract.restaurants.beans.RestaurantFormBean;
import com.rm.contract.restaurants.beans.ShopViewDto;
import com.rm.contract.restaurants.constants.RestaurantOptions;
import com.rm.dao.restaurants.impl.RestaurantQueryBuilder;
import com.rm.soa.restaurants.SoaRestaurant;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("/restaurants/{lang}")
@Transactional
public class RestaurantWS {
	@Autowired
	private SoaRestaurant soaRestaurant;

	@GET
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public RestaurantFormBean create(@PathParam("lang") String lang) {
		return soaRestaurant.createRestaurant();
	}

	@GET
	@Path("/currents/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ShopViewDto> currents(@PathParam("lang") String lang) {
		return soaRestaurant.fetch(RestaurantQueryBuilder.get(),
				new OptionsList(lang).withOption(RestaurantOptions.States));
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean delete(@PathParam("id") Long id, @PathParam("lang") String lang) {
		try {
			soaRestaurant.removeRestaurant(id);
			return true;
		} catch (NoDataFoundException e) {
			return true;
		}
	}

	@GET
	@Path("edit/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public RestaurantFormBean edit(@PathParam("id") Long id, @PathParam("lang") String lang)
			throws NoDataFoundException {
		return soaRestaurant.editRestaurant(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ShopViewDto> fetch(@PathParam("lang") String lang) {
		return fetch(lang, null, null);
	}

	@GET
	@Path("/{first}/{count}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ShopViewDto> fetch(@PathParam("lang") String lang, @PathParam("first") Long first,
			@PathParam("count") Long count) {
		RestaurantQueryBuilder query = RestaurantQueryBuilder.get().withFirst(first).withLimit(count);
		return soaRestaurant.fetch(query, new OptionsList(lang));
	}

	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RestaurantFormBean save(@PathParam("id") Long id, @PathParam("lang") String lang, RestaurantFormBean form)
			throws NoDataFoundException {
		form.setId(id);
		return soaRestaurant.saveOrUpdate(form, new OptionsList(lang));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RestaurantFormBean save(@PathParam("lang") String lang, RestaurantFormBean form)
			throws NoDataFoundException {
		return soaRestaurant.saveOrUpdate(form, new OptionsList(lang));
	}
}
