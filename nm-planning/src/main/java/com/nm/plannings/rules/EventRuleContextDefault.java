package com.nm.plannings.rules;

import com.nm.plannings.constants.SlotTypeDefault;
import com.nm.plannings.dtos.DtoPlanningQuery.Filter;

/**
 * 
 * @author nabilmansouri
 *
 */
public class EventRuleContextDefault extends EventRulesContext {

	public EventRuleContextDefault() {
		withStrongers(SlotTypeDefault.Close).setFilter(Filter.Weak);
	}

}
