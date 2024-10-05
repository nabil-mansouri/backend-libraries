package com.nm.products.converter.impl;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.nm.products.converter.ProductInstanceConverter;
import com.nm.products.dtos.forms.ProductInstanceDto;
import com.nm.products.dtos.forms.ProductPartInstanceDto;
import com.nm.products.dtos.options.ProductOptions;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductPartViewDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.IngredientInstance;
import com.nm.products.model.ProductDefinition;
import com.nm.products.model.ProductDefinitionPart;
import com.nm.products.model.ProductInstance;
import com.nm.products.model.ProductInstancePart;
import com.nm.products.soa.cache.ProductCacheBuilder;
import com.nm.products.soa.cache.ProductDefinitionCache;
import com.nm.products.soa.cache.ProductInstanceConverterContext;
import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public class ProductInstanceConverterImpl implements ProductInstanceConverter {
	private ProductCacheBuilder productCacheBuilder;

	public void setProductCacheBuilder(ProductCacheBuilder productCacheBuilder) {
		this.productCacheBuilder = productCacheBuilder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rm.soa.products.converter.ProductInstanceConverter#convert(com.rm
	 * .contract.products.beans.ProductDefBean)
	 */
	@Transactional(readOnly = true)
	public ProductInstance convert(ProductViewDto root) throws NoDataFoundException {
		ProductDefinitionCache cache = productCacheBuilder.buildFromId(root);
		final ProductInstanceConverterContext context = new ProductInstanceConverterContext(cache);
		// final GraphPathBuilder pathBuilder = new DefaultGraphPathBuilder();
		//
		// GraphIteratorBuilder.buildDeep().iterate(root, new
		// DefaultIteratorListener() {
		// @Override
		// public boolean onFounded(AbstractGraph node) {
		// if (node.root()) {
		// ProductViewDto rootNode = (ProductViewDto) node;
		// ProductInstance instance = new ProductInstance();
		// convert(context.getProductsById(rootNode.getId()), instance,
		// rootNode.getPrice());
		// context.getProductByPath().put(pathBuilder.getPath(), instance);
		// context.setRoot(instance);
		// }
		// if (node.nodetype().equals(ProductNodeType.PART.name())) {
		// // Get parent
		// pathBuilder.up(1);
		// ProductInstance parent =
		// context.getProductByPath(pathBuilder.getPath());
		// pathBuilder.down(node);
		// //
		// ProductPartViewDto partNode = (ProductPartViewDto) node;
		// if (partNode.hasSelected()) {
		// // Save part only if there is product selected
		// ProductInstancePart part = new ProductInstancePart();
		// convert(context.getPartsById(partNode.getId()), part);
		// parent.getParts().add(part);
		// //
		// ProductViewDto prodNode = partNode.getSelected();
		// ProductInstance instance = new ProductInstance();
		// convert(context.getProductsById(prodNode.getId()), instance,
		// prodNode.getPrice());
		// part.setChild(instance);
		// // Set product
		// pathBuilder.down(prodNode);
		// context.put(pathBuilder.getPath(), instance);
		// pathBuilder.up(1);
		// //
		// for (IngredientViewDto ing : prodNode.getExcluded()) {
		// IngredientInstance ingI = new IngredientInstance();
		// convert(context.getIngById(ing.getId()), ingI);
		// instance.getExcluded().add(ingI);
		// }
		// }
		// }
		// return true;
		// }
		// });
		return context.getRoot();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rm.soa.products.converter.ProductInstanceConverter#convert(com.rm
	 * .model.products.ProductDefinition, com.rm.model.products.ProductInstance,
	 * java.lang.Double)
	 */
	@Transactional(readOnly = true)
	public ProductInstance convert(ProductDefinition definition, ProductInstance instance, Double price) {
		// for(CmsContentsImage
		// im:definition.getContent().findImages(CmsPartTypeDefault.Main)){
		// instance.setImage(im.getImage().get);
		// }
		// instance.getImages().addAll(definition.getImage().getImages());
		// instance.setName(definition.getContent().getName());
		instance.setIdProduct(definition.getId());
		instance.setPrice(price);
		instance.setType(definition.getType());
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rm.soa.products.converter.ProductInstanceConverter#convert(com.rm
	 * .model.products.IngredientDefinition,
	 * com.rm.model.products.IngredientInstance)
	 */
	@Transactional(readOnly = true)
	public IngredientInstance convert(IngredientDefinition definition, IngredientInstance instance) {
		// instance.setImage(definition.getImage().getImage());
		// instance.setName(definition.getContent().getName());
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rm.soa.products.converter.ProductInstanceConverter#convert(com.rm
	 * .model.products.ProductDefinitionPart,
	 * com.rm.model.products.ProductInstancePart)
	 */
	@Transactional(readOnly = true)
	public ProductInstancePart convert(ProductDefinitionPart part, ProductInstancePart instance) {
		instance.setIdPart(part.getId());
		instance.setMandatory(part.getMandatory());
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rm.soa.products.converter.ProductInstanceConverter#convert(com.rm
	 * .model.products.ProductInstance, java.lang.String, java.util.Collection)
	 */
	@Transactional(readOnly = true)
	public ProductInstanceDto convert(ProductInstance instance, String lang, Collection<ModelOptions> options) {
		ProductInstanceDto view = new ProductInstanceDto();
		view.setId(instance.getId());
		// view.setImg(uploadService.toAbsoluteURL(instance.getImage()));
		//
		if (lang != null) {
			view.setName(instance.getName(lang));
		}
		//
		view.setCreatedAt(instance.getCreated());
		// for (String img : instance.getImages()) {
		// view.addImgs(uploadService.toAbsoluteURL(img));
		// }
		for (String curr : instance.getNameLangage()) {
			view.addLang(curr);
		}
		if (options.contains(ProductOptions.Ingredients)) {
			for (IngredientInstance ing : instance.getExcluded()) {
				view.add(convert(ing, lang));
			}
		}
		if (options.contains(ProductOptions.Parts)) {
			// for (ProductInstancePart part : instance.getParts()) {
			// TODO
			// view.add(convert(part, lang, options));
			// }
		}

		view.setAllTexts(StringUtils.join(Arrays.asList(instance.getName()), "\n"));
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rm.soa.products.converter.ProductInstanceConverter#convert(com.rm
	 * .model.products.ProductInstancePart,
	 * com.rm.contract.products.beans.ProductPartDefBean)
	 */
	@Transactional(readOnly = true)
	public ProductInstancePart convert(ProductInstancePart part, ProductPartViewDto def) {
		part.setIdPart(def.getId());
		part.setMandatory(def.isMandatory());
		return part;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rm.soa.products.converter.ProductInstanceConverter#convert(com.rm
	 * .model.products.IngredientInstance, java.lang.String)
	 */
	@Transactional(readOnly = true)
	public IngredientViewDto convert(IngredientInstance ingredient, String currentLoc) {
		IngredientViewDto view = new IngredientViewDto();
		// view.setImg(uploadService.toAbsoluteURL(ingredient.getImage()));
		//
		if (currentLoc != null) {
			view.setName(ingredient.getName(currentLoc));
		}
		//
		view.setId(ingredient.getId());
		view.setAllTexts(StringUtils.join(Arrays.asList(ingredient.getName()), "\n"));
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rm.soa.products.converter.ProductInstanceConverter#convert(com.rm
	 * .model.products.ProductInstancePart, java.lang.String,
	 * java.util.Collection)
	 */
	@Transactional(readOnly = true)
	public ProductPartInstanceDto convert(ProductInstancePart instance, String lang, Collection<ModelOptions> options) {
		ProductPartInstanceDto view = new ProductPartInstanceDto();
		view.setId(instance.getId());
		view.setMandatory(instance.getMandatory());
		if (instance.getChild() != null) {
			view.setSelected(convert(instance.getChild(), lang, options));
		}
		return view;
	}
}
