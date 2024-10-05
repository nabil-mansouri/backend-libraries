package com.nm.prices.soa.computers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nm.prices.dtos.forms.old.PriceComputeBean;
import com.nm.prices.model.Price;

/**
 * 
 * @author Nabil
 * 
 */
public class OldPriceComputerProcessor {

	// @Autowired
	// @Qualifier(OldPriceComputer.PriceComputer)
	private Map<String, OldPriceComputer> strategies = new HashMap<String, OldPriceComputer>();

	public void compute(PriceComputeBean context, Price price) {
		compute(context, price, Arrays.asList(OldPriceComputer.DefaultStrategy, OldPriceComputer.AdditionnalStrategy));
	}

	public void computeFlexible(PriceComputeBean context, Price price) {
		compute(context, price, Arrays.asList(OldPriceComputer.FlexibleStrategy, OldPriceComputer.AdditionnalStrategy));
	}

	public void compute(PriceComputeBean context, Price price, List<String> strategies) {
		context.setId(price.getId());
		context.getStrategies().clear();
		context.getStrategies().addAll(strategies);
		for (String st : strategies) {
			if (!this.strategies.get(st).compute(context, price)) {
				return;
			}
		}
	}

}
