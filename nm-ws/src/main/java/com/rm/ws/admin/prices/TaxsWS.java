package com.rm.ws.admin.prices;

import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("/taxs")
public class TaxsWS {
	// @Autowired
	// private SoaPrice saoTarif;
	// @Autowired
	// private DaoTaxsDefinition daoTaxsDefinition;
	// TODO
	// @DELETE
	// @Path("{idTax}")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public Boolean delete(@PathParam("idTax") Long idTax) {
	// try {
	// saoTarif.removeTax(idTax);
	// daoTaxsDefinition.loadById(idTax);
	// return false;
	// } catch (NoDataFoundException e) {
	// return true;
	// }
	// }
	//
	// @GET
	// @Path("edit/{id}")
	// @Produces(MediaType.APPLICATION_JSON)
	// public TaxDefFormBean edit(@PathParam("id") Long id) throws
	// NoDataFoundException {
	// return saoTarif.editTax(id);
	// }
	//
	// @GET()
	// @Produces(MediaType.APPLICATION_JSON)
	// public Collection<TaxDefFormBean> getTable() {
	// return getTable(null, null);
	// }
	//
	// @GET
	// @Path("{first}/{count}")
	// @Produces(MediaType.APPLICATION_JSON)
	// public Collection<TaxDefFormBean> getTable(@PathParam("first") Long
	// first, @PathParam("count") Long count) {
	// TaxDefFilterBean request = new TaxDefFilterBean();
	// request.setFirst(first);
	// request.setCount(count);
	// return saoTarif.fetch(request);
	// }
	//
	// @POST
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public TaxDefFormBean saveOrUpdate(TaxDefFormBean request) throws
	// NoDataFoundException {
	// return saoTarif.saveOrUpdate(request);
	// }
	//
	// @POST
	// @Path("{id}")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public TaxDefFormBean saveOrUpdate(@PathParam("id") Long id,
	// TaxDefFormBean request) throws NoDataFoundException {
	// request.setId(id);
	// return saoTarif.saveOrUpdate(request);
	// }
}
