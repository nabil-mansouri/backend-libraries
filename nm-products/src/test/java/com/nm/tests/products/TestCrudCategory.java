package com.nm.tests.products;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nm.app.locale.LocaleFormDto;
import com.nm.app.locale.SoaLocale;
import com.nm.cms.dtos.CmsDtoContentsTextForm;
import com.nm.products.dao.DaoCategory;
import com.nm.products.dao.impl.CategoryQueryBuilder;
import com.nm.products.dtos.forms.CategoryFormDto;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.views.CategoryViewDto;
import com.nm.products.factory.ProductDataFactory;
import com.nm.products.model.Category;
import com.nm.products.soa.SoaProductDefinition;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestProducts.class)
public class TestCrudCategory {

	@Autowired
	private SoaProductDefinition soaProductDefinition;
	@Autowired
	private DaoCategory daoCategory;
	@Autowired
	private SoaLocale soaLocale;
	@Autowired
	private ProductDataFactory dataFactory;
	//
	private CategoryFormDto form;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	public void setAppliContent(ConfigurableApplicationContext applicationContext) {
	}

	//
	@Before
	public void setUp() {
		soaLocale.setSelectedLocales(Arrays.asList(new LocaleFormDto("fr"), new LocaleFormDto("en")));
		soaLocale.setDefaultLocale(new LocaleFormDto("fr"));
		this.form = dataFactory.createCategory();
	}

	@Test
	@Transactional
	public void testShouldCreateCategory() throws NoDataFoundException {
		Assert.assertEquals(2, form.getCms().getContents().size());
	}

	@Test
	@Transactional
	public void testShouldSaveCategory() throws NoDataFoundException {
		CategoryViewDto view = soaProductDefinition.saveOrUpdate(form, new OptionsList());
		Assert.assertNotNull(view.getId());
	}

	@Test
	@Transactional
	public void testDeleteCategoryWithProduct() throws NoDataFoundException {
		CategoryViewDto cat1 = soaProductDefinition.saveOrUpdate(form, new OptionsList());
		ProductFormDto product = dataFactory.createProduct();
		CategoryViewDto cat2 = product.getCategories().iterator().next();
		Assert.assertEquals(cat2.getId(), cat1.getId());
		cat2.setSelected(true);
		soaProductDefinition.saveOrUpdate(product, new OptionsList());
		soaProductDefinition.removeCategory(cat1.getId());
		product = soaProductDefinition.editProduct(product.getId());
		Assert.assertEquals(0, product.getCategories().size());
	}

	@Test
	@Transactional
	public void testShouldSaveCategoryWithParent() throws NoDataFoundException {
		CategoryViewDto parentView = soaProductDefinition.saveOrUpdate(form, new OptionsList());
		CategoryFormDto form = soaProductDefinition.createCategory(parentView.getId());
		for (CmsDtoContentsTextForm content : form.getCms().getContents()) {
			content.setName("CAT");
		}
		CategoryViewDto view = soaProductDefinition.saveOrUpdate(form, new OptionsList());
		Assert.assertNotNull(view.getId());
		Category parentEntity = daoCategory.get(parentView.getId());
		Assert.assertEquals(1, parentEntity.getChildren().size());
		//
		CategoryQueryBuilder query = CategoryQueryBuilder.get().withNoParent();
		Collection<Category> roots = daoCategory.find(query);
		Assert.assertEquals(1, roots.size());
	}

	@Test
	@Transactional
	public void testShouldCategoryWithParentAndDeleteChild() throws NoDataFoundException {
		CategoryQueryBuilder query = CategoryQueryBuilder.get().withNoParent();
		CategoryViewDto parentView = soaProductDefinition.saveOrUpdate(form, new OptionsList());
		CategoryFormDto childForm = soaProductDefinition.createCategory(parentView.getId());
		CategoryViewDto childView = soaProductDefinition.saveOrUpdate(childForm, new OptionsList());
		Assert.assertNotNull(childView.getId());
		//
		Collection<Category> roots = daoCategory.find(query);
		Assert.assertEquals(1, roots.iterator().next().getChildren().size());
		soaProductDefinition.removeCategory(childView.getId());
		//
		roots = daoCategory.find(query);
		Assert.assertEquals(1, roots.size());
		Assert.assertEquals(0, roots.iterator().next().getChildren().size());
	}

	@Test
	@Transactional
	public void testShouldCategoryWithParentAndDeleteParent() throws NoDataFoundException {
		CategoryViewDto parentView = soaProductDefinition.saveOrUpdate(form, new OptionsList());
		CategoryFormDto childForm = soaProductDefinition.createCategory(parentView.getId());
		CategoryViewDto childView = soaProductDefinition.saveOrUpdate(childForm, new OptionsList());
		Assert.assertNotNull(childView.getId());
		soaProductDefinition.removeCategory(parentView.getId());
		//
		CategoryQueryBuilder query = CategoryQueryBuilder.get().withNoParent();
		Collection<Category> roots = daoCategory.find(query);
		Assert.assertEquals(1, roots.size());
		Assert.assertEquals(0, roots.iterator().next().getChildren().size());
	}

	@Test
	@Transactional
	public void testShouldTransformJson() throws Exception {
		CategoryViewDto parentView = soaProductDefinition.saveOrUpdate(form, new OptionsList());
		CategoryFormDto childForm = soaProductDefinition.createCategory(parentView.getId());
		String value = mapper.writeValueAsString(childForm);
		CategoryFormDto clone = mapper.readValue(value, CategoryFormDto.class);
		Assert.assertEquals(clone.getParent().getId(), childForm.getParent().getId());
	}
}
