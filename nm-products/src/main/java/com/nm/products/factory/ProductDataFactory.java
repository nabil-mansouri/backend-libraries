package com.nm.products.factory;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.util.Assert;

import com.nm.app.currency.CurrencyDto;
import com.nm.app.currency.SoaDevise;
import com.nm.app.locale.LocaleFormDto;
import com.nm.app.locale.SoaLocale;
import com.nm.cms.dtos.CmsDtoContentsTextForm;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.products.dao.DaoCategory;
import com.nm.products.dao.DaoIngredient;
import com.nm.products.dao.DaoProductDefinition;
import com.nm.products.dao.impl.CategoryQueryBuilder;
import com.nm.products.dao.impl.IngredientQueryBuilder;
import com.nm.products.dtos.forms.CategoryFormDto;
import com.nm.products.dtos.forms.IngredientFormDto;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.forms.ProductPartFormDto;
import com.nm.products.dtos.options.ProductOptions;
import com.nm.products.dtos.views.CategoryViewDto;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.model.ProductDefinition;
import com.nm.products.soa.SoaProductDefinition;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author nabilmansouri
 *
 */
public class ProductDataFactory {
	private SoaProductDefinition soaProductDefinition;
	private DaoProductDefinition daoProductDefinition;
	private DaoCategory daoCategory;
	private DaoIngredient daoIngredient;
	private SoaLocale soaLocale;
	private SoaDevise soaDevise;

	//
	public void setDaoCategory(DaoCategory daoCategory) {
		this.daoCategory = daoCategory;
	}

	public void setDaoIngredient(DaoIngredient daoIngredient) {
		this.daoIngredient = daoIngredient;
	}

	public void setDaoProductDefinition(DaoProductDefinition daoProductDefinition) {
		this.daoProductDefinition = daoProductDefinition;
	}

	public void setSoaDevise(SoaDevise soaDevise) {
		this.soaDevise = soaDevise;
	}

	public void setSoaLocale(SoaLocale soaLocale) {
		this.soaLocale = soaLocale;
	}

	public void setSoaProductDefinition(SoaProductDefinition soaProductDefinition) {
		this.soaProductDefinition = soaProductDefinition;
	}

	public IngredientFormDto createIngredien() {
		IngredientFormDto form = soaProductDefinition.createIngredient();
		for (CmsDtoContentsTextForm content : form.getCms().getContents()) {
			content.setName("ING");
			content.setDescription("DESC");
			content.addKeyword("KEY1").addKeyword("KEY2");
		}
		form.getCms().setImg(new AppDataDtoImpl(""));
		return form;
	}

	public ProductFormDto createProduct() {
		ProductFormDto form = soaProductDefinition.createProduct();
		for (CmsDtoContentsTextForm content : form.getCms().getContents()) {
			content.setName("PRODUCT");
			content.setDescription("DESC");
			content.addKeyword("KEY1").addKeyword("KEY2");
		}
		form.getCms().setImg(new AppDataDtoImpl(""));
		return form;
	}

	public ProductFormDto createProduct(int nbParts) {
		ProductFormDto form = soaProductDefinition.createProduct();
		form.getParts().clear();
		if (nbParts > 0) {
			form.setHasProducts(true);
		}
		for (CmsDtoContentsTextForm content : form.getCms().getContents()) {
			content.setName("PRODUCT");
			content.setDescription("DESC");
			content.addKeyword("KEY1").addKeyword("KEY2");
		}
		form.getCms().setImg(new AppDataDtoImpl(""));
		for (int i = 0; i < nbParts; i++) {
			ProductPartFormDto p = new ProductPartFormDto();
			p.setName("PART" + i);
			form.getParts().add(p);
		}
		return form;
	}

	public CategoryFormDto createCategory() {
		return createCategory(null);
	}

	public CategoryFormDto createCategory(Long parent) {
		CategoryFormDto form = soaProductDefinition.createCategory();
		if (parent != null) {
			form = soaProductDefinition.createCategory(parent);
		}
		for (CmsDtoContentsTextForm content : form.getCms().getContents()) {
			content.setName("CAT");
			content.setDescription("DESC");
			content.addKeyword("KEY1").addKeyword("KEY2");
		}
		form.getCms().setImg(new AppDataDtoImpl(""));
		return form;
	}

	public void loadConfig(ScenarioContext context) {
		soaLocale
				.setSelectedLocales(Arrays.asList(new LocaleFormDto("fr").setName("France").setNativeName("Français")));
		soaLocale.setDefaultLocale(new LocaleFormDto("fr"));
		soaDevise.setDefault(new CurrencyDto("EUR").setSymbol("€"));
		daoCategory.flush();
	}

	public void loadProducts(ScenarioContext context) throws Exception {
		{
			ProductFormDto sauce1 = soaProductDefinition.createProduct();
			sauce1.select(context.getAllCategoriesView().get(ScenarioContext.SAUCE))
					.setImg(new AppDataDtoImpl("img/sauce1.png"));
			sauce1.getCms().fetchByLang("fr").setDescription("Sauce blanche").addKeyword("sauce").setLang("fr")
					.setName("Sauce blanche");
			soaProductDefinition.saveOrUpdate(sauce1, new OptionsList("fr"));
			Assert.notNull(sauce1.getId());

			context.getAllProductsForm().put(ScenarioContext.SAUCE_BLANCHE, sauce1);
		}
		{
			ProductFormDto sauce2 = soaProductDefinition.createProduct();
			sauce2.select(context.getAllCategoriesView().get(ScenarioContext.SAUCE))
					.setImg(new AppDataDtoImpl("img/sauce2.png"));
			sauce2.getCms().fetchByLang("fr").setDescription("Sauce curry").setName("Sauce curry").addKeyword("sauce");
			soaProductDefinition.saveOrUpdate(sauce2, new OptionsList("fr"));
			Assert.notNull(sauce2.getId());
			context.getAllProductsForm().put(ScenarioContext.SAUCE_CURRY, sauce2);
		}
		{
			ProductFormDto kebab = soaProductDefinition.createProduct();
			kebab.select(context.getAllCategoriesView().get(ScenarioContext.SANDWICH))
					.setImg(new AppDataDtoImpl("img/kebab.png"));
			kebab.getCms().fetchByLang("fr").setDescription("Kebab en galette").addKeyword("kebab").setName("Kebab");
			kebab.select(context.getAllIngredientsView().get(ScenarioContext.TOMATE))
					.select(context.getAllIngredientsView().get(ScenarioContext.OIGNON));
			soaProductDefinition.saveOrUpdate(kebab, new OptionsList("fr"));
			Assert.notNull(kebab.getId());
			context.getAllProductsForm().put(ScenarioContext.KEBAB, kebab);
		}
		{
			ProductFormDto frites = soaProductDefinition.createProduct();
			frites.select(context.getAllCategoriesView().get(ScenarioContext.ACCOMPAGNEMENT))
					.setImg(new AppDataDtoImpl("img/frites.png"));
			frites.getCms().fetchByLang("fr").setDescription("Frites épicé").addKeyword("frite").setName("Frites");
			soaProductDefinition.saveOrUpdate(frites, new OptionsList("fr"));
			Assert.notNull(frites.getId());
			context.getAllProductsForm().put(ScenarioContext.FRITES, frites);
		}
		{
			ProductFormDto potatoes = soaProductDefinition.createProduct();
			potatoes.select(context.getAllCategoriesView().get(ScenarioContext.ACCOMPAGNEMENT))
					.setImg(new AppDataDtoImpl("img/potatoes.png"));
			potatoes.getCms().fetchByLang("fr").setDescription("Potatoes épicé").addKeyword("potatoe")
					.setName("Potatoes");
			soaProductDefinition.saveOrUpdate(potatoes, new OptionsList("fr"));
			Assert.notNull(potatoes.getId());
			context.getAllProductsForm().put(ScenarioContext.POTATOES, potatoes);
		}
		{
			ProductFormDto chocolat = soaProductDefinition.createProduct();
			chocolat.select(context.getAllCategoriesView().get(ScenarioContext.DESSERT))
					.setImg(new AppDataDtoImpl("img/chocolat.png"));
			chocolat.getCms().fetchByLang("fr").setDescription("Chocolat fondant").addKeyword("chocolat")
					.setName("Chocolat fondant");
			soaProductDefinition.saveOrUpdate(chocolat, new OptionsList("fr"));
			Assert.notNull(chocolat.getId());
			context.getAllProductsForm().put(ScenarioContext.CHOCOLAT, chocolat);
		}
		{
			ProductFormDto yaourt = soaProductDefinition.createProduct();
			yaourt.select(context.getAllCategoriesView().get(ScenarioContext.DESSERT))
					.setImg(new AppDataDtoImpl("img/yaourt.png"));
			yaourt.getCms().fetchByLang("fr").setDescription("Yaourt danone").addKeyword("yaourt")
					.setName("Yaourt Vanille");
			soaProductDefinition.saveOrUpdate(yaourt, new OptionsList("fr"));
			Assert.notNull(yaourt.getId());
			context.getAllProductsForm().put(ScenarioContext.YAOURT, yaourt);
		}
		doProducts(context);
	}

	public void loadProductsComposed(ScenarioContext context) throws Exception {
		{
			ProductFormDto tacos = soaProductDefinition.createProduct();
			tacos.select(context.getAllCategoriesView().get(ScenarioContext.SANDWICH));
			tacos.select(context.getAllIngredientsView().get(ScenarioContext.TOMATE), true)
					.select(context.getAllIngredientsView().get(ScenarioContext.OIGNON));
			tacos.clearPart().pushPart().setFacultatif(1).setName("Sauce n°1")
					.select(context.getAllProductsView().get(ScenarioContext.SAUCE_BLANCHE))
					.select(context.getAllProductsView().get(ScenarioContext.SAUCE_CURRY));
			tacos.pushPart().setFacultatif(1).setName("Sauce n°2")
					.select(context.getAllProductsView().get(ScenarioContext.SAUCE_BLANCHE))
					.select(context.getAllProductsView().get(ScenarioContext.SAUCE_CURRY));
			tacos.setImg(new AppDataDtoImpl("img/tacos.png")).getCms().fetchByLang("fr")
					.setDescription("Tacos en galette").addKeyword("tacos").setName("Tacos");
			soaProductDefinition.saveOrUpdate(tacos, new OptionsList("fr"));
			Assert.notNull(tacos.getId());
			context.getAllProductsForm().put(ScenarioContext.TACOS, tacos);
			daoProductDefinition.flush();
		}
		doProducts(context);
		{
			ProductFormDto kebab = soaProductDefinition.createProduct();
			kebab.select(context.getAllCategoriesView().get(ScenarioContext.MENU))
					.setImg(new AppDataDtoImpl("img/menus.png"));
			kebab.clearPart().pushPart().setFacultatif(0).setName("Sandwich")
					.select(context.getAllProductsView().get(ScenarioContext.KEBAB))
					.select(context.getAllProductsView().get(ScenarioContext.TACOS));
			kebab.pushPart().setFacultatif(1).setName("Accompagnement")
					.select(context.getAllProductsView().get(ScenarioContext.POTATOES))
					.select(context.getAllProductsView().get(ScenarioContext.FRITES));
			kebab.pushPart().setFacultatif(1).setName(ScenarioContext.Dessert)
					.select(context.getAllProductsView().get(ScenarioContext.YAOURT))
					.select(context.getAllProductsView().get(ScenarioContext.CHOCOLAT));
			kebab.getCms().fetchByLang("fr").setDescription("Menus avec sandwich, accompagnement et dessert")
					.addKeyword("menu tacos").setName("Menu moyen");
			soaProductDefinition.saveOrUpdate(kebab, new OptionsList("fr"));
			context.getAllProductsForm().put(ScenarioContext.MENU_MOYEN, kebab);
			daoProductDefinition.flush();
		}
		doProducts(context);
		{
			ProductFormDto tacos = soaProductDefinition.createProduct();
			tacos.select(context.getAllCategoriesView().get(ScenarioContext.MENU))
					.setImg(new AppDataDtoImpl("img/menus1.png"));
			tacos.clearPart().pushPart().setFacultatif(0).setName("Sandwich")
					.select(context.getAllProductsView().get(ScenarioContext.KEBAB))
					.select(context.getAllProductsView().get(ScenarioContext.TACOS));
			tacos.pushPart().setFacultatif(0).setName("Accompagnement")
					.select(context.getAllProductsView().get(ScenarioContext.POTATOES))
					.select(context.getAllProductsView().get(ScenarioContext.FRITES));
			tacos.getCms().fetchByLang("fr").setDescription("Menu pour étudiants").addKeyword("etudiant")
					.setName("Menu étudiant");
			soaProductDefinition.saveOrUpdate(tacos, new OptionsList("fr"));
			context.getAllProductsForm().put(ScenarioContext.MENUS_ETUDIANTS, tacos);
			daoProductDefinition.flush();
		}
		doProducts(context);
	}

	public void doProducts(ScenarioContext context) throws Exception {
		daoProductDefinition.flush();
		for (String key : context.getAllProductsForm().keySet()) {
			Assert.notNull(context.getAllProductsForm().get(key).getId());
			//
			OptionsList options = new OptionsList("fr");
			options.add(ProductOptions.Parts);
			options.add(ProductOptions.Ingredients);
			try {
				ProductViewDto def = soaProductDefinition.fetch(context.getAllProductsForm().get(key).getId(), options);
				context.getAllProductsView().put(key, def);
				context.getAllProducts().put(key, daoProductDefinition.get(def.getId()));
			} catch (NoSuchElementException e) {
				Collection<ProductDefinition> c = daoProductDefinition.findAll();
				for (ProductDefinition p : c) {
					System.out.println(p);
				}
				System.out.println(context.getAllProductsForm().get(key).getId());
			}
		}
	}

	public void loadCategories(ScenarioContext context) throws Exception {
		{
			CategoryFormDto cat = soaProductDefinition.createCategory().setImg(new AppDataDtoImpl("img/sandwich.png"));
			cat.getCms().fetchByLang("fr")//
					.setDescription("Tout nos sandwichs")//
					.setName("Sandwich").addKeyword("sandwich");
			soaProductDefinition.saveOrUpdate(cat, new OptionsList());

			context.getAllCategoriesForm().put(ScenarioContext.SANDWICH, cat);
		}
		{
			CategoryFormDto cat = soaProductDefinition.createCategory().setImg(new AppDataDtoImpl("img/boisson.png"));
			cat.getCms().fetchByLang("fr")//
					.setDescription("Toutes nos boissons")//
					.setName("Boisson").addKeyword("boissons");
			soaProductDefinition.saveOrUpdate(cat, new OptionsList());
			context.getAllCategoriesForm().put(ScenarioContext.BOISSON, cat);
		}
		{
			CategoryFormDto cat = soaProductDefinition.createCategory()
					.setImg(new AppDataDtoImpl("img/accompagnement.png"));
			cat.getCms().fetchByLang("fr")//
					.setDescription("Tout nos accompagnement")//
					.setName("Accompagnement").addKeyword("accompagnement");
			soaProductDefinition.saveOrUpdate(cat, new OptionsList());
			context.getAllCategoriesForm().put(ScenarioContext.ACCOMPAGNEMENT, cat);
		}
		{
			CategoryFormDto cat = soaProductDefinition.createCategory().setImg(new AppDataDtoImpl("img/dessert.png"));
			cat.getCms().fetchByLang("fr")//
					.setDescription("Tout nos desserts")//
					.setName(ScenarioContext.Dessert).addKeyword(ScenarioContext.Dessert);
			soaProductDefinition.saveOrUpdate(cat, new OptionsList());
			context.getAllCategoriesForm().put(ScenarioContext.DESSERT, cat);
		}
		{
			CategoryFormDto cat = soaProductDefinition.createCategory().setImg(new AppDataDtoImpl("img/sauce.png"));
			cat.getCms().fetchByLang("fr")//
					.setDescription("Toutes nos sauces")//
					.setName("Sauce").addKeyword("sauce");
			soaProductDefinition.saveOrUpdate(cat, new OptionsList());
			context.getAllCategoriesForm().put(ScenarioContext.SAUCE, cat);
		}
		{
			CategoryFormDto cat = soaProductDefinition.createCategory().setImg(new AppDataDtoImpl("img/menu.png"));
			cat.getCms().fetchByLang("fr")//
					.setDescription("Tout nos menus")//
					.setName("Menu").addKeyword("menu");
			soaProductDefinition.saveOrUpdate(cat, new OptionsList());
			context.getAllCategoriesForm().put(ScenarioContext.SAUCE, cat);
		}
		{
			//
			CategoryFormDto cat = new CategoryFormDto().setImg(new AppDataDtoImpl(""))
					.add(new CmsDtoContentsTextForm().setDescription("").addKeyword("").setLang("fr").setName(""));
			soaProductDefinition.saveOrUpdate(cat, new OptionsList("fr"));
			Assert.notNull(cat.getId());
			context.getAllCategoriesForm().put(ScenarioContext.MENU, cat);
		}
		daoCategory.flush();

		for (String key : context.getAllCategoriesForm().keySet()) {
			Assert.notNull(context.getAllCategoriesForm().get(key).getId());
			CategoryQueryBuilder query = CategoryQueryBuilder.get()
					.withId(context.getAllCategoriesForm().get(key).getId());
			CategoryViewDto def = soaProductDefinition.fetch(query, new OptionsList("fr")).iterator().next();

			context.getAllCategoriesView().put(key, def);
			context.getAllCategories().put(key, daoCategory.get(def.getId()));
		}
	}

	public void loadIngredients(ScenarioContext context) throws Exception {
		{
			IngredientFormDto oignon = new IngredientFormDto().setImg(new AppDataDtoImpl("img/oignon.png"))
					.add(new CmsDtoContentsTextForm().setDescription("Oignon frit").addKeyword("oignon").setLang("fr")
							.setName("Oignon"));
			soaProductDefinition.saveOrUpdate(oignon, new OptionsList("fr"));
			context.getAllIngredientsForm().put(ScenarioContext.OIGNON, oignon);
		}
		{
			//
			IngredientFormDto tomate = new IngredientFormDto().setImg(new AppDataDtoImpl("img/tomate.png"))
					.add(new CmsDtoContentsTextForm().setDescription("Tomate fraiches").addKeyword("tomate")
							.setLang("fr").setName("Tomate"));
			soaProductDefinition.saveOrUpdate(tomate, new OptionsList("fr"));
			context.getAllIngredientsForm().put(ScenarioContext.TOMATE, tomate);
		}
		{
			IngredientFormDto salade = new IngredientFormDto().setImg(new AppDataDtoImpl("img/salade.png"))
					.add(new CmsDtoContentsTextForm().setDescription("Salade fraiches").addKeyword("salade")
							.setLang("fr").setName("Salade"));
			soaProductDefinition.saveOrUpdate(salade, new OptionsList("fr"));
			Assert.notNull(salade.getId());
			context.getAllIngredientsForm().put(ScenarioContext.SALADE, salade);
		}
		daoIngredient.flush();
		for (String key : context.getAllIngredientsForm().keySet()) {
			Assert.notNull(context.getAllIngredientsForm().get(key).getId());
			IngredientQueryBuilder query = IngredientQueryBuilder.get()
					.withId(context.getAllIngredientsForm().get(key).getId());
			IngredientViewDto def = soaProductDefinition.fetch(query, new OptionsList("fr")).iterator().next();
			context.getAllIngredientsView().put(key, def);
			context.getAllIngredients().put(key, daoIngredient.get(def.getId()));
		}
	}

	public ScenarioContext testCreateSafe() throws Exception {
		ScenarioContext context = new ScenarioContext();
		this.loadConfig(context);
		this.loadCategories(context);
		this.loadIngredients(context);
		this.loadProducts(context);
		this.loadProductsComposed(context);
		daoProductDefinition.flush();
		return context;
	}

}
