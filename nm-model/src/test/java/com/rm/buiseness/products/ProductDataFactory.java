package com.rm.buiseness.products;

import com.rm.model.products.Category;
import com.rm.model.products.IngredientDefinition;
import com.rm.model.products.ProductDefinition;

/**
 * 
 * @author Nabil
 * 
 */
public class ProductDataFactory {
	public static ProductDefinition initSimpleProduct() {
		ProductDefinition product = new ProductDefinition();
		product.getContent().addName("fr", "cat");
		product.getContent().addDescription("fr", "cat Descprition");
		product.getImage().setImage("test/img.png");
		product.getImage().addImages("img1");
		product.getImage().addImages("img2");
		return product;
	}

	public static ProductDefinition initComposedProduct() {
		ProductDefinition product = new ProductDefinition();
		product.getContent().addName("fr", "cat");
		product.getContent().addDescription("fr", "cat Descprition");
		product.getImage().setImage("test/img.png");
		product.getImage().addImages("img1");
		product.getImage().addImages("img2");
		return product;
	}

	public static IngredientDefinition initIngredient() {
		IngredientDefinition ingredient = new IngredientDefinition();
		ingredient.getContent().addName("fr", "cat");
		ingredient.getContent().addDescription("fr", "cat Descprition");
		ingredient.getImage().setImage("test/img.png");
		return ingredient;
	}

	public static Category initCategory() {
		Category category = new Category();
		category.getContent().addName("fr", "cat");
		category.getContent().addDescription("fr", "cat Descprition");
		category.getImage().setImage("test/img.png");
		return category;
	}
}
