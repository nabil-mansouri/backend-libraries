package com.rm.ws.admin.products;

import java.io.IOException;
import java.util.Collection;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rm.contract.commons.beans.OptionsList;
import com.rm.contract.products.forms.ProductFormDto;
import com.rm.contract.products.views.ProductViewDto;
import com.rm.soa.products.SoaProductDefinition;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("/products/drafts/{lang}")
public class ProductDraftsWS {
	@Autowired
	private SoaProductDefinition soaProductDefinition;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ProductViewDto> fetch(@PathParam("lang") String lang) {
		return soaProductDefinition.fetchDrafts(new OptionsList(lang));
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductFormDto fetch(@PathParam("lang") String lang, @PathParam("id") Long id)
			throws NoDataFoundException, IOException {
		return soaProductDefinition.editDraft(id, new OptionsList(lang));
	}

	@POST
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductFormDto save(@PathParam("lang") String lang, ProductFormDto form)
			throws IOException, NoDataFoundException {
		return soaProductDefinition.saveOrUpdateDraft(form);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ProductFormDto fetch(@PathParam("lang") String lang, @PathParam("id") Long id, ProductFormDto form)
			throws IOException, NoDataFoundException {
		return soaProductDefinition.saveOrUpdateDraft(form);
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response remove(@PathParam("lang") String lang, @PathParam("id") Long id)
			throws NoDataFoundException {
		soaProductDefinition.removeDraft(id);
		return javax.ws.rs.core.Response.ok().build();
	}
}
