package com.rm.soa.discounts.computer;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rm.model.discounts.DiscountDefinition;

/**
 * 
 * @author Nabil
 * 
 */
@Component
public class DiscountComputerProcessor {

	@Autowired
	@Qualifier(DiscountComputer.DiscountComputer)
	private Map<String, DiscountComputer> strategies = new HashMap<String, DiscountComputer>();
	protected Collection<String> getStrategies() {
		return Arrays.asList(DiscountComputer.DiscountComputerFree,//
				DiscountComputer.DiscountComputerGift,//
				DiscountComputer.DiscountComputerOperation,//
				DiscountComputer.DiscountComputerReplacePrice,//
				DiscountComputer.DiscountComputerSpecial);
	}

	public DiscountComputerContext compute(CartBean cartBean, DiscountDefinition discount) {
		DiscountComputerContext context = new DiscountComputerContext();
		return compute(context, cartBean, discount);
	}

	public DiscountComputerContext compute(DiscountComputerContext context, CartBean cartBean, DiscountDefinition discount) {
		for (String s : getStrategies()) {
			strategies.get(s).compute(context, cartBean, discount);
		}
		return context;
	}

}
