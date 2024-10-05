package com.rm.ws.admin.clients;

import java.util.Arrays;
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

import com.rm.contract.clients.beans.ClientFilterBean;
import com.rm.contract.clients.beans.ClientForm;
import com.rm.contract.clients.constants.ClientOptions;
import com.rm.soa.clients.SoaClient;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("/client")
public class ClientWS {
	@Autowired
	private SoaClient soaClient;

	@GET
	@Path("filter/{filter}/{limit}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ClientForm> filter(@PathParam("limit") Long limit, @PathParam("filter") String filter) {
		ClientFilterBean request = new ClientFilterBean();
		request.setFirst(0l);
		request.setLimit(limit);
		request.setText(filter);
		request.getOptions().addAll(Arrays.asList(ClientOptions.values()));
		return soaClient.fetch(request);
	}

	@POST
	@Path("filter")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ClientForm> filter(ClientFilterBean request) {
		return soaClient.fetch(request);
	}

	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean delete(@PathParam("id") Long id) {
		try {
			soaClient.delete(id);
			return true;
		} catch (NoDataFoundException e) {
			return true;
		}
	}

	@GET
	@Path("edit/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ClientForm edit(@PathParam("id") Long id) throws NoDataFoundException {
		return soaClient.edit(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ClientForm> getTable() {
		return getTable(null, null);
	}

	@GET
	@Path("/{first}/{count}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ClientForm> getTable(@PathParam("first") Long first, @PathParam("count") Long count) {
		ClientFilterBean request = new ClientFilterBean();
		request.setFirst(first);
		request.setLimit(count);
		return soaClient.fetch(request);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ClientForm save(ClientForm request) throws NoDataFoundException {
		return soaClient.save(request);
	}
}
