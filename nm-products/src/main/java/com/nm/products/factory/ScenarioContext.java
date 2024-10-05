package com.nm.products.factory;

import java.util.HashMap;
import java.util.Map;

import com.nm.products.dtos.forms.CategoryFormDto;
import com.nm.products.dtos.forms.IngredientFormDto;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.views.CategoryViewDto;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.model.Category;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;

/**
 * 
 * @author nabilmansouri
 *
 */
public class ScenarioContext {
	private Map<String, CategoryFormDto> allCategoriesForm = new HashMap<String, CategoryFormDto>();
	private Map<String, CategoryViewDto> allCategoriesView = new HashMap<String, CategoryViewDto>();
	private Map<String, Category> allCategories = new HashMap<String, Category>();
	//
	private Map<String, IngredientFormDto> allIngredientsForm = new HashMap<String, IngredientFormDto>();
	private Map<String, IngredientViewDto> allIngredientsView = new HashMap<String, IngredientViewDto>();
	private Map<String, IngredientDefinition> allIngredients = new HashMap<String, IngredientDefinition>();
	//
	private Map<String, ProductViewDto> allProductsView = new HashMap<String, ProductViewDto>();
	private Map<String, ProductFormDto> allProductsForm = new HashMap<String, ProductFormDto>();
	private Map<String, ProductDefinition> allProducts = new HashMap<String, ProductDefinition>();
	// Categories
	public static final String SANDWICH = "SANDWICH";
	public static final String SAUCE = "SAUCE";
	public static final String MENU = "MENU";
	public static final String DESSERT = "DESSERT";
	public static final String ACCOMPAGNEMENT = "ACCOMPAGNEMENT";
	public static final String BOISSON = "BOISSON";
	// Produits
	public static final String POTATOES = "POTATOES";
	public static final String MENU_MOYEN = "MENU_MOYEN";
	public static final String MENUS_ETUDIANTS = "MENUS_ETUDIANTS";
	public static final String CHOCOLAT = "CHOCOLAT";
	public static final String TACOS = "TACOS";
	public static final String SAUCE_BLANCHE = "SAUCE_BLANCHE";
	public static final String SAUCE_CURRY = "SAUCE_CURRY";
	public static final String KEBAB = "KEBAB";
	public static final String FRITES = "FRITES";
	public static final String YAOURT = "YAOURT";
	// Ingredients
	public static final String SALADE = "SALADE";
	public static final String OIGNON = "OIGNON";
	public static final String TOMATE = "TOMATE";
	// Part Name
	public static final String Dessert = "Dessert";

	public Map<String, CategoryFormDto> getAllCategoriesForm() {
		return allCategoriesForm;
	}

	public Map<String, CategoryViewDto> getAllCategoriesView() {
		return allCategoriesView;
	}

	public Map<String, Category> getAllCategories() {
		return allCategories;
	}

	public Map<String, IngredientFormDto> getAllIngredientsForm() {
		return allIngredientsForm;
	}

	public Map<String, IngredientViewDto> getAllIngredientsView() {
		return allIngredientsView;
	}

	public Map<String, IngredientDefinition> getAllIngredients() {
		return allIngredients;
	}

	public Map<String, ProductViewDto> getAllProductsView() {
		return allProductsView;
	}

	public Map<String, ProductFormDto> getAllProductsForm() {
		return allProductsForm;
	}

	public Map<String, ProductDefinition> getAllProducts() {
		return allProducts;
	}
}
