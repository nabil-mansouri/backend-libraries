package com.nm.prices.soa.synchronizer;

/**
 * 
 * @author Nabil
 * 
 */
@Deprecated
public class PriceSynchronizerProcessor {

	// @Autowired
	// private SoaProductDefinition soaProductDefinition;
	// @Autowired
	// private PriceCacheBuilder priceCacheBuilder;
	// private Map<String, PriceSynchronizer> strategies = new HashMap<String,
	// PriceSynchronizer>();

	// @Transactional(readOnly = true)
	// public PriceSynchronizerContext process(PriceComputeBean reference,
	// AbstractGraph test) {
	// return process(reference, test,
	// Arrays.asList(PriceSynchronizer.PriceSynchronizerDefinition,
	// PriceSynchronizer.PriceSynchronizerSelected));
	// }

	// @Transactional(readOnly = true)
	// public PriceSynchronizerContext process(PriceComputeBean reference,
	// AbstractGraph test, final List<String> strategies) {
	// PriceComputeCache cache = priceCacheBuilder.build(reference);
	// final PriceSynchronizerContext context = new
	// PriceSynchronizerContext(cache);
	// final GraphPathBuilder pathBuilder =
	// soaProductDefinition.getPathBuilder();
	// //
	// pathBuilder.down(test);
	// reference.setPath(pathBuilder.getPath());
	// cache.getPrices().put(pathBuilder.getPath() , reference);
	// // Iterate products
	// GraphIteratorBuilder.buildDeep().iterate(test, new
	// DefaultIteratorListener() {
	// public boolean onFounded(AbstractGraph node) {
	// for (String stra : strategies) {
	// PriceSynchronizerProcessor.this.strategies.get(stra).synchronyze(context,
	// node, pathBuilder.getPath());
	// }
	// return true;
	// }
	// }, pathBuilder);
	// return context;
	// }

}
