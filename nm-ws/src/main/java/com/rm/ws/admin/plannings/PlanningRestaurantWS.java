package com.rm.ws.admin.plannings;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rm.contract.plannings.beans.EventBean;
import com.rm.contract.plannings.beans.PlanningBean;
import com.rm.contract.plannings.constants.PlanningType;
import com.rm.contract.plannings.constants.SlotType;
import com.rm.soa.plannings.SoaPlanning;
import com.rm.soa.plannings.rules.EventRulesContext;
import com.rm.soa.plannings.rules.EventRulesContext.Filter;
import com.rm.soa.restaurants.SoaRestaurant;
import com.rm.utils.dates.DateUtilsMore;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Transactional
@Path("/planning/restaurant")
public class PlanningRestaurantWS {
	@Autowired
	private SoaPlanning soaPlanning;
	@Autowired
	private SoaRestaurant soaRestaurant;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PlanningBean view(@PathParam("id") Long id) throws Exception {
		return soaPlanning.getOrCreate(id, PlanningType.Restaurant);
	}

	@GET
	@Path("/open/{idPlanning}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EventBean> viewOpen(@PathParam("idPlanning") Long idPlanning, @PathParam("start") Long start,
			@PathParam("end") Long end) throws Exception {
		EventRulesContext context = soaRestaurant.getPlanningContext().setFilter(Filter.Weak);
		return soaPlanning.getEventsViewExclude(idPlanning, DateUtilsMore.fromJSLong(start),
				DateUtilsMore.fromJSLong(end), context);
	}

	@GET
	@Path("/close/{idPlanning}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EventBean> viewClose(@PathParam("idPlanning") Long idPlanning, @PathParam("start") Long start,
			@PathParam("end") Long end) throws Exception {
		EventRulesContext context = soaRestaurant.getPlanningContext().setFilter(Filter.Strong);
		return soaPlanning.getEventsViewExclude(idPlanning, DateUtilsMore.fromJSLong(start),
				DateUtilsMore.fromJSLong(end), context);
	}

	@GET
	@Path("/edit/open/{idPlanning}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EventBean> editOpen(@PathParam("idPlanning") Long idPlanning, @PathParam("start") Long start,
			@PathParam("end") Long end) throws Exception {
		return soaPlanning.getEventsEdit(idPlanning, DateUtilsMore.fromJSLong(start), DateUtilsMore.fromJSLong(end),
				SlotType.RestaurantOpen);
	}

	@GET
	@Path("/edit/close/{idPlanning}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EventBean> editClose(@PathParam("idPlanning") Long idPlanning, @PathParam("start") Long start,
			@PathParam("end") Long end) throws Exception {
		return soaPlanning.getEventsEdit(idPlanning, DateUtilsMore.fromJSLong(start), DateUtilsMore.fromJSLong(end),
				SlotType.RestaurantClose);
	}

}
