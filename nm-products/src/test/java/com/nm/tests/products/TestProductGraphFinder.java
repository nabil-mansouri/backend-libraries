package com.nm.tests.products;

import java.util.ArrayList;
import java.util.Collection;

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
import com.nm.products.constants.ProductNodeType;
import com.nm.products.dao.DaoIngredient;
import com.nm.products.dao.DaoProductDefinition;
import com.nm.products.dtos.views.ProductAsTreeDto;
import com.nm.products.dtos.views.ProductAsTreeIdentifier;
import com.nm.products.dtos.views.ProductNodeDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.soa.SoaProductDefinition;
import com.nm.utils.graphs.IGraph;
import com.nm.utils.graphs.finder.GraphFinder;
import com.nm.utils.graphs.finder.GraphFinderPath;
import com.nm.utils.graphs.finder.GraphFinderPathList;
import com.nm.utils.graphs.finder.IGraphIdentifier;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestProducts.class)
public class TestProductGraphFinder {

	@Autowired
	protected SoaProductDefinition soaProductDefinition;
	@Autowired
	protected DaoProductDefinition daoProduct;
	@Autowired
	protected DaoIngredient daoIngredient;
	@Autowired
	protected SoaLocale soaLocale;
	//
	private Collection<IGraphIdentifier> ids;
	private GraphFinder finder;

	@Before
	public void setUp() throws Exception {
		ids = new ArrayList<IGraphIdentifier>();
		finder = new GraphFinder(GraphIteratorBuilder.buildDeep());
	}

	@Test
	@Transactional
	public void testShouldFindProductParent() throws Exception {
		//
		ProductAsTreeDto tree = new ProductAsTreeDto();
		ProductAsTreeDto child = tree.createChild(false).createChild(false);
		child.setNode(new ProductNodeDto().setType(ProductNodeType.PRODUCT_PART).setProduct(new ProductViewDto(2l)));

		ids.add(new ProductAsTreeIdentifier(new ProductViewDto(2l)).add(ProductNodeType.PRODUCT)
				.add(ProductNodeType.PRODUCT_PART));
		GraphFinderPath<IGraph> gPath = finder.findFirst(tree, ids);
		Assert.assertTrue(gPath.founded());
		Assert.assertEquals(2l, ((ProductAsTreeDto) gPath.getFounded()).getNode().getProduct().getId(), 0);
	}

	@Test
	@Transactional
	public void testShouldNotFindProductParent() throws Exception {
		//
		ProductAsTreeDto tree = new ProductAsTreeDto();
		ProductAsTreeDto child = tree.createChild(false).createChild(false);
		child.setNode(new ProductNodeDto().setType(ProductNodeType.PRODUCT_PART).setProduct(new ProductViewDto(1l)));

		ids.add(new ProductAsTreeIdentifier(new ProductViewDto(2l)).add(ProductNodeType.PRODUCT)
				.add(ProductNodeType.PRODUCT_PART));
		GraphFinderPath<IGraph> gPath = finder.findFirst(tree, ids);
		Assert.assertFalse(gPath.founded());
	}

	@Test
	@Transactional
	public void testShouldFindProductParentFirstStrict() throws Exception {
		//
		ProductAsTreeDto tree = new ProductAsTreeDto();
		ProductAsTreeDto child = tree.createChild(false).createChild(false);
		child.setNode(new ProductNodeDto().setType(ProductNodeType.PRODUCT_PART)
				.setProduct(new ProductViewDto(1l).setIdDraft(1l)));
		child = tree.createChild(false).createChild(false);
		child.setNode(new ProductNodeDto().setType(ProductNodeType.PRODUCT_PART)
				.setProduct(new ProductViewDto(1l).setIdDraft(2l)));
		//
		ids.add(new ProductAsTreeIdentifier(new ProductViewDto(1l)).add(ProductNodeType.PRODUCT)
				.add(ProductNodeType.PRODUCT_PART));
		GraphFinderPath<IGraph> gPath = finder.findFirst(tree, ids);
		Assert.assertTrue(gPath.founded());
		Assert.assertEquals(1l, ((ProductAsTreeDto) gPath.getFounded()).getNode().getProduct().getId(), 0);
		Assert.assertEquals(1l, ((ProductAsTreeDto) gPath.getFounded()).getNode().getProduct().getIdDraft(), 0);
	}

	@Test
	@Transactional
	public void testShouldFindProductParentLastStrict() throws Exception {
		//
		ProductAsTreeDto tree = new ProductAsTreeDto();
		ProductAsTreeDto child = tree.createChild(false).createChild(false);
		child.setNode(new ProductNodeDto().setType(ProductNodeType.PRODUCT_PART)
				.setProduct(new ProductViewDto(1l).setIdDraft(1l)));
		child = tree.createChild(false).createChild(false);
		child.setNode(new ProductNodeDto().setType(ProductNodeType.PRODUCT_PART)
				.setProduct(new ProductViewDto(1l).setIdDraft(2l)));
		//
		ids.add(new ProductAsTreeIdentifier(new ProductViewDto(1l)).add(ProductNodeType.PRODUCT)
				.add(ProductNodeType.PRODUCT_PART));
		GraphFinderPath<IGraph> gPath = finder.findLast(tree, ids);
		Assert.assertTrue(gPath.founded());
		Assert.assertEquals(1l, ((ProductAsTreeDto) gPath.getFounded()).getNode().getProduct().getId(), 0);
		Assert.assertEquals(2l, ((ProductAsTreeDto) gPath.getFounded()).getNode().getProduct().getIdDraft(), 0);
	}

	@Test
	@Transactional
	public void testShouldFindAll() throws Exception {
		//
		ProductAsTreeDto tree = new ProductAsTreeDto();
		ProductAsTreeDto child = tree.createChild(false).createChild(false);
		child = tree.createChild(false).createChild(false);
		child = tree.createChild(false).createChild(false);
		child.setNode(new ProductNodeDto().setType(ProductNodeType.PRODUCT_PART)
				.setProduct(new ProductViewDto(1l).setIdDraft(1l)));
		child = tree.createChild(false).createChild(false);
		child.setNode(new ProductNodeDto().setType(ProductNodeType.PRODUCT_PART)
				.setProduct(new ProductViewDto(1l).setIdDraft(2l)));
		child = tree.createChild(false).createChild(false);
		child.setNode(new ProductNodeDto().setType(ProductNodeType.PRODUCT_PART)
				.setProduct(new ProductViewDto(12l).setIdDraft(2l)));
		//
		ids.add(new ProductAsTreeIdentifier(new ProductViewDto(1l)).add(ProductNodeType.PRODUCT)
				.add(ProductNodeType.PRODUCT_PART));
		GraphFinderPathList<?> gPath = finder.findAll(tree, ids);
		Assert.assertTrue(gPath.founded());
		Assert.assertEquals(2, gPath.getAll().size());
	}
}
