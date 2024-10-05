package com.nm.products.converter.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nm.products.constants.NavigationHeadState;
import com.nm.products.constants.ProductNodeType;
import com.nm.products.converter.ProductDefinitionGraphConverter;
import com.nm.products.converter.ProductDefinitionViewConverter;
import com.nm.products.dtos.navigation.NavigationBean;
import com.nm.products.dtos.navigation.NavigationNode;
import com.nm.products.dtos.navigation.NavigationPath;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductAsListDto;
import com.nm.products.dtos.views.ProductAsTreeDto;
import com.nm.products.dtos.views.ProductAsTreeIdentifier;
import com.nm.products.dtos.views.ProductNodeDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.products.model.ProductDefinitionPart;
import com.nm.products.soa.impl.ProductDtoCache;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.graphs.GraphInfo;
import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.finder.GraphFinder;
import com.nm.utils.graphs.finder.GraphFinderPath;
import com.nm.utils.graphs.finder.IGraphIdentifier;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;
import com.nm.utils.graphs.listeners.IteratorListenerGraphInfo;

/**
 * 
 * @author Nabil
 * 
 */
public class ProductDefinitionGraphConverterImpl implements ProductDefinitionGraphConverter {
	protected static Log log = LogFactory.getLog(ProductDefinitionGraphConverter.class);
	private ProductDefinitionViewConverter viewConverter;

	public void setViewConverter(ProductDefinitionViewConverter viewConverter) {
		this.viewConverter = viewConverter;
	}

	public ProductAsListDto toDto(ProductDefinition product, final OptionsList options) {
		final ProductAsListDto bean = new ProductAsListDto();
		final ProductDtoCache cache = new ProductDtoCache(viewConverter, options);
		GraphIteratorBuilder.buildDeep().iterate(product, new IteratorListenerGraphInfo() {
			public void onFounded(GraphInfo node) {
				if (node.getCurrent() instanceof ProductDefinition) {
					// CONVERT NOT DEEP
					ProductNodeDto nodeL = new ProductNodeDto();
					nodeL.setType(ProductNodeType.PRODUCT);
					nodeL.setProduct(cache.get((ProductDefinition) node.getCurrent()));
					// SET PART PARENT
					if (node.hasParent()) {
						nodeL.setType(ProductNodeType.PRODUCT_PART);
						nodeL.setPart(cache.get((ProductDefinitionPart) node.getParent().getCurrent()));
						bean.getNodes().add(nodeL);
					} else {
						bean.getNodes().add(nodeL);
					}
				} else if (node.getCurrent() instanceof ProductDefinitionPart) {
					// CONVERT NOT DEEP
					ProductNodeDto nodeL = new ProductNodeDto();
					nodeL.setType(ProductNodeType.PART);
					nodeL.setPart(cache.get((ProductDefinitionPart) node.getCurrent()));
					bean.getNodes().add(nodeL);
				} else if (node.getCurrent() instanceof IngredientDefinition) {
					// CONVERT NOT DEEP
					ProductNodeDto nodeL = new ProductNodeDto();
					nodeL.setType(ProductNodeType.INGREDIENT);
					nodeL.setIngredient(cache.get((IngredientDefinition) node.getCurrent()));
					bean.getNodes().add(nodeL);
				} else {
					log.error("Unknwonk node type for :" + node.getCurrent());
				}
			}

		});
		return bean;
	}

	protected void toDto(ProductAsTreeDto parent, NavigationNode node, OptionsList options) {
		IngredientViewDto ing = node.getBody().getIngredient();
		if (ing != null) {
			ProductNodeDto nodeL = new ProductNodeDto();
			nodeL.setType(ProductNodeType.INGREDIENT);
			nodeL.setProduct(node.getBody().getProduct());
			nodeL.setIngredient(ing);
			parent.createChild(true).setNode(nodeL);
		}
	}

	public ProductAsTreeDto toDto(NavigationBean navigation, OptionsList options) {
		ProductAsTreeDto tree = new ProductAsTreeDto();
		tree.setRoot(true);
		GraphFinder finder = new GraphFinder(GraphIteratorBuilder.buildDeep());
		Collection<IGraphIdentifier> ids = new ArrayList<IGraphIdentifier>();
		// ROOT is not always visible
		ProductNodeDto nodeR = new ProductNodeDto();
		nodeR.setType(ProductNodeType.PRODUCT);
		nodeR.setProduct(navigation.getRoot());
		tree.setNode(nodeR);
		//
		for (NavigationNode node : navigation.getHistory()) {
			NavigationPath path = node.find(NavigationHeadState.Current);
			if (path.founded()) {
				if (path.getItem().isRoot()) {
					// IF ROOT VISIBLE-> do only ingredients
					toDto(tree, node, options);
				} else if (path.getItem().getPart() != null) {
					// Only if not empty
					if (node.getBody().getProduct() != null) {
						ProductViewDto product = path.getItem().getParent();
						ids.clear();
						ids.add(new ProductAsTreeIdentifier(product).add(ProductNodeType.PRODUCT)
								.add(ProductNodeType.PRODUCT_PART));
						GraphFinderPath<IGraph> gPath = finder.findLast(tree, ids);
						//
						if (gPath.founded()) {
							ProductAsTreeDto grandParent = (ProductAsTreeDto) gPath.getFounded();
							//
							ProductAsTreeDto parent = grandParent.createChild(true);
							ProductNodeDto nodeL = new ProductNodeDto();
							nodeL.setType(ProductNodeType.PRODUCT_PART);
							nodeL.setProduct(node.getBody().getProduct());
							nodeL.setPart(path.getItem().getPart());
							parent.setNode(nodeL);
							//
							toDto(parent, node, options);
						} else {
							throw new IllegalStateException(
									"Could not found parent of part" + product + "/" + path.getItem().getPart());
						}
					}
				} else {
					throw new IllegalStateException("This is not root nor part..." + path);
				}
			}
		}
		return tree;
	}
}
