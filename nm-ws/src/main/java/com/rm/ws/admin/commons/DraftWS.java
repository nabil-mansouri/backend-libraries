package com.rm.ws.admin.commons;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rm.contract.commons.DraftBean;
import com.rm.contract.commons.constants.DraftType;
import com.rm.soa.commons.SoaDraft;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("draft")
public class DraftWS {
	@Autowired
	private SoaDraft soaDraft;

	@Path("/{type}/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean delete(@PathParam("type") DraftType type, @PathParam("id") Long id) throws NoDataFoundException {
		soaDraft.remove(id);
		return true;
	}

	@Path("/{type}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<DraftBean> getDraft(@PathParam("type") DraftType type) {
		return soaDraft.getDrafts(type);
	}

	@Path("/{type}/{id}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DraftBean getDraft(@PathParam("type") DraftType type, @PathParam("id") Long id) throws NoDataFoundException {
		return soaDraft.getDraft(id);
	}

	@Path("/last/modified/{type}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DraftBean getLastModifierDraft(@PathParam("type") DraftType type) throws NoDataFoundException {
		return soaDraft.getLastModifiedDraft(type);
	}

	@Path("/{type}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DraftBean saveDraft(String payload, @PathParam("type") DraftType type) {
		return soaDraft.saveDraft(type, payload);
	}

	@Path("/{type}/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DraftBean saveDraft(String payload, @PathParam("type") DraftType type, @PathParam("id") Long id) throws NoDataFoundException {
		return soaDraft.update(type, payload, id);
	}

	@Path("/{type}/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DraftBean update(String payload, @PathParam("type") DraftType type, @PathParam("id") Long id) throws NoDataFoundException {
		return soaDraft.update(type, payload, id);
	}
}
