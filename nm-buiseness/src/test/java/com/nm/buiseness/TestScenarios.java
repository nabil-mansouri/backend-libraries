package com.nm.buiseness;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.app.currency.CurrencyDto;
import com.nm.app.currency.SoaDevise;
import com.nm.app.locale.LocaleFormDto;
import com.nm.app.locale.SoaLocale;
import com.nm.bridges.SoaBridgeConfig;
import com.nm.bridges.prices.OrderType;
import com.nm.bridges.prices.dtos.CustomPriceFormDto;
import com.nm.bridges.prices.dtos.CustomPriceFormFilterDto;
import com.nm.bridges.prices.dtos.PriceFormNodeDto;
import com.nm.bridges.prices.dtos.PriceFormNodeFilterDto;
import com.nm.cms.dtos.CmsDtoContentsTextForm;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.prices.dtos.constants.PriceFormOptions;
import com.nm.prices.soa.SoaPrice;
import com.nm.products.dao.DaoCategory;
import com.nm.products.dao.DaoIngredient;
import com.nm.products.dao.DaoProductDefinition;
import com.nm.products.dao.impl.CategoryQueryBuilder;
import com.nm.products.dao.impl.IngredientQueryBuilder;
import com.nm.products.dtos.forms.CategoryFormDto;
import com.nm.products.dtos.forms.IngredientFormDto;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.options.ProductOptions;
import com.nm.products.dtos.views.CategoryViewDto;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.model.Category;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.products.soa.SoaProductDefinition;
import com.nm.shop.dao.DaoShop;
import com.nm.shop.dao.impl.ShopQueryBuilder;
import com.nm.shop.dtos.ShopFormDto;
import com.nm.shop.dtos.ShopViewDto;
import com.nm.shop.model.Shop;
import com.nm.shop.soa.SoaShop;
import com.nm.tests.utils.DataFactory;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH_TEST })
@Component
public class TestScenarios {

	@Autowired
	private SoaProductDefinition soaProductDefinition;
	@Autowired
	protected SoaLocale soaLocale;
	@Autowired
	private SoaDevise soaDevise;
	@Autowired
	private SoaShop soaShop;
	@Autowired
	private DaoCategory daoCategory;
	@Autowired
	private DaoIngredient daoIngredient;
	@Autowired
	private SoaBridgeConfig soaOrderType;
	@Autowired
	private DaoShop daoShop;
	@Autowired
	private SoaPrice soaPrice;
	@Autowired
	private DaoProductDefinition daoProductDefinition;
	@Autowired
	private DataFactory dataFactory;
	//
	private Map<String, CategoryFormDto> allCategoriesForm = new HashMap<String, CategoryFormDto>();
	private Map<String, CategoryViewDto> allCategoriesView = new HashMap<String, CategoryViewDto>();
	private Map<String, Category> allCategories = new HashMap<String, Category>();
	//
	private Map<String, IngredientFormDto> allIngredientsForm = new HashMap<String, IngredientFormDto>();
	private Map<String, IngredientViewDto> allIngredientsView = new HashMap<String, IngredientViewDto>();
	private Map<String, IngredientDefinition> allIngredients = new HashMap<String, IngredientDefinition>();
	//
	private Map<String, ShopFormDto> allShopsForm = new HashMap<String, ShopFormDto>();
	private Map<String, ShopViewDto> allShopsView = new HashMap<String, ShopViewDto>();
	private Map<String, Shop> allShops = new HashMap<String, Shop>();
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
	// Shops
	public static final String RESTO1 = "RESTO1";
	public static final String RESTO2 = "RESTO2";
	// Part Name
	public static final String Dessert = "Dessert";

	private void loadConfig() {
		soaOrderType.setSelectedOrderTypes(Arrays.asList(OrderType.values()));
		soaLocale
				.setSelectedLocales(Arrays.asList(new LocaleFormDto("fr").setName("France").setNativeName("Français")));
		soaLocale.setDefaultLocale(new LocaleFormDto("fr"));
		soaDevise.setDefault(new CurrencyDto("EUR").setSymbol("€"));
	}

	private void loadCategories() throws Exception {
		{
			CategoryFormDto cat = soaProductDefinition.createCategory().setImg(new AppDataDtoImpl("img/sandwich.png"));
			cat.getCms().fetchByLang("fr")//
					.setDescription("Tout nos sandwichs")//
					.setName("Sandwich").addKeyword("sandwich");
			soaProductDefinition.saveOrUpdate(cat, new OptionsList());
			allCategoriesForm.put(SANDWICH, cat);
		}
		{
			CategoryFormDto cat = soaProductDefinition.createCategory().setImg(new AppDataDtoImpl("img/boisson.png"));
			cat.getCms().fetchByLang("fr")//
					.setDescription("Toutes nos boissons")//
					.setName("Boisson").addKeyword("boissons");
			soaProductDefinition.saveOrUpdate(cat, new OptionsList());
			allCategoriesForm.put(BOISSON, cat);
		}
		{
			CategoryFormDto cat = soaProductDefinition.createCategory()
					.setImg(new AppDataDtoImpl("img/accompagnement.png"));
			cat.getCms().fetchByLang("fr")//
					.setDescription("Tout nos accompagnement")//
					.setName("Accompagnement").addKeyword("accompagnement");
			soaProductDefinition.saveOrUpdate(cat, new OptionsList());
			allCategoriesForm.put(ACCOMPAGNEMENT, cat);
		}
		{
			CategoryFormDto cat = soaProductDefinition.createCategory().setImg(new AppDataDtoImpl("img/dessert.png"));
			cat.getCms().fetchByLang("fr")//
					.setDescription("Tout nos desserts")//
					.setName(Dessert).addKeyword(Dessert);
			soaProductDefinition.saveOrUpdate(cat, new OptionsList());
			allCategoriesForm.put(DESSERT, cat);
		}
		{
			CategoryFormDto cat = soaProductDefinition.createCategory().setImg(new AppDataDtoImpl("img/sauce.png"));
			cat.getCms().fetchByLang("fr")//
					.setDescription("Toutes nos sauces")//
					.setName("Sauce").addKeyword("sauce");
			soaProductDefinition.saveOrUpdate(cat, new OptionsList());
			allCategoriesForm.put(SAUCE, cat);
		}
		{
			CategoryFormDto cat = soaProductDefinition.createCategory().setImg(new AppDataDtoImpl("img/menu.png"));
			cat.getCms().fetchByLang("fr")//
					.setDescription("Tout nos menus")//
					.setName("Menu").addKeyword("menu");
			soaProductDefinition.saveOrUpdate(cat, new OptionsList());
			allCategoriesForm.put(SAUCE, cat);
		}
		{
			//
			CategoryFormDto cat = new CategoryFormDto().setImg(new AppDataDtoImpl(""))
					.add(new CmsDtoContentsTextForm().setDescription("").addKeyword("").setLang("fr").setName(""));
			soaProductDefinition.saveOrUpdate(cat, new OptionsList("fr"));
			assertNotNull(cat.getId());
			allCategoriesForm.put(MENU, cat);
		}
		daoCategory.flush();
		daoCategory.clear();
		for (String key : allCategoriesForm.keySet()) {
			Assert.assertNotNull(allCategoriesForm.get(key).getId());
			CategoryQueryBuilder query = CategoryQueryBuilder.get().withId(allCategoriesForm.get(key).getId());
			CategoryViewDto def = soaProductDefinition.fetch(query, new OptionsList("fr")).iterator().next();
			allCategoriesView.put(key, def);
			allCategories.put(key, daoCategory.get(def.getId()));
		}
	}

	private void loadIngredients() throws Exception {
		{
			IngredientFormDto oignon = new IngredientFormDto().setImg(new AppDataDtoImpl("img/oignon.png"))
					.add(new CmsDtoContentsTextForm().setDescription("Oignon frit").addKeyword("oignon").setLang("fr")
							.setName("Oignon"));
			soaProductDefinition.saveOrUpdate(oignon, new OptionsList("fr"));
			allIngredientsForm.put(OIGNON, oignon);
		}
		{
			//
			IngredientFormDto tomate = new IngredientFormDto().setImg(new AppDataDtoImpl("img/tomate.png"))
					.add(new CmsDtoContentsTextForm().setDescription("Tomate fraiches").addKeyword("tomate")
							.setLang("fr").setName("Tomate"));
			soaProductDefinition.saveOrUpdate(tomate, new OptionsList("fr"));
			allIngredientsForm.put(TOMATE, tomate);
		}
		{
			IngredientFormDto salade = new IngredientFormDto().setImg(new AppDataDtoImpl("img/salade.png"))
					.add(new CmsDtoContentsTextForm().setDescription("Salade fraiches").addKeyword("salade")
							.setLang("fr").setName("Salade"));
			soaProductDefinition.saveOrUpdate(salade, new OptionsList("fr"));
			assertNotNull(salade.getId());
			allIngredientsForm.put(SALADE, salade);
		}
		daoIngredient.flush();
		daoIngredient.clear();
		for (String key : allIngredientsForm.keySet()) {
			Assert.assertNotNull(allIngredientsForm.get(key).getId());
			IngredientQueryBuilder query = IngredientQueryBuilder.get().withId(allIngredientsForm.get(key).getId());
			IngredientViewDto def = soaProductDefinition.fetch(query, new OptionsList("fr")).iterator().next();
			allIngredientsView.put(key, def);
			allIngredients.put(key, daoIngredient.get(def.getId()));
		}
	}

	private void loadShops() throws Exception {
		{
			ShopFormDto prod = soaShop.createShop();
			prod.getCms().setImg(new AppDataDtoImpl("img/resto1.png"));
			prod.getCms().fetchByLang("fr").setDescription("Shop du parc")//
					.addKeyword("resto du parc").setName("Kebab du parc");
			dataFactory.createAdress(prod.getAddress());
			ShopFormDto result = soaShop.saveOrUpdate(prod, new OptionsList("fr"));
			assertNotNull(result.getId());
			allShopsForm.put(RESTO1, result);

		}
		{
			ShopFormDto prod = soaShop.createShop();
			prod.getCms().setImg(new AppDataDtoImpl("img/resto1.png"));
			prod.getCms().fetchByLang("fr").setDescription("Shop de torcy")//
					.addKeyword("resto torcy").setName("Kebab de torcy");
			dataFactory.createAdress(prod.getAddress());
			ShopFormDto result = soaShop.saveOrUpdate(prod, new OptionsList("fr"));
			assertNotNull(result.getId());
			allShopsForm.put(RESTO2, result);
		}
		daoShop.flush();
		daoShop.clear();
		for (String key : allShopsForm.keySet()) {
			Assert.assertNotNull(allShopsForm.get(key).getId());
			ShopQueryBuilder query = ShopQueryBuilder.get().withId(allShopsForm.get(key).getId());
			ShopViewDto def = soaShop.fetch(query, new OptionsList("fr")).iterator().next();
			allShopsView.put(key, def);
			allShops.put(key, daoShop.get(def.getId()));
		}
	}

	private void loadProducts() throws Exception {
		{
			ProductFormDto sauce1 = soaProductDefinition.createProduct();
			sauce1.select(allCategoriesView.get(SAUCE)).setImg(new AppDataDtoImpl("img/sauce1.png"));
			sauce1.getCms().fetchByLang("fr").setDescription("Sauce blanche").addKeyword("sauce").setLang("fr")
					.setName("Sauce blanche");
			soaProductDefinition.saveOrUpdate(sauce1, new OptionsList("fr"));
			assertNotNull(sauce1.getId());
			allProductsForm.put(SAUCE_BLANCHE, sauce1);
		}
		{
			ProductFormDto sauce2 = soaProductDefinition.createProduct();
			sauce2.select(allCategoriesView.get(SAUCE)).setImg(new AppDataDtoImpl("img/sauce2.png"));
			sauce2.getCms().fetchByLang("fr").setDescription("Sauce curry").setName("Sauce curry").addKeyword("sauce");
			soaProductDefinition.saveOrUpdate(sauce2, new OptionsList("fr"));
			assertNotNull(sauce2.getId());
			allProductsForm.put(SAUCE_CURRY, sauce2);
		}
		{
			ProductFormDto kebab = soaProductDefinition.createProduct();
			kebab.select(allCategoriesView.get(SANDWICH)).setImg(new AppDataDtoImpl("img/kebab.png"));
			kebab.getCms().fetchByLang("fr").setDescription("Kebab en galette").addKeyword("kebab").setName("Kebab");
			kebab.select(allIngredientsView.get(TOMATE)).select(allIngredientsView.get(OIGNON));
			soaProductDefinition.saveOrUpdate(kebab, new OptionsList("fr"));
			assertNotNull(kebab.getId());
			allProductsForm.put(KEBAB, kebab);
		}
		{
			ProductFormDto frites = soaProductDefinition.createProduct();
			frites.select(allCategoriesView.get(ACCOMPAGNEMENT)).setImg(new AppDataDtoImpl("img/frites.png"));
			frites.getCms().fetchByLang("fr").setDescription("Frites épicé").addKeyword("frite").setName("Frites");
			soaProductDefinition.saveOrUpdate(frites, new OptionsList("fr"));
			assertNotNull(frites.getId());
			allProductsForm.put(FRITES, frites);
		}
		{
			ProductFormDto potatoes = soaProductDefinition.createProduct();
			potatoes.select(allCategoriesView.get(ACCOMPAGNEMENT)).setImg(new AppDataDtoImpl("img/potatoes.png"));
			potatoes.getCms().fetchByLang("fr").setDescription("Potatoes épicé").addKeyword("potatoe")
					.setName("Potatoes");
			soaProductDefinition.saveOrUpdate(potatoes, new OptionsList("fr"));
			assertNotNull(potatoes.getId());
			allProductsForm.put(POTATOES, potatoes);
		}
		{
			ProductFormDto chocolat = soaProductDefinition.createProduct();
			chocolat.select(allCategoriesView.get(DESSERT)).setImg(new AppDataDtoImpl("img/chocolat.png"));
			chocolat.getCms().fetchByLang("fr").setDescription("Chocolat fondant").addKeyword("chocolat")
					.setName("Chocolat fondant");
			soaProductDefinition.saveOrUpdate(chocolat, new OptionsList("fr"));
			assertNotNull(chocolat.getId());
			allProductsForm.put(CHOCOLAT, chocolat);
		}
		{
			ProductFormDto yaourt = soaProductDefinition.createProduct();
			yaourt.select(allCategoriesView.get(DESSERT)).setImg(new AppDataDtoImpl("img/yaourt.png"));
			yaourt.getCms().fetchByLang("fr").setDescription("Yaourt danone").addKeyword("yaourt")
					.setName("Yaourt Vanille");
			soaProductDefinition.saveOrUpdate(yaourt, new OptionsList("fr"));
			assertNotNull(yaourt.getId());
			allProductsForm.put(YAOURT, yaourt);
		}
		doProducts();
	}

	private void loadProductsComposed() throws Exception {
		{
			ProductFormDto tacos = soaProductDefinition.createProduct();
			tacos.select(allCategoriesView.get(SANDWICH));
			tacos.select(allIngredientsView.get(TOMATE), true).select(allIngredientsView.get(OIGNON));
			tacos.clearPart().pushPart().setFacultatif(1).setName("Sauce n°1")
					.select(allProductsView.get(SAUCE_BLANCHE)).select(allProductsView.get(SAUCE_CURRY));
			tacos.pushPart().setFacultatif(1).setName("Sauce n°2").select(allProductsView.get(SAUCE_BLANCHE))
					.select(allProductsView.get(SAUCE_CURRY));
			tacos.setImg(new AppDataDtoImpl("img/tacos.png")).getCms().fetchByLang("fr")
					.setDescription("Tacos en galette").addKeyword("tacos").setName("Tacos");
			soaProductDefinition.saveOrUpdate(tacos, new OptionsList("fr"));
			assertNotNull(tacos.getId());
			allProductsForm.put(TACOS, tacos);
			daoProductDefinition.flush();
		}
		doProducts();
		{
			ProductFormDto kebab = soaProductDefinition.createProduct();
			kebab.select(allCategoriesView.get(MENU)).setImg(new AppDataDtoImpl("img/menus.png"));
			kebab.clearPart().pushPart().setFacultatif(0).setName("Sandwich").select(allProductsView.get(KEBAB))
					.select(allProductsView.get(TACOS));
			kebab.pushPart().setFacultatif(1).setName("Accompagnement").select(allProductsView.get(POTATOES))
					.select(allProductsView.get(FRITES));
			kebab.pushPart().setFacultatif(1).setName(Dessert).select(allProductsView.get(YAOURT))
					.select(allProductsView.get(CHOCOLAT));
			kebab.getCms().fetchByLang("fr").setDescription("Menus avec sandwich, accompagnement et dessert")
					.addKeyword("menu tacos").setName("Menu moyen");
			soaProductDefinition.saveOrUpdate(kebab, new OptionsList("fr"));
			allProductsForm.put(MENU_MOYEN, kebab);
			daoProductDefinition.flush();
		}
		doProducts();
		{
			ProductFormDto tacos = soaProductDefinition.createProduct();
			tacos.select(allCategoriesView.get(MENU)).setImg(new AppDataDtoImpl("img/menus1.png"));
			tacos.clearPart().pushPart().setFacultatif(0).setName("Sandwich").select(allProductsView.get(KEBAB))
					.select(allProductsView.get(TACOS));
			tacos.pushPart().setFacultatif(0).setName("Accompagnement").select(allProductsView.get(POTATOES))
					.select(allProductsView.get(FRITES));
			tacos.getCms().fetchByLang("fr").setDescription("Menu pour étudiants").addKeyword("etudiant")
					.setName("Menu étudiant");
			soaProductDefinition.saveOrUpdate(tacos, new OptionsList("fr"));
			allProductsForm.put(MENUS_ETUDIANTS, tacos);
			daoProductDefinition.flush();
		}
		doProducts();
	}

	private void doProducts() throws Exception {
		daoProductDefinition.flush();
		daoProductDefinition.clear();
		for (String key : allProductsForm.keySet()) {
			Assert.assertNotNull(allProductsForm.get(key).getId());
			//
			OptionsList options = new OptionsList("fr");
			options.add(ProductOptions.Parts);
			options.add(ProductOptions.Ingredients);
			ProductViewDto def = soaProductDefinition.fetch(allProductsForm.get(key).getId(), options);
			allProductsView.put(key, def);
			allProducts.put(key, daoProductDefinition.get(def.getId()));
		}
	}

	private void loadPrices() throws Exception {
		{
			CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
			CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
			form.select(allProductsView.get(TACOS));
			form = (CustomPriceFormDto) soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
			f = ((CustomPriceFormFilterDto) form.getFilter());
			f.setAllRestaurants(false).select(allShopsView.get(RESTO1));
			form.getFilter().setHasFrom(false).setHasTo(false);
			form.getRoot().clear().add(new PriceFormNodeFilterDto(5d));
			soaPrice.saveOrUpdate(form, new OptionsList());
			daoCategory.flush();
			assertNotNull(form.getId());
		}
		{
			CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
			CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
			form.select(allProductsView.get(KEBAB));
			form = (CustomPriceFormDto) soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
			f = ((CustomPriceFormFilterDto) form.getFilter());
			f.setAllRestaurants(false).select(allShopsView.get(RESTO1));
			form.getFilter().setHasFrom(false).setHasTo(false);
			form.getRoot().clear().add(new PriceFormNodeFilterDto(4d));
			soaPrice.saveOrUpdate(form, new OptionsList());
			daoCategory.flush();
			assertNotNull(form.getId());
		}
		{
			CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
			CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
			form.select(allProductsView.get(FRITES));
			form = (CustomPriceFormDto) soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
			f = ((CustomPriceFormFilterDto) form.getFilter());
			f.setAllRestaurants(false).select(allShopsView.get(RESTO1));
			form.getFilter().setHasFrom(false).setHasTo(false);
			form.getRoot().clear().add(new PriceFormNodeFilterDto(1.75d));
			soaPrice.saveOrUpdate(form, new OptionsList());
			daoCategory.flush();
			assertNotNull(form.getId());
		}
		{
			CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
			CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
			form.select(allProductsView.get(POTATOES));
			form = (CustomPriceFormDto) soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
			f = ((CustomPriceFormFilterDto) form.getFilter());
			f.setAllRestaurants(false).select(allShopsView.get(RESTO1));
			form.getFilter().setHasFrom(false).setHasTo(false);
			form.getRoot().clear().add(new PriceFormNodeFilterDto(1.95d));
			soaPrice.saveOrUpdate(form, new OptionsList());
			daoCategory.flush();
			assertNotNull(form.getId());
		}
		{
			CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
			CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
			form.select(allProductsView.get(YAOURT));
			form = (CustomPriceFormDto) soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
			f = ((CustomPriceFormFilterDto) form.getFilter());
			f.setAllRestaurants(false).select(allShopsView.get(RESTO1));
			form.getFilter().setHasFrom(false).setHasTo(false);
			form.getRoot().clear().add(new PriceFormNodeFilterDto(1.65d));
			soaPrice.saveOrUpdate(form, new OptionsList());
			daoCategory.flush();
			assertNotNull(form.getId());
		}
		{
			CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
			CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
			form.select(allProductsView.get(CHOCOLAT));
			form = (CustomPriceFormDto) soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
			f = ((CustomPriceFormFilterDto) form.getFilter());
			f.setAllOrders(false).setHasFrom(false).setHasTo(false);
			f.findBy(OrderType.InPlace).setSelected(true);
			form.getRoot().clear().add(new PriceFormNodeFilterDto(OrderType.InPlace, 1.95d));
			soaPrice.saveOrUpdate(form, new OptionsList());
			daoCategory.flush();
			assertNotNull(form.getId());
		}
		{
			CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
			form.select(allProductsView.get(MENU_MOYEN));
			form = (CustomPriceFormDto) soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
			CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
			f.setAllRestaurants(false).select(allShopsView.get(RESTO1));
			form.getFilter().setHasFrom(false).setHasTo(false);
			//
			form.getRoot().clear().add(new PriceFormNodeFilterDto(5d));
			int nb = 0;
			for (PriceFormNodeDto n : form.getNodes()) {
				switch (n.getNode().getType()) {
				case INGREDIENT:
					break;
				case PART:
					if (n.getNode().getPart().getName().equalsIgnoreCase(Dessert)) {
						n.setEnable(true).clear().add(new PriceFormNodeFilterDto(1.5d));
						nb++;
					}
					break;
				case PRODUCT:
					break;
				case PRODUCT_PART:
					if (n.getNode().getProduct().getId().equals(this.allProducts.get(CHOCOLAT).getId())) {
						n.setEnable(true).clear().add(new PriceFormNodeFilterDto(2.5d));
						nb++;
					}
					break;
				}
			}
			Assert.assertEquals(2, nb);
			soaPrice.saveOrUpdate(form, new OptionsList());
			assertNotNull(form.getId());
			daoCategory.flush();
		}
		{
			CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
			form.select(allProductsView.get(MENUS_ETUDIANTS));
			form = (CustomPriceFormDto) soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
			form.getFilter().setHasFrom(false).setHasTo(false);
			CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
			f.setAllOrders(false).select(OrderType.InPlace).select(OrderType.Delivered);
			//
			form.getRoot().clear().add(new PriceFormNodeFilterDto(OrderType.InPlace, 5d))
					.add(new PriceFormNodeFilterDto(OrderType.Delivered, 6d));
			soaPrice.saveOrUpdate(form, new OptionsList());
			assertNotNull(form.getId());
			daoCategory.flush();
		}
		{
			CustomPriceFormDto form = (CustomPriceFormDto) soaPrice.createPrice();
			form.select(allProductsView.get(TACOS));
			form = (CustomPriceFormDto) soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
			CustomPriceFormFilterDto f = ((CustomPriceFormFilterDto) form.getFilter());
			f.setAllRestaurants(false).select(allShopsView.get(RESTO2));
			form.getFilter().setHasFrom(false).setHasTo(false);
			form.getRoot().clear().add(new PriceFormNodeFilterDto(4d));
			soaPrice.saveOrUpdate(form, new OptionsList());
			assertNotNull(form.getId());
			daoCategory.flush();
		}
	}

	public synchronized void testCreateSafe() throws Exception {
		this.loadConfig();
		this.loadCategories();
		this.loadIngredients();
		this.loadShops();
		this.loadProducts();
		this.loadProductsComposed();
		this.loadPrices();
	}

	//
	@Test
	// @Rollback(false)
	@Transactional
	public synchronized void testCreate() throws Exception {
		this.loadConfig();
		this.loadCategories();
		this.loadIngredients();
		this.loadShops();
		this.loadProducts();
		this.loadProductsComposed();
		this.loadPrices();
	}

	public Map<String, CategoryViewDto> getAllCategories() {
		return allCategoriesView;
	}

	public Map<String, IngredientViewDto> getAllIngredients() {
		return allIngredientsView;
	}

	public Map<String, ProductDefinition> getAllProducts() {
		return allProducts;
	}

	public Map<String, ShopViewDto> getAllShops() {
		return allShopsView;
	}

	public Map<String, CategoryFormDto> getAllCategoriesForm() {
		return allCategoriesForm;
	}

	public Map<String, CategoryViewDto> getAllCategoriesView() {
		return allCategoriesView;
	}

	public Map<String, IngredientFormDto> getAllIngredientsForm() {
		return allIngredientsForm;
	}

	public Map<String, IngredientViewDto> getAllIngredientsView() {
		return allIngredientsView;
	}

	public Map<String, ShopFormDto> getAllShopsForm() {
		return allShopsForm;
	}

	public Map<String, ShopViewDto> getAllShopsView() {
		return allShopsView;
	}

	public Map<String, ProductViewDto> getAllProductsView() {
		return allProductsView;
	}

	public Map<String, ProductFormDto> getAllProductsForm() {
		return allProductsForm;
	}

}
