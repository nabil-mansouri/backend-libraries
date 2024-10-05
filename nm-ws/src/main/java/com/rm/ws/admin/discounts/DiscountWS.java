package com.rm.ws.admin.discounts;

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

import com.rm.contract.discounts.beans.DiscountFormBean;
import com.rm.soa.discounts.SoaDiscount;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("/discounts")
public class DiscountWS {
	@Autowired
	private SoaDiscount soaDiscount;

	@DELETE
	@Path("/{lang}/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean delete(@PathParam("id") Long id, @PathParam("lang") String lang) {
		try {
			soaDiscount.delete(id);
			return true;
		} catch (NoDataFoundException e) {
			return true;
		}
	}

	@GET
	@Path("/{lang}/edit/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DiscountFormBean edit(@PathParam("id") Long id, @PathParam("lang") String lang) throws NoDataFoundException {
		return soaDiscount.edit(id, lang);
	}

	@GET
	@Path("/{lang}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<DiscountFormBean> getTable(@PathParam("lang") String lang) throws NoDataFoundException {
		return getTable(lang, null, null);
	}

	@GET
	@Path("/{lang}/{first}/{count}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<DiscountFormBean> getTable(@PathParam("lang") String lang, @PathParam("first") Long first, @PathParam("count") Long count)
			throws NoDataFoundException {
		return soaDiscount.fetch(lang);
	}

	@POST
	@Path("/{lang}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DiscountFormBean save(@PathParam("lang") String lang, DiscountFormBean request) throws NoDataFoundException {
		return soaDiscount.save(request, lang);
	}

	@POST
	@Path("/{lang}/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DiscountFormBean save(@PathParam("id") Long id,@PathParam("lang") String lang, DiscountFormBean request) throws NoDataFoundException {
		request.setId(id);
		return soaDiscount.save(request, lang);
	}
}
