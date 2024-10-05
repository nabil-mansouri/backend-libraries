package com.rm.ws.admin.products;

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
import com.rm.contract.products.forms.CategoryFormDto;
import com.rm.contract.products.views.CategoryViewDto;
import com.rm.soa.products.SoaProductDefinition;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("/categories/{lang}")
@Transactional
public class CategoryWS {
	@Autowired
	private SoaProductDefinition soaProductDefinition;

	@GET
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	public CategoryFormDto create(@PathParam("lang") String lang) {
		return soaProductDefinition.createCategory();
	}

	@GET
	@Path("create/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CategoryFormDto create(@PathParam("lang") String lang, @PathParam("id") Long id) {
		return soaProductDefinition.createCategory(id);
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean delete(@PathParam("id") Long id) {
		soaProductDefinition.removeCategory(id);
		return true;
	}

	@GET
	@Path("edit/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CategoryFormDto edit(@PathParam("id") Long id, @PathParam("lang") String lang) throws NoDataFoundException {
		return soaProductDefinition.editCategory(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CategoryViewDto save(@PathParam("lang") String lang, CategoryFormDto request) throws NoDataFoundException {
		return soaProductDefinition.saveOrUpdate(request, new OptionsList(lang));
	}

	@POST
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CategoryViewDto edit(@PathParam("lang") String lang, @PathParam("id") Long id, CategoryFormDto request)
			throws NoDataFoundException {
		request.setId(id);
		return soaProductDefinition.saveOrUpdate(request, new OptionsList(lang));
	}
}
