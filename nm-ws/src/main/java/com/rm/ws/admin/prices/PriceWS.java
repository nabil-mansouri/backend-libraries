package com.rm.ws.admin.prices;

import java.util.Arrays;
import java.util.Collection;

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
import org.springframework.transaction.annotation.Transactional;

import com.rm.contract.commons.beans.OptionsList;
import com.rm.contract.prices.constants.OrderType;
import com.rm.contract.prices.constants.PriceFormOptions;
import com.rm.contract.prices.filters.PriceFilterBean;
import com.rm.contract.prices.forms.PriceFormBean;
import com.rm.contract.prices.forms.old.PriceForm;
import com.rm.contract.prices.views.PriceViewBean;
import com.rm.dao.prices.impl.PriceValueQueryBuilder;
import com.rm.soa.prices.SoaPrice;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Transactional
@Path("/prices/{lang}")
public class PriceWS {
	@Autowired
	private SoaPrice soaPrice;

	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean delete(@PathParam("lang") String lang, @PathParam("id") Long id) {
		soaPrice.removePrice(id);
		return true;
	}

	@GET
	@Path("edit/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PriceFormBean edit(@PathParam("id") Long id, @PathParam("lang") String lang) throws NoDataFoundException {
		return soaPrice.editPrice(id);
	}

	@GET
	@Path("flexible/{idProduct}")
	@Produces(MediaType.APPLICATION_JSON)
	public PriceForm flexible(@PathParam("idProduct") Long idProduct, @PathParam("lang") String lang)
			throws NoDataFoundException {
		// return saoTarif.flexible(idProduct, lang);
		return null;
	}

	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<PriceViewBean> getAll(@PathParam("lang") String lang) throws NoDataFoundException {
		return soaPrice.fetch(PriceValueQueryBuilder.get(), new OptionsList(lang));
	}

	@GET
	@Path("{first}/{count}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<PriceViewBean> getAll(@PathParam("lang") String lang, @PathParam("first") Long first,
			@PathParam("count") Long count) throws NoDataFoundException {
		return soaPrice.fetch(PriceValueQueryBuilder.get().withLimit(count).withFirst(first), new OptionsList(lang));
	}

	@GET
	@Path("restaurant/{idResto}/type/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<PriceViewBean> getPricesByRestoAndType(@PathParam("lang") String lang,
			@PathParam("idResto") Long idResto, @PathParam("type") OrderType type) throws NoDataFoundException {
		PriceFilterBean request = new PriceFilterBean();
		request.setOrderType(type);
		request.setIdRestaurant(idResto);
		return soaPrice.fetch(request, new OptionsList(lang));
	}

	@GET
	@Path("currents/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<PriceViewBean> getCurrents(@QueryParam("category") Long cat,
			@QueryParam("restaurant") Long restaurant, @QueryParam("type") OrderType type,
			@PathParam("lang") String lang) throws NoDataFoundException {
		PriceFilterBean request = new PriceFilterBean();
		request.setOnlyCurrent(true);
		//
		request.setIdCategory(cat);
		request.setIdRestaurant(restaurant);
		request.setOrderType(type);
		//
		// request.getOptions().add(PriceOptions.ComputeTotal);
		return soaPrice.fetch(request, new OptionsList(lang));
	}

	@GET
	@Path("compute/{prodId}/{type}/{resto}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrent(@PathParam("type") OrderType type, @PathParam("resto") Long resto,
			@PathParam("prodId") Long prodId, @PathParam("lang") String lang) throws NoDataFoundException {
		PriceFilterBean request = new PriceFilterBean();
		request.setIdRestaurant(resto);
		request.setOrderType(type);
		request.setIdProducts(Arrays.asList(prodId));
		// request.getOptions().addAll(Arrays.asList(PriceOptions.values()));
		// request.getOptions().addAll(Arrays.asList(ProductOptions.values()));
		Collection<PriceViewBean> prices = soaPrice.fetch(request, new OptionsList(lang));
		if (prices.isEmpty()) {
			return Response.serverError().status(404).build();
		} else {
			return Response.ok(prices.iterator().next()).build();
		}
	}

	@GET
	@Path("product/{idProduct}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<PriceViewBean> getByProduct(@PathParam("lang") String lang,
			@PathParam("idProduct") Long idProduct) throws NoDataFoundException {
		PriceFilterBean request = new PriceFilterBean();
		request.getIdProducts().add(idProduct);
		// request.getOptions().addAll(Lists.newArrayList(PriceOptions.values()));
		// request.getOptions().addAll(Lists.newArrayList(ProductOptions.values()));
		return soaPrice.fetch(request, new OptionsList(lang));
	}

	@POST
	@Path("filter/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<PriceViewBean> filter(@PathParam("lang") String lang, PriceFilterBean request)
			throws NoDataFoundException {
		return soaPrice.fetch(request, new OptionsList(lang));
	}

	@GET
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public PriceFormBean create(@PathParam("lang") String lang) throws NoDataFoundException {
		return soaPrice.createPrice();
	}

	@GET
	@Path("/filter")
	@Produces(MediaType.APPLICATION_JSON)
	public PriceFilterBean filter(@PathParam("lang") String lang) throws NoDataFoundException {
		return soaPrice.createFilter();
	}

	@POST
	@Path("/refresh")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PriceFormBean refresh(@PathParam("lang") String lang, PriceFormBean form) throws NoDataFoundException {
		return soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PriceFormBean saveOrUpdate(@PathParam("lang") String lang, PriceFormBean request)
			throws NoDataFoundException {
		return soaPrice.saveOrUpdate(request, new OptionsList(lang));
	}

	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PriceFormBean saveOrUpdate(@PathParam("id") Long id, @PathParam("lang") String lang, PriceFormBean request)
			throws NoDataFoundException {
		request.setId(id);
		return soaPrice.saveOrUpdate(request, new OptionsList(lang));
	}

}
