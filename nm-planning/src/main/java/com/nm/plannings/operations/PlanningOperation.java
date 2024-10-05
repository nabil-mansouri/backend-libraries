package com.nm.plannings.operations;

import com.nm.plannings.dtos.DtoPlanningQuery;
import com.nm.plannings.dtos.DtoPlanningResult;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface PlanningOperation {
	DtoPlanningResult operation(DtoPlanningQuery query, OptionsList options) throws Exception;

	boolean accept(DtoPlanningQuery query);
}
