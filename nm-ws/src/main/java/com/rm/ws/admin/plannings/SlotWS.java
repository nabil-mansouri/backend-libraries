package com.rm.ws.admin.plannings;

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

import com.rm.contract.plannings.beans.TimeSlotBean;
import com.rm.soa.plannings.SoaPlanning;
import com.rm.utils.dao.NoDataFoundException;
import com.rm.utils.dates.DateUtilsMore;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Transactional
@Path("/plannings/slots")
public class SlotWS {
	@Autowired
	private SoaPlanning soaPlanning; 

	@DELETE
	@Path("{idSlot}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean delete(@PathParam("idSlot") Long idSlot) {
		try {
			soaPlanning.removeSlot(idSlot);
			return true;
		} catch (NoDataFoundException e) {
			return true;
		}
	}

	@DELETE
	@Path("{idSlot}/{start}/{end}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean delete(@PathParam("idSlot") Long idSlot, @PathParam("start") Long start,
			@PathParam("end") Long end) {
		try {
			soaPlanning.removeSlot(idSlot, DateUtilsMore.fromJSLong(start), DateUtilsMore.fromJSLong(end));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@GET
	@Path("/{idSlot}")
	@Produces(MediaType.APPLICATION_JSON)
	public TimeSlotBean edit(@PathParam("idSlot") Long idSlot) throws NoDataFoundException {
		return soaPlanning.editSlot(idSlot);
	}

	@POST
	@Path("/{idPlanning}")
	@Produces(MediaType.APPLICATION_JSON)
	public TimeSlotBean saveOrUpdate(@PathParam("idPlanning") Long idPlanning, TimeSlotBean form) throws Exception {
		soaPlanning.saveOrUpdate(idPlanning, form);
		return form;
	}

}
