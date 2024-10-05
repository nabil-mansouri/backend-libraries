package com.rm.soa.discounts.converters.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.soa.discounts.beans.DiscountCacheBean;
import com.rm.soa.discounts.converters.DiscountCache;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Service
public class DiscountCacheImpl implements DiscountCache {

	// @Autowired
	// private SoaProductDefinition soaProductDefinition;
	// @Autowired
	// private SoaPrice soaPrice;

	@Transactional(readOnly = true)
	public PriceForm getPrice(DiscountCacheBean cache, Long product, String lang) throws NoDataFoundException {
		if (!cache.getPriceByProduct().containsKey(product)) {
			PriceFilterBean priceRequest = new PriceFilterBean();
			priceRequest.addProductId(product);
			priceRequest.setOnlyCurrent(true);
			// priceRequest.getOptions().addAll(Arrays.asList(PriceOptions.values()));
			// priceRequest.getOptions().add(ProductOptions.Parts);
			// TODO
			// Collection<PriceForm> response = soaPrice.fetch(priceRequest,
			// lang);
			// if (response.isEmpty()) {// Compensate absence of price
			// ProductDefinitionQueryBuilder q =
			// ProductDefinitionQueryBuilder.get().withId(product);
			// Collection<ProductViewDto> defs = soaProductDefinition.fetch(q,
			// new OptionsList(lang).withAddAll(priceRequest.getOptions()));
			// //
			// PriceForm priceBean = new PriceForm();
			// priceBean.setProduct(defs.iterator().next());
			// return priceBean;
			// } else {
			// PriceForm priceBean = response.iterator().next();
			// cache.getPriceByProduct().put(product, priceBean);
			// return priceBean;
			// }
		}
		return cache.getPriceByProduct().get(product);
	}

}
