package com.nm.shop.bridge;

import com.nm.plannings.soa.rules.EventRulesContext;

/**
 * 
 * @author nabilmansouri
 *
 */
public class ShopEventRuleContext extends EventRulesContext {

	public ShopEventRuleContext() {
		withStrongers(ShopSlotType.ShopClose).setFilter(EventRulesContext.Filter.Weak);
	}

}
