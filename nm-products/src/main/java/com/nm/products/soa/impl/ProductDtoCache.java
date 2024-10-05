package com.nm.products.soa.impl;

import java.util.concurrent.ConcurrentHashMap;

import com.nm.products.converter.ProductDefinitionViewConverter;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductPartViewDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.products.model.ProductDefinitionPart;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author nabilmansouri
 *
 */
public class ProductDtoCache {
	private final ProductDefinitionViewConverter converter;
	private final OptionsList options;
	private ConcurrentHashMap<Long, ProductViewDto> dtos = new ConcurrentHashMap<Long, ProductViewDto>();
	private ConcurrentHashMap<Long, ProductPartViewDto> dtosP = new ConcurrentHashMap<Long, ProductPartViewDto>();
	private ConcurrentHashMap<Long, IngredientViewDto> dtosI = new ConcurrentHashMap<Long, IngredientViewDto>();

	public ProductDtoCache(ProductDefinitionViewConverter converter) {
		this.converter = converter;
		this.options = new OptionsList();
	}

	public ProductDtoCache(ProductDefinitionViewConverter converter, OptionsList options) {
		this.converter = converter;
		this.options = options;
	}

	protected void push(ProductViewDto r) {
		dtos.put(r.getId(), r);
	}

	protected ProductViewDto getSafe(ProductDefinition r) {
		if (exists(r)) {
			return dtos.get(r.getId());
		} else {
			ProductViewDto dto = converter.toDto(r, options);
			push(dto);
			return dto;
		}
	}

	protected ProductViewDto getSafe(ProductViewDto r) {
		if (exists(r)) {
			return dtos.get(r.getId());
		} else {
			push(r);
			return r;
		}
	}

	public ProductViewDto get(ProductDefinition r) {
		return this.getSafe(r);
	}

	public ProductViewDto get(ProductViewDto r) {
		return this.getSafe(r);
	}

	protected boolean exists(ProductDefinition r) {
		return dtos.containsKey(r.getId());
	}

	protected boolean exists(ProductViewDto r) {
		return dtos.containsKey(r.getId());
	}

	//
	protected ProductPartViewDto getSafe(ProductDefinitionPart r) {
		if (exists(r)) {
			return dtosP.get(r.getId());
		} else {
			ProductPartViewDto dto = converter.toDto(r, options);
			push(dto);
			return dto;
		}
	}

	protected void push(ProductPartViewDto r) {
		dtosP.put(r.getId(), r);
	}

	protected ProductPartViewDto getSafe(ProductPartViewDto r) {
		if (exists(r)) {
			return dtosP.get(r.getId());
		} else {
			push(r);
			return r;
		}
	}

	public ProductPartViewDto get(ProductDefinitionPart r) {
		return this.getSafe(r);
	}

	public ProductPartViewDto get(ProductPartViewDto r) {
		return this.getSafe(r);
	}

	protected boolean exists(ProductDefinitionPart r) {
		return dtosP.containsKey(r.getId());
	}

	protected boolean exists(ProductPartViewDto r) {
		return dtosP.containsKey(r.getId());
	}

	//
	//
	protected IngredientViewDto getSafe(IngredientDefinition r) {
		if (exists(r)) {
			return dtosI.get(r.getId());
		} else {
			IngredientViewDto dto = converter.toDto(r, options);
			push(dto);
			return dto;
		}
	}

	protected void push(IngredientViewDto r) {
		dtosI.put(r.getId(), r);
	}

	protected IngredientViewDto getSafe(IngredientViewDto r) {
		if (exists(r)) {
			return dtosI.get(r.getId());
		} else {
			push(r);
			return r;
		}
	}

	public IngredientViewDto get(IngredientDefinition r) {
		return this.getSafe(r);
	}

	public IngredientViewDto get(IngredientViewDto r) {
		return this.getSafe(r);
	}

	protected boolean exists(IngredientDefinition r) {
		return dtosI.containsKey(r.getId());
	}

	protected boolean exists(IngredientViewDto r) {
		return dtosI.containsKey(r.getId());
	}
}
