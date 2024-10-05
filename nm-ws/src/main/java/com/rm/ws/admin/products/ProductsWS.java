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
import com.rm.contract.products.constants.ProductFormOptions;
import com.rm.contract.products.constants.ProductState;
import com.rm.contract.products.constants.ProductType;
import com.rm.contract.products.forms.ProductFormDto;
import com.rm.contract.products.views.ProductViewDto;
import com.rm.dao.products.impl.ProductDefinitionQueryBuilder;
import com.rm.soa.products.SoaProductDefinition;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("/products/{lang}")
@Transactional()
public class ProductsWS {
	@Autowired
	private SoaProductDefinition soaProductDefinition;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ProductViewDto> fetch(@PathParam("lang") String lang) {
		return soaProductDefinition.fetch(ProductDefinitionQueryBuilder.get().withType(ProductType.Product),
				new OptionsList(lang));
	}

	@GET
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductFormDto create(@PathParam("lang") String lang) {
		return soaProductDefinition.createProduct();
	}

	@GET
	@Path("edit/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductFormDto edit(@PathParam("id") Long id, @PathParam("lang") String lang) throws NoDataFoundException {
		return soaProductDefinition.editProduct(id);
	}

	@GET
	@Path("publish/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductViewDto publish(@PathParam("id") Long id, @PathParam("lang") String lang)
			throws NoDataFoundException {
		return soaProductDefinition.setProductState(id, ProductState.Publish, new OptionsList(lang));
	}

	@GET
	@Path("unpublish/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductViewDto unpublish(@PathParam("id") Long id, @PathParam("lang") String lang)
			throws NoDataFoundException {
		return soaProductDefinition.setProductState(id, ProductState.UnPublish, new OptionsList(lang));
	}

	@POST
	@Path("refresh/category")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductFormDto refreshCat(ProductFormDto form, @PathParam("lang") String lang)
			throws NoDataFoundException {
		return soaProductDefinition.refresh(form, new OptionsList(lang).withOption(ProductFormOptions.Categories));
	}

	@POST
	@Path("refresh/ingredient")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductFormDto refreshIng(ProductFormDto form, @PathParam("lang") String lang)
			throws NoDataFoundException {
		return soaProductDefinition.refresh(form, new OptionsList(lang).withOption(ProductFormOptions.Ingredients));
	}

	@POST
	@Path("refresh/product")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductFormDto refreshProd(ProductFormDto form, @PathParam("lang") String lang)
			throws NoDataFoundException {
		return soaProductDefinition.refresh(form, new OptionsList(lang).withOption(ProductFormOptions.Parts));
	}

	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean delete(@PathParam("lang") String lang, @PathParam("id") Long id) {
		soaProductDefinition.removeProduct(id);
		return true;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProductViewDto save(@PathParam("lang") String lang, ProductFormDto request) throws NoDataFoundException {
		return soaProductDefinition.saveOrUpdate(request, new OptionsList(lang));
	}

	@POST
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProductViewDto save(@PathParam("lang") String lang, ProductFormDto request, @PathParam("id") Long id)
			throws NoDataFoundException {
		request.setId(id);
		return soaProductDefinition.saveOrUpdate(request, new OptionsList(lang));
	}
}
