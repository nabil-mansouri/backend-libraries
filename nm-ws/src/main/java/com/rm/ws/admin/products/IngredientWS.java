package com.rm.ws.admin.products;

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
import com.rm.contract.products.forms.IngredientFormDto;
import com.rm.contract.products.views.IngredientViewDto;
import com.rm.dao.products.impl.IngredientQueryBuilder;
import com.rm.soa.products.SoaProductDefinition;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("/ingredients/{lang}")
@Transactional
public class IngredientWS {
	@Autowired
	private SoaProductDefinition soaProductDefinition;

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean delete(@PathParam("lang") String lang, @PathParam("id") Long id) {
		soaProductDefinition.removeIngredient(id);
		return true;
	}

	@GET
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	public IngredientFormDto create(@PathParam("lang") String lang) {
		return soaProductDefinition.createIngredient();
	}

	@GET
	@Path("edit/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public IngredientFormDto edit(@PathParam("id") Long id, @PathParam("lang") String lang)
			throws NoDataFoundException {
		return soaProductDefinition.editIngredient(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<IngredientViewDto> getTable(@PathParam("lang") String lang) {
		return getTable(lang, null, null);
	}

	@GET
	@Path("{first}/{count}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<IngredientViewDto> getTable(@PathParam("lang") String lang, @PathParam("first") Long first,
			@PathParam("count") Long count) {
		IngredientQueryBuilder query = IngredientQueryBuilder.get();
		query.withFirst(first).withLimit(count);
		return soaProductDefinition.fetch(query, new OptionsList(lang));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public IngredientViewDto save(@PathParam("lang") String lang, IngredientFormDto request)
			throws NoDataFoundException {
		return soaProductDefinition.saveOrUpdate(request, new OptionsList(lang));
	}

	@POST
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public IngredientViewDto edit(@PathParam("lang") String lang, @PathParam("id") Long id, IngredientFormDto request)
			throws NoDataFoundException {
		request.setId(id);
		return soaProductDefinition.saveOrUpdate(request, new OptionsList(lang));
	}
}
