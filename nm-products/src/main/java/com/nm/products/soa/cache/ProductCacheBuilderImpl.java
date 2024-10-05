package com.nm.products.soa.cache;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.nm.products.dao.DaoIngredient;
import com.nm.products.dao.DaoProductDefinition;
import com.nm.products.dao.DaoProductDefinitionPart;
import com.nm.products.dtos.old.ProductDefCache;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.products.model.ProductDefinitionPart;

/**
 * 
 * @author Nabil
 * 
 */
public class ProductCacheBuilderImpl implements ProductCacheBuilder {
	private DaoProductDefinition daoProduct;
	private DaoProductDefinitionPart daoPart;
	private DaoIngredient daoIngredient;

	public void setDaoIngredient(DaoIngredient daoIngredient) {
		this.daoIngredient = daoIngredient;
	}

	public void setDaoPart(DaoProductDefinitionPart daoPart) {
		this.daoPart = daoPart;
	}

	public void setDaoProduct(DaoProductDefinition daoProduct) {
		this.daoProduct = daoProduct;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rm.soa.products.converter.impl.ProductCacheBuilder#buildFromId(com
	 * .rm.contract.products.beans.ProductDefBean)
	 */
	@Transactional(readOnly = true)
	public ProductDefinitionCache buildFromId(ProductViewDto node) {
		ProductDefinitionCache bean = new ProductDefinitionCache();
		final Set<Long> productsIds = new HashSet<Long>();
		final Set<Long> partsId = new HashSet<Long>();
		final Set<Long> ingIds = new HashSet<Long>();
		// GraphIteratorBuilder.buildDeep().iterate(node, new IteratorListener()
		// {
		//
		// public boolean stop() {
		// return false;
		// }
		//
		// public boolean onFounded(AbstractGraph node) {
		// if (node.nodetype().equals(ProductNodeType.PRODUCT.name())) {
		// ProductViewDto product = (ProductViewDto) node;
		// productsIds.add(product.getId());
		// for (IngredientViewDto ing : product.getExcluded()) {
		// ingIds.add(ing.getId());
		// }
		// } else if (node.nodetype().equals(ProductNodeType.PART.name())) {
		// ProductPartViewDto part = (ProductPartViewDto) node;
		// partsId.add(part.getId());
		// if (part.hasSelected()) {
		// productsIds.add(part.getSelected().getId());
		// }
		// }
		// return true;
		// }
		//
		// public boolean doSetParent() {
		// return true;
		// }
		// });
		//
		Collection<ProductDefinitionPart> parts = daoPart.findByIds(partsId);
		Collection<ProductDefinition> products = daoProduct.findByIds(productsIds);
		Collection<IngredientDefinition> ingredients = daoIngredient.findByIds(ingIds);
		//
		for (ProductDefinitionPart part : parts) {
			bean.getPartsById().put(part.getId(), part);
		}
		for (ProductDefinition prod : products) {
			bean.getProductsById().put(prod.getId(), prod);
		}
		for (IngredientDefinition prod : ingredients) {
			bean.getIngById().put(prod.getId(), prod);
		}
		return bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rm.soa.products.converter.impl.ProductCacheBuilder#buildFromPath(
	 * com.rm.contract.products.beans.ProductDefBean)
	 */
	@Transactional(readOnly = true)
	public ProductDefCache buildFromPath(ProductViewDto reference) {
		final ProductDefCache bean = new ProductDefCache();
		// final GraphPathBuilder builder = new DefaultGraphPathBuilder();
		// GraphIteratorBuilder.buildDeep().iterate(reference, new
		// IteratorListener() {
		//
		// public boolean stop() {
		// return false;
		// }
		//
		// public boolean onFounded(AbstractGraph node) {
		// if (node.nodetype().equals(ProductNodeType.PRODUCT.name())) {
		// bean.put(builder.getPath(), (ProductViewDto) node);
		// } else if (node.nodetype().equals(ProductNodeType.PART.name())) {
		// bean.put(builder.getPath(), (ProductPartViewDto) node);
		// bean.incrementCardinality(builder.getPath());
		// } else if (node.nodetype().equals(ProductNodeType.INGREDIENT.name()))
		// {
		// bean.put(builder.getPath(), (IngredientViewDto) node);
		// }
		// return true;
		// }
		//
		// public boolean doSetParent() {
		// return true;
		// }
		// }, builder);
		return bean;
	}
}
