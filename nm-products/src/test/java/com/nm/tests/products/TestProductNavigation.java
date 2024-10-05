package com.nm.tests.products;

import java.io.Serializable;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.app.locale.SoaLocale;
import com.nm.products.constants.NavigationHeadState;
import com.nm.products.constants.NavigationState;
import com.nm.products.constants.ProductNodeType;
import com.nm.products.converter.ProductDefinitionGraphConverter;
import com.nm.products.dao.DaoIngredient;
import com.nm.products.dao.DaoProductDefinition;
import com.nm.products.dtos.navigation.NavigationBean;
import com.nm.products.dtos.navigation.NavigationException;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductAsTreeDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.factory.ProductDataFactory;
import com.nm.products.factory.ScenarioContext;
import com.nm.products.model.IngredientDefinition;
import com.nm.products.model.ProductDefinition;
import com.nm.products.navigation.ProductNavigator;
import com.nm.products.soa.SoaProductDefinition;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;
import com.nm.utils.graphs.listeners.IteratorListenerIGraph;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestProducts.class)
public class TestProductNavigation {

	@Autowired
	protected SoaProductDefinition soaProductDefinition;
	@Autowired
	protected DaoProductDefinition daoProduct;
	@Autowired
	protected DaoIngredient daoIngredient;
	@Autowired
	protected SoaLocale soaLocale;
	//
	@Autowired
	private ProductNavigator navigator;
	@Autowired
	private ProductDefinitionGraphConverter graphConverter;
	@Autowired
	private ProductDataFactory testScenarios;
	private ScenarioContext context;
	private Map<Integer, String> idsByStep = new HashMap<Integer, String>();

	//
	@Before
	public void setUp() throws Exception {
		context = testScenarios.testCreateSafe();
	}

	protected ProductViewDto find(String name, Collection<ProductViewDto> products) {
		for (ProductViewDto v : products) {
			System.out.println(v);
			if (v.equals(context.getAllProductsView().get(name))) {
				return v;
			}
		}
		return null;
	}

	protected IngredientViewDto findIng(String name, Collection<IngredientViewDto> products) {
		for (IngredientViewDto v : products) {
			System.out.println(v);
			if (v.equals(context.getAllIngredientsView().get(name))) {
				return v;
			}
		}
		return null;
	}

	protected NavigationBean menu(int step) throws Exception {
		return menu(step, 0);
	}

	protected void put(int step, NavigationBean nav) {
		if (nav.getCurrent().find(NavigationHeadState.Current).founded()) {
			idsByStep.put(step, nav.getCurrent().find(NavigationHeadState.Current).getItem().getId());
		}
	}

	protected NavigationBean menu(int step, int scenario) throws Exception {
		NavigationBean nav = null;
		// Ready on sandwich
		if (0 <= step) {
			nav = navigator.init(context.getAllProducts().get(ScenarioContext.MENU_MOYEN));
			put(0, nav);
		}
		// sleect tacos
		if (1 <= step) {
			switch (scenario) {
			case 12:
				navigator.skip(nav);
				break;
			case 14:
				navigator.push(nav, daoProduct.get(context.getAllProductsView().get(ScenarioContext.CHOCOLAT).getId()));
				break;
			default:
				ProductViewDto founded = find(ScenarioContext.TACOS, nav.getCurrent().getBody().getProducts());
				navigator.push(nav, daoProduct.get(founded.getId()));
				break;
			}
			put(1, nav);
		}
		// Skip ingredient
		if (2 <= step) {
			switch (scenario) {
			case 1:
				IngredientViewDto founded = findIng(ScenarioContext.TOMATE,
						nav.getCurrent().getBody().getIngredients());
				navigator.push(nav, daoIngredient.get(founded.getId()));
				break;
			case 13:
				navigator.push(nav,
						daoIngredient.get(context.getAllIngredientsView().get(ScenarioContext.OIGNON).getId()));
				break;
			default:
				navigator.skip(nav);
				break;
			}
			put(2, nav);
		}
		// Skip sauce 1
		if (3 <= step) {
			navigator.skip(nav);
			put(3, nav);
		}
		// Select sauce blanche
		if (4 <= step) {
			ProductViewDto founded = find(ScenarioContext.SAUCE_BLANCHE, nav.getCurrent().getBody().getProducts());
			navigator.push(nav, daoProduct.get(founded.getId()));
			put(4, nav);
		}
		// Select frite
		if (5 <= step) {
			switch (scenario) {
			case 1:
				navigator.skip(nav);
				break;
			default:
				ProductViewDto founded = find(ScenarioContext.FRITES, nav.getCurrent().getBody().getProducts());
				navigator.push(nav, daoProduct.get(founded.getId()));
				break;
			}
			put(5, nav);

		}
		// Select chocolat
		if (6 <= step) {
			ProductViewDto founded = find(ScenarioContext.CHOCOLAT, nav.getCurrent().getBody().getProducts());
			navigator.push(nav, daoProduct.get(founded.getId()));
			put(6, nav);
		}
		return nav;

	}

	protected NavigationBean kebab(int step) throws Exception {
		NavigationBean nav = null;
		// Ready on sandwich
		if (0 <= step) {
			nav = navigator.init(context.getAllProducts().get(ScenarioContext.KEBAB));
		}
		return nav;
	}

	protected NavigationBean tacos(int step) throws Exception {
		return tacos(step, 0);
	}

	protected NavigationBean tacos(int step, int scenario) throws Exception {
		NavigationBean nav = null;
		// Ready on sandwich
		if (0 <= step) {
			nav = navigator.init(context.getAllProducts().get(ScenarioContext.TACOS));
		}
		// Skip ingredient
		if (1 <= step) {
			switch (scenario) {
			case 1:
				IngredientViewDto founded = findIng(ScenarioContext.TOMATE,
						nav.getCurrent().getBody().getIngredients());
				navigator.push(nav, daoIngredient.get(founded.getId()));
				break;
			default:
				navigator.skip(nav);
				break;
			}
		}
		// Skip sauce 1
		if (2 <= step) {
			navigator.skip(nav);
		}
		// Select sauce blanche
		if (3 <= step) {
			ProductViewDto founded = find(ScenarioContext.SAUCE_BLANCHE, nav.getCurrent().getBody().getProducts());
			navigator.push(nav, daoProduct.get(founded.getId()));
		}
		return nav;
	}

	@Test
	@Transactional
	public void testShouldInitMenu() throws Exception {
		NavigationBean nav = menu(0);
		// should have 2 stack 1-3 with sandwich selected
		Assert.assertEquals(1, nav.getHistory().size());
		Assert.assertEquals(2, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(3, nav.getCurrent().find(NavigationHeadState.Current).getStack().getItems().size());
		Assert.assertEquals(1, nav.getCurrent().find(NavigationHeadState.Current).getIndexStack());
		Assert.assertEquals(0, nav.getCurrent().find(NavigationHeadState.Current).getIndexItem());
		Assert.assertEquals(NavigationHeadState.PreviousNotVisiteable,
				nav.getCurrent().getHead().getStacks().iterator().next().getItems().iterator().next().getState());
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getProducts().size());
		Assert.assertEquals(0, nav.getCurrent().getBody().getIngredients().size());
		Assert.assertTrue(nav.getCurrent().getBody().isRequired());
		ProductViewDto founded = find(ScenarioContext.TACOS, nav.getCurrent().getBody().getProducts());
		Assert.assertNotNull(founded);
	}

	@Test
	@Transactional
	public void testShouldGoToTacosOnMenu() throws Exception {
		NavigationBean nav = menu(1);
		// Should has not changed (select ingredients) then skip ingredient
		Assert.assertEquals(3, nav.getCurrent().find(NavigationHeadState.Current).getStack().getItems().size());
		Assert.assertEquals(1, nav.getCurrent().find(NavigationHeadState.Current).getIndexStack());
		Assert.assertEquals(0, nav.getCurrent().find(NavigationHeadState.Current).getIndexItem());
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getProducts().size());
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getIngredients().size());
		IngredientViewDto foundedIng = findIng(ScenarioContext.TOMATE, nav.getCurrent().getBody().getIngredients());
		Assert.assertNotNull(foundedIng);
	}

	@Test(expected = NavigationException.class)
	@Transactional
	public void testShouldThrowErrorSkipingRequired() throws Exception {
		menu(1, 12);
	}

	@Test(expected = NavigationException.class)
	@Transactional
	public void testShouldThrowErrorSelectingBadProduct() throws Exception {
		menu(1, 14);
	}

	@Test
	@Transactional
	public void testShouldGoToTacosSauce1OnMenuAfterSelectingTOmate() throws Exception {
		NavigationBean nav = menu(2, 1);
		// Should got to next (sauce1 inside tacos) 1-3-2
		Assert.assertEquals(3, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(2, nav.getCurrent().find(NavigationHeadState.Current).getStack().getItems().size());
		Assert.assertEquals(2, nav.getCurrent().find(NavigationHeadState.Current).getIndexStack());
		Assert.assertEquals(0, nav.getCurrent().find(NavigationHeadState.Current).getIndexItem());
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getProducts().size());
		Assert.assertFalse(nav.getCurrent().getBody().isRequired());
	}

	@Test(expected = NavigationException.class)
	@Transactional
	public void testShouldGoThrowErrorBecauseSelectingOignong() throws Exception {
		menu(2, 13);
	}

	@Test
	@Transactional
	public void testShouldGoToTacosSauce1OnMenu() throws Exception {
		NavigationBean nav = menu(2);
		// Should got to next (sauce1 inside tacos) 1-3-2
		Assert.assertEquals(3, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(2, nav.getCurrent().find(NavigationHeadState.Current).getStack().getItems().size());
		Assert.assertEquals(2, nav.getCurrent().find(NavigationHeadState.Current).getIndexStack());
		Assert.assertEquals(0, nav.getCurrent().find(NavigationHeadState.Current).getIndexItem());
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getProducts().size());
		Assert.assertFalse(nav.getCurrent().getBody().isRequired());
	}

	@Test
	@Transactional
	public void testShouldGoToTacosSauce2OnMenu() throws Exception {
		NavigationBean nav = menu(3);
		// Should got to next (sauce2 inside tacos) 1-3-2 , then select sauceb
		Assert.assertEquals(3, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(2, nav.getCurrent().find(NavigationHeadState.Current).getStack().getItems().size());
		Assert.assertEquals(2, nav.getCurrent().find(NavigationHeadState.Current).getIndexStack());
		Assert.assertEquals(1, nav.getCurrent().find(NavigationHeadState.Current).getIndexItem());
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getProducts().size());
		Assert.assertFalse(nav.getCurrent().getBody().isRequired());
		ProductViewDto founded = find(ScenarioContext.SAUCE_BLANCHE, nav.getCurrent().getBody().getProducts());
		Assert.assertNotNull(founded);
	}

	@Test
	@Transactional
	public void testShouldGoToAccompagenementOnMenu() throws Exception {
		NavigationBean nav = menu(4);
		// Should got to next (accompagenement) 1-3 , then skip
		Assert.assertEquals(2, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(3, nav.getCurrent().find(NavigationHeadState.Current).getStack().getItems().size());
		Assert.assertEquals(1, nav.getCurrent().find(NavigationHeadState.Current).getIndexStack());
		Assert.assertEquals(1, nav.getCurrent().find(NavigationHeadState.Current).getIndexItem());
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getProducts().size());
		Assert.assertFalse(nav.getCurrent().getBody().isRequired());
	}

	@Test
	@Transactional
	public void testShouldGoToDessertOnMenu() throws Exception {
		NavigationBean nav = menu(5);
		// Should got to next (desset) 1-3 , then skip
		Assert.assertEquals(2, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(3, nav.getCurrent().find(NavigationHeadState.Current).getStack().getItems().size());
		Assert.assertEquals(1, nav.getCurrent().find(NavigationHeadState.Current).getIndexStack());
		Assert.assertEquals(2, nav.getCurrent().find(NavigationHeadState.Current).getIndexItem());
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getProducts().size());
		Assert.assertFalse(nav.getCurrent().getBody().isRequired());
	}

	@Test
	@Transactional
	public void testShouldGoToDessertOnMenuUsingSkip() throws Exception {
		NavigationBean nav = menu(5, 1);
		// Should got to next (desset) 1-3 , then skip
		Assert.assertEquals(2, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(3, nav.getCurrent().find(NavigationHeadState.Current).getStack().getItems().size());
		Assert.assertEquals(1, nav.getCurrent().find(NavigationHeadState.Current).getIndexStack());
		Assert.assertEquals(2, nav.getCurrent().find(NavigationHeadState.Current).getIndexItem());
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getProducts().size());
		Assert.assertFalse(nav.getCurrent().getBody().isRequired());
	}

	@Test
	@Transactional
	public void testShouldEndOnMenu() throws Exception {
		NavigationBean nav = menu(6);
		Assert.assertEquals(0, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(NavigationState.Commit, nav.getCurrent().getState());
	}

	@Test
	@Transactional
	public void testShouldInitTacos() throws Exception {
		NavigationBean nav = tacos(0);
		// should have 1 stack
		Assert.assertEquals(1, nav.getHistory().size());
		Assert.assertEquals(1, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(1, nav.getCurrent().find(NavigationHeadState.Current).getStack().getItems().size());
		Assert.assertEquals(0, nav.getCurrent().find(NavigationHeadState.Current).getIndexStack());
		Assert.assertEquals(0, nav.getCurrent().find(NavigationHeadState.Current).getIndexItem());
		Assert.assertEquals(0, nav.getCurrent().getBody().getProducts().size());
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getIngredients().size());
		IngredientViewDto founded = findIng(ScenarioContext.TOMATE, nav.getCurrent().getBody().getIngredients());
		Assert.assertNotNull(founded);
	}

	@Test
	@Transactional
	public void testShouldGoToTacosSauce1AfterSelectingTOmate() throws Exception {
		NavigationBean nav = tacos(1, 1);
		// Should got to next (sauce1 inside tacos) 1-2
		Assert.assertEquals(2, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(2, nav.getCurrent().find(NavigationHeadState.Current).getStack().getItems().size());
		Assert.assertEquals(1, nav.getCurrent().find(NavigationHeadState.Current).getIndexStack());
		Assert.assertEquals(0, nav.getCurrent().find(NavigationHeadState.Current).getIndexItem());
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getProducts().size());
		Assert.assertFalse(nav.getCurrent().getBody().isRequired());
	}

	@Test
	@Transactional
	public void testShouldGoToTacosSauce1AfterSkip() throws Exception {
		NavigationBean nav = tacos(1);
		// Should got to next (sauce1 inside tacos) 1-2
		Assert.assertEquals(2, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(2, nav.getCurrent().find(NavigationHeadState.Current).getStack().getItems().size());
		Assert.assertEquals(1, nav.getCurrent().find(NavigationHeadState.Current).getIndexStack());
		Assert.assertEquals(0, nav.getCurrent().find(NavigationHeadState.Current).getIndexItem());
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getProducts().size());
		Assert.assertFalse(nav.getCurrent().getBody().isRequired());
	}

	//
	@Test
	@Transactional
	public void testShouldGoToTacosSauce2OnTacos() throws Exception {
		NavigationBean nav = tacos(2);
		// Should got to next (sauce2 inside tacos) 1-2 , then select sauceb
		Assert.assertEquals(2, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(2, nav.getCurrent().find(NavigationHeadState.Current).getStack().getItems().size());
		Assert.assertEquals(1, nav.getCurrent().find(NavigationHeadState.Current).getIndexStack());
		Assert.assertEquals(1, nav.getCurrent().find(NavigationHeadState.Current).getIndexItem());
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getProducts().size());
		Assert.assertFalse(nav.getCurrent().getBody().isRequired());
		ProductViewDto founded = find(ScenarioContext.SAUCE_BLANCHE, nav.getCurrent().getBody().getProducts());
		Assert.assertNotNull(founded);
	}

	@Test
	@Transactional
	public void testShouldEndOnTacos() throws Exception {
		NavigationBean nav = tacos(3);
		Assert.assertEquals(0, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(NavigationState.Commit, nav.getCurrent().getState());
	}

	@Test
	@Transactional
	public void testShouldEndOnKebab() throws Exception {
		NavigationBean nav = kebab(0);
		Assert.assertEquals(0, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(NavigationState.Commit, nav.getCurrent().getState());
	}

	@Test
	@Transactional
	public void testShouldGoToDessertOnMenuAndGoBackWithoutModify() throws Exception {
		NavigationBean nav = menu(5, 1);
		// Should bo back to selected sandwich
		boolean isBack = navigator.backTo(nav, this.idsByStep.get(0));
		Assert.assertTrue(isBack);
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getProducts().size());
		ProductViewDto founded = find(ScenarioContext.TACOS, nav.getCurrent().getBody().getProducts());
		Assert.assertNotNull(founded);
		Assert.assertEquals(founded.getId(), nav.getCurrent().getBody().getProduct().getId());
		// Return to last and finish
		nav.goToLast();
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getProducts().size());
		founded = find(ScenarioContext.CHOCOLAT, nav.getCurrent().getBody().getProducts());
		Assert.assertNotNull(founded);
		navigator.push(nav, daoProduct.get(founded.getId()));
		Assert.assertEquals(0, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(NavigationState.Commit, nav.getCurrent().getState());
	}

	@Test
	@Transactional
	public void testShouldGoToDessertOnMenuAndGoBackWithModify() throws Exception {
		NavigationBean nav = menu(5, 1);
		// Should bo back to selected sandwich
		boolean isBack = navigator.backTo(nav, this.idsByStep.get(0));
		Assert.assertTrue(isBack);
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getProducts().size());
		ProductViewDto founded = find(ScenarioContext.TACOS, nav.getCurrent().getBody().getProducts());
		Assert.assertNotNull(founded);
		Assert.assertEquals(founded.getId(), nav.getCurrent().getBody().getProduct().getId());
		int before = nav.getHistory().size();
		// Modify and continue
		founded = find(ScenarioContext.KEBAB, nav.getCurrent().getBody().getProducts());
		navigator.push(nav, daoProduct.get(founded.getId()));
		// Should got to next (accompagenement) 1-3 , then skip
		Assert.assertEquals(2, nav.getCurrent().getHead().getStacks().size());
		Assert.assertEquals(3, nav.getCurrent().find(NavigationHeadState.Current).getStack().getItems().size());
		Assert.assertEquals(1, nav.getCurrent().find(NavigationHeadState.Current).getIndexStack());
		Assert.assertEquals(1, nav.getCurrent().find(NavigationHeadState.Current).getIndexItem());
		Assert.assertNotEquals(0, nav.getCurrent().getBody().getProducts().size());
		Assert.assertFalse(nav.getCurrent().getBody().isRequired());
		int after = nav.getHistory().size();
		Assert.assertTrue(before > after);
	}

	@Test
	@Transactional
	public void testShouldTransformMenuToTree() throws Exception {
		final Deque<Serializable> ids = new LinkedList<Serializable>();
		ids.add(context.getAllProducts().get(ScenarioContext.MENU_MOYEN));
		ids.add(context.getAllProducts().get(ScenarioContext.TACOS));
		ids.add(context.getAllIngredients().get(ScenarioContext.TOMATE));
		ids.add(context.getAllProducts().get(ScenarioContext.SAUCE_BLANCHE));
		ids.add(context.getAllProducts().get(ScenarioContext.CHOCOLAT));
		//
		NavigationBean nav = menu(6, 1);
		// Should bo back to selected sandwich
		ProductAsTreeDto tree = graphConverter.toDto(nav, new OptionsList());
		GraphIteratorBuilder.buildDeep().iterate(tree, new IteratorListenerIGraph() {

			public void onFounded(IGraph node) {
				ProductAsTreeDto tree = (ProductAsTreeDto) node;
				Serializable s = ids.pollFirst();
				if (s instanceof ProductDefinition) {
					if (tree.isRoot()) {
						Assert.assertEquals(ProductNodeType.PRODUCT, tree.getNode().getType());
					} else {
						Assert.assertEquals(ProductNodeType.PRODUCT_PART, tree.getNode().getType());
					}
					Assert.assertNotNull(tree.getNode().getProduct());
					Assert.assertEquals(((ProductDefinition) s).getId(), tree.getNode().getProduct().getId());
				} else if (s instanceof IngredientDefinition) {
					Assert.assertFalse(tree.isRoot());
					Assert.assertEquals(ProductNodeType.INGREDIENT, tree.getNode().getType());
					Assert.assertNotNull(tree.getNode().getProduct());
					Assert.assertNotNull(tree.getNode().getIngredient());
					Assert.assertEquals(((IngredientDefinition) s).getId(), tree.getNode().getIngredient().getId());
				} else {
					Assert.fail("Coul not determine type of serilised");
				}
			}
		});
		Assert.assertTrue(ids.isEmpty());
	}

	@Test
	@Transactional
	public void testShouldTransformTacosToTree() throws Exception {
		final Deque<Serializable> ids = new LinkedList<Serializable>();
		ids.add(context.getAllProducts().get(ScenarioContext.TACOS));
		ids.add(context.getAllIngredients().get(ScenarioContext.TOMATE));
		ids.add(context.getAllProducts().get(ScenarioContext.SAUCE_BLANCHE));
		//
		NavigationBean nav = tacos(6, 1);
		// Should bo back to selected sandwich
		ProductAsTreeDto tree = graphConverter.toDto(nav, new OptionsList());
		GraphIteratorBuilder.buildDeep().iterate(tree, new IteratorListenerIGraph() {

			public void onFounded(IGraph node) {
				ProductAsTreeDto tree = (ProductAsTreeDto) node;
				Serializable s = ids.pollFirst();
				if (s instanceof ProductDefinition) {
					if (tree.isRoot()) {
						Assert.assertEquals(ProductNodeType.PRODUCT, tree.getNode().getType());
					} else {
						Assert.assertEquals(ProductNodeType.PRODUCT_PART, tree.getNode().getType());
					}
					Assert.assertNotNull(tree.getNode().getProduct());
					Assert.assertEquals(((ProductDefinition) s).getId(), tree.getNode().getProduct().getId());
				} else if (s instanceof IngredientDefinition) {
					Assert.assertFalse(tree.isRoot());
					Assert.assertEquals(ProductNodeType.INGREDIENT, tree.getNode().getType());
					Assert.assertNotNull(tree.getNode().getProduct());
					Assert.assertNotNull(tree.getNode().getIngredient());
					Assert.assertEquals(((IngredientDefinition) s).getId(), tree.getNode().getIngredient().getId());
				} else {
					Assert.fail("Coul not determine type of serilised");
				}
			}
		});
		Assert.assertTrue(ids.isEmpty());
	}

	@Test
	@Transactional
	public void testShouldTransformKebabToTree() throws Exception {
		final Deque<Serializable> ids = new LinkedList<Serializable>();
		ids.add(context.getAllProducts().get(ScenarioContext.KEBAB));
		//
		NavigationBean nav = kebab(6);
		// Should bo back to selected sandwich
		ProductAsTreeDto tree = graphConverter.toDto(nav, new OptionsList());
		GraphIteratorBuilder.buildDeep().iterate(tree, new IteratorListenerIGraph() {

			public void onFounded(IGraph node) {
				ProductAsTreeDto tree = (ProductAsTreeDto) node;
				Serializable s = ids.pollFirst();
				if (s instanceof ProductDefinition) {
					if (tree.isRoot()) {
						Assert.assertEquals(ProductNodeType.PRODUCT, tree.getNode().getType());
					} else {
						Assert.assertEquals(ProductNodeType.PRODUCT_PART, tree.getNode().getType());
					}
					Assert.assertNotNull(tree.getNode().getProduct());
					Assert.assertEquals(((ProductDefinition) s).getId(), tree.getNode().getProduct().getId());
				} else if (s instanceof IngredientDefinition) {
					Assert.assertFalse(tree.isRoot());
					Assert.assertEquals(ProductNodeType.INGREDIENT, tree.getNode().getType());
					Assert.assertNotNull(tree.getNode().getProduct());
					Assert.assertNotNull(tree.getNode().getIngredient());
					Assert.assertEquals(((IngredientDefinition) s).getId(), tree.getNode().getIngredient().getId());
				} else {
					Assert.fail("Coul not determine type of serilised");
				}
			}
		});
		Assert.assertTrue(ids.isEmpty());
	}
}
