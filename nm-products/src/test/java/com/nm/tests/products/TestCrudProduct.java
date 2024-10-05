package com.nm.tests.products;

import java.util.Arrays;

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
import com.nm.products.dao.DaoCategory;
import com.nm.products.dao.DaoProductDefinition;
import com.nm.products.dtos.forms.CategoryFormDto;
import com.nm.products.dtos.forms.CategoryTreeDto;
import com.nm.products.dtos.forms.IngredientFormDto;
import com.nm.products.dtos.forms.ProductFormDto;
import com.nm.products.dtos.options.ProductFormOptions;
import com.nm.products.dtos.views.IngredientViewDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.products.factory.ProductDataFactory;
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
public class TestCrudProduct {

	@Autowired
	protected SoaProductDefinition soaProductDefinition;
	@Autowired
	private DaoCategory daoCategory;
	@Autowired
	protected SoaLocale soaLocale;
	@Autowired
	private DaoProductDefinition daoProductDefinition;
	//
	@Autowired
	private ProductDataFactory dataFactory;
	private ProductFormDto form;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	public void setAppliContent(ConfigurableApplicationContext applicationContext) {
	}

	//
	@Before
	public void setUp() {
		soaLocale.setSelectedLocales(Arrays.asList(new LocaleFormDto("fr"), new LocaleFormDto("en")));
		soaLocale.setDefaultLocale(new LocaleFormDto("fr"));
		this.form = dataFactory.createProduct();
	}

	@Test
	@Transactional
	public void testShouldCreateProduct() throws NoDataFoundException {
		form = soaProductDefinition.createProduct();
		Assert.assertEquals(2, form.getCms().getContents().size());
		Assert.assertEquals(1, form.getParts().size());
		Assert.assertFalse(form.getHasIngredients());
		Assert.assertFalse(form.getHasProducts());
		Assert.assertTrue(form.getParts().iterator().next().isSelected());
	}

	@Test
	@Transactional
	public void testShouldCreateCategoryTree() throws NoDataFoundException {
		CategoryFormDto parent = dataFactory.createCategory();
		soaProductDefinition.saveOrUpdate(parent, new OptionsList());
		CategoryFormDto child = dataFactory.createCategory(parent.getId());
		soaProductDefinition.saveOrUpdate(child, new OptionsList());
		daoCategory.flush();
		System.out.println(daoCategory.count());
		//
		form = soaProductDefinition.createProduct();
		Assert.assertEquals(1, form.getCategories().size());
		Assert.assertEquals(1, form.getCategories().iterator().next().getChildrens().size());
	}

	@Test
	@Transactional
	public void testShouldDeleteDraftAfterSave() throws Exception {
		form = soaProductDefinition.createProduct();
		soaProductDefinition.saveOrUpdateDraft(form);
		Assert.assertNotNull(form.getIdDraft());
		Assert.assertEquals(1, soaProductDefinition.fetchDrafts(new OptionsList()).size());
		Assert.assertNotNull(soaProductDefinition.fetchDrafts(new OptionsList()).iterator().next().getIdDraft());
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		Assert.assertEquals(0, soaProductDefinition.fetchDrafts(new OptionsList()).size());
	}

	@Test
	@Transactional
	public void testShouldDeleteDraft() throws Exception {
		form = soaProductDefinition.createProduct();
		soaProductDefinition.saveOrUpdateDraft(form);
		Assert.assertNotNull(form.getIdDraft());
		Assert.assertEquals(1, soaProductDefinition.fetchDrafts(new OptionsList()).size());
		Assert.assertNotNull(soaProductDefinition.fetchDrafts(new OptionsList()).iterator().next().getIdDraft());
		soaProductDefinition.removeDraft(form.getIdDraft());
		Assert.assertEquals(0, soaProductDefinition.fetchDrafts(new OptionsList()).size());
	}

	@Test
	@Transactional
	public void testShouldSaveOnlyOneDraft() throws Exception {
		form = soaProductDefinition.createProduct();
		soaProductDefinition.saveOrUpdateDraft(form);
		Assert.assertNotNull(form.getIdDraft());
		Assert.assertEquals(1, soaProductDefinition.fetchDrafts(new OptionsList()).size());
		soaProductDefinition.saveOrUpdateDraft(form);
		Assert.assertEquals(1, soaProductDefinition.fetchDrafts(new OptionsList()).size());
	}

	@Test
	@Transactional
	public void testShouldSaveOnlySelectedRootCategories() throws NoDataFoundException {
		CategoryFormDto parent = dataFactory.createCategory();
		soaProductDefinition.saveOrUpdate(parent, new OptionsList());
		CategoryFormDto parent2 = dataFactory.createCategory();
		soaProductDefinition.saveOrUpdate(parent2, new OptionsList());
		//
		form = soaProductDefinition.createProduct();
		CategoryTreeDto selected = form.getCategories().iterator().next();
		selected.setSelected(true);
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		//
		form = soaProductDefinition.editProduct(form.getId());
		int nbSelected = 0;
		for (CategoryTreeDto tree : form.getCategories()) {
			if (tree.isSelected()) {
				nbSelected++;
			}
		}
		Assert.assertEquals(1, nbSelected);
	}

	@Test
	@Transactional
	public void testShouldSaveOnlySelectedChildrenCategories() throws NoDataFoundException {
		CategoryFormDto parent = dataFactory.createCategory();
		soaProductDefinition.saveOrUpdate(parent, new OptionsList());
		CategoryFormDto parent2 = dataFactory.createCategory(parent.getId());
		soaProductDefinition.saveOrUpdate(parent2, new OptionsList());
		//
		form = soaProductDefinition.createProduct();
		CategoryTreeDto selected = form.getCategories().iterator().next().getChildrens().iterator().next();
		selected.setSelected(true);
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		//
		form = soaProductDefinition.editProduct(form.getId());
		int nbSelected = 0, nbCHildrenSelected = 0;
		for (CategoryTreeDto tree : form.getCategories()) {
			if (tree.isSelected()) {
				nbSelected++;
			}
			for (CategoryTreeDto child : tree.getChildrens()) {
				if (child.isSelected()) {
					nbSelected++;
					nbCHildrenSelected++;
				}
			}
		}
		Assert.assertEquals(1, nbSelected);
		Assert.assertEquals(1, nbCHildrenSelected);
	}

	@Test
	@Transactional
	public void testShouldRefreshCategoryTreeAfterCreate() throws NoDataFoundException {
		CategoryFormDto parent = dataFactory.createCategory();
		soaProductDefinition.saveOrUpdate(parent, new OptionsList());
		daoProductDefinition.flush();
		CategoryFormDto child = dataFactory.createCategory(parent.getId());
		soaProductDefinition.saveOrUpdate(child, new OptionsList());
		daoProductDefinition.flush();
		//
		form = soaProductDefinition.createProduct();
		form.getCategories().iterator().next().getChildrens().iterator().next().setSelected(true);
		//
		CategoryFormDto parent2 = dataFactory.createCategory();
		soaProductDefinition.saveOrUpdate(parent2, new OptionsList());
		//
		soaProductDefinition.refresh(form, new OptionsList().withOption(ProductFormOptions.Categories));
		Assert.assertEquals(2, form.getCategories().size());
		boolean ok = false;
		for (CategoryTreeDto tree : form.getCategories()) {
			if (tree.childrens().size() > 0) {
				ok = true;
				Assert.assertTrue(form.getCategories().iterator().next().getChildrens().iterator().next().isSelected());
			}
		}
		Assert.assertTrue(ok);
	}

	@Test
	@Transactional
	public void testShouldRefreshCategoryTreeAfterDelete() throws NoDataFoundException {
		CategoryFormDto parent = dataFactory.createCategory();
		soaProductDefinition.saveOrUpdate(parent, new OptionsList());
		CategoryFormDto parent2 = dataFactory.createCategory();
		soaProductDefinition.saveOrUpdate(parent2, new OptionsList());
		//
		form = soaProductDefinition.createProduct();
		Assert.assertEquals(2, form.getCategories().size());
		soaProductDefinition.refresh(form, new OptionsList().withOption(ProductFormOptions.Categories));
		Assert.assertEquals(2, form.getCategories().size());
		//
		soaProductDefinition.removeCategory(parent.getId());
		soaProductDefinition.refresh(form, new OptionsList().withOption(ProductFormOptions.Categories));
		Assert.assertEquals(1, form.getCategories().size());
	}

	@Test
	@Transactional
	public void testShouldCreatePartsExcludingSelf() throws NoDataFoundException {
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		ProductFormDto form1 = dataFactory.createProduct();
		Assert.assertEquals(1, form1.getProducts().size());
		soaProductDefinition.saveOrUpdate(form1, new OptionsList());
		//
		form1 = soaProductDefinition.editProduct(form1.getId());
		Assert.assertEquals(1, form1.getProducts().size());
		Assert.assertFalse(form1.getProducts().containsKey(form1.getId()));
	}

	@Test
	@Transactional
	public void testShouldRefreshPartsAfterCreateAndEdit() throws NoDataFoundException {
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		Assert.assertEquals(0, form.getProducts().size());
		//
		ProductFormDto form1 = dataFactory.createProduct();
		soaProductDefinition.saveOrUpdate(form1, new OptionsList());
		//
		soaProductDefinition.refresh(form, new OptionsList().withOption(ProductFormOptions.Parts));
		Assert.assertEquals(1, form.getProducts().size());
		form.getParts().iterator().next().select(form1.getId());
		soaProductDefinition.refresh(form, new OptionsList().withOption(ProductFormOptions.Parts));
		Assert.assertTrue(form.getParts().iterator().next().isSelected(form1.getId()));
		Assert.assertEquals(1, form.getProducts().size());
		// EDIT
		form1 = soaProductDefinition.editProduct(form1.getId());
		soaProductDefinition.saveOrUpdate(form1, new OptionsList());
		soaProductDefinition.refresh(form, new OptionsList().withOption(ProductFormOptions.Parts));
		Assert.assertTrue(form.getParts().iterator().next().isSelected(form1.getId()));
		Assert.assertEquals(1, form.getProducts().size());
	}

	@Test
	@Transactional
	public void testShouldRefreshPartsAfterEdit() throws NoDataFoundException {
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		Assert.assertEquals(0, form.getProducts().size());
		//
		ProductFormDto form1 = dataFactory.createProduct();
		Assert.assertEquals(1, form1.getProducts().size());
		//
		form = soaProductDefinition.editProduct(form.getId());
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		//
		soaProductDefinition.refresh(form1, new OptionsList().withOption(ProductFormOptions.Parts));
		Assert.assertEquals(1, form1.getProducts().size());
	}

	@Test
	@Transactional
	public void testShouldRefreshPartsAfterDelete() throws NoDataFoundException {
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		ProductFormDto form1 = dataFactory.createProduct();
		Assert.assertEquals(1, form1.getProducts().size());
		//
		form1.getParts().iterator().next().select(form.getId());
		soaProductDefinition.removeProduct(form.getId());
		soaProductDefinition.refresh(form1, new OptionsList().withOption(ProductFormOptions.Parts));
		Assert.assertEquals(0, form1.getProducts().size());
		Assert.assertFalse(form1.getParts().iterator().next().isSelected(form.getId()));
	}

	@Test
	@Transactional
	public void testShouldDeleteProductInAnotherPart() throws NoDataFoundException {
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		ProductFormDto form1 = dataFactory.createProduct();
		Assert.assertEquals(1, form1.getProducts().size());
		//
		form1.getParts().iterator().next().select(form.getId());
		soaProductDefinition.saveOrUpdate(form1, new OptionsList());
		soaProductDefinition.removeProduct(form.getId());
		form1 = soaProductDefinition.editProduct(form1.getId());
		Assert.assertEquals(0, form1.getProducts().size());
		Assert.assertFalse(form1.getParts().iterator().next().isSelected(form.getId()));
	}

	@Test
	@Transactional
	public void testShouldDeleteProductWithParts() throws NoDataFoundException {
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		ProductFormDto form1 = dataFactory.createProduct();
		Assert.assertEquals(1, form1.getProducts().size());
		//
		form1.getParts().iterator().next().select(form.getId());
		soaProductDefinition.saveOrUpdate(form1, new OptionsList());
		soaProductDefinition.removeProduct(form1.getId());
		form = soaProductDefinition.editProduct(form.getId());
		Assert.assertEquals(0, form.getProducts().size());
	}

	@Test
	@Transactional
	public void testShouldDeleteProductButNoIngredient() throws NoDataFoundException {
		IngredientFormDto ing1 = dataFactory.createIngredien();
		soaProductDefinition.saveOrUpdate(ing1, new OptionsList());
		form = soaProductDefinition.createProduct();
		form.getIngredients().iterator().next().setSelected(true);
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		soaProductDefinition.removeProduct(form.getId());
		form = soaProductDefinition.createProduct();
		Assert.assertEquals(1, form.getIngredients().size());
	}

	@Test
	@Transactional
	public void testShouldSaveIngredientMandatory() throws NoDataFoundException {
		IngredientFormDto ing1 = dataFactory.createIngredien();
		soaProductDefinition.saveOrUpdate(ing1, new OptionsList());
		form = soaProductDefinition.createProduct();
		form.setHasIngredients(true);
		form.getIngredients().iterator().next().setSelected(true).setFacultatif(true);
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		daoProductDefinition.flush();
		daoProductDefinition.clear();
		form = soaProductDefinition.editProduct(form.getId());
		Assert.assertTrue(form.getIngredients().iterator().next().isSelected());
		Assert.assertTrue(form.getIngredients().iterator().next().getFacultatif());
		form.getIngredients().iterator().next().setSelected(true).setFacultatif(false);
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		form = soaProductDefinition.editProduct(form.getId());
		Assert.assertFalse(form.getIngredients().iterator().next().getFacultatif());
	}

	@Test
	@Transactional
	public void testShouldDeleteProductButNoCategory() throws NoDataFoundException {
		CategoryFormDto cat = dataFactory.createCategory();
		soaProductDefinition.saveOrUpdate(cat, new OptionsList());
		form = soaProductDefinition.createProduct();
		form.getCategories().iterator().next().setSelected(true);
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		soaProductDefinition.removeProduct(form.getId());
		form = soaProductDefinition.createProduct();
		Assert.assertEquals(1, form.getCategories().size());
	}

	@Test
	@Transactional
	public void testShouldCreateWithIngredient() throws NoDataFoundException {
		IngredientFormDto ing1 = dataFactory.createIngredien();
		soaProductDefinition.saveOrUpdate(ing1, new OptionsList());
		IngredientFormDto ing2 = dataFactory.createIngredien();
		soaProductDefinition.saveOrUpdate(ing2, new OptionsList());
		//
		form = soaProductDefinition.createProduct();
		Assert.assertEquals(2, form.getIngredients().size());
		Assert.assertFalse(form.getHasIngredients());
	}

	@Test
	@Transactional
	public void testShouldSaveWithIngredient() throws NoDataFoundException {
		IngredientFormDto ing1 = dataFactory.createIngredien();
		soaProductDefinition.saveOrUpdate(ing1, new OptionsList());
		//
		form = soaProductDefinition.createProduct();
		form.setHasIngredients(true);
		form.getIngredients().iterator().next().setSelected(true);
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		form = soaProductDefinition.editProduct(form.getId());
		Assert.assertTrue(form.getHasIngredients());
	}

	@Test
	@Transactional
	public void testShouldSaveWithSomeIngredientOnly() throws NoDataFoundException {
		IngredientFormDto ing1 = dataFactory.createIngredien();
		soaProductDefinition.saveOrUpdate(ing1, new OptionsList());
		IngredientFormDto ing2 = dataFactory.createIngredien();
		soaProductDefinition.saveOrUpdate(ing2, new OptionsList());
		//
		form = soaProductDefinition.createProduct();
		form.setHasIngredients(true);
		form.getIngredients().iterator().next().setSelected(true);
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		daoProductDefinition.flush();
		daoProductDefinition.clear();
		form = soaProductDefinition.editProduct(form.getId());
		int nbSelected = 0;
		for (IngredientViewDto ing : form.getIngredients()) {
			if (ing.isSelected()) {
				nbSelected++;
			}
		}
		Assert.assertEquals(1, nbSelected);
	}

	@Test
	@Transactional
	public void testShouldRefreshIngredientAfterCreate() throws NoDataFoundException {
		IngredientFormDto ing1 = dataFactory.createIngredien();
		soaProductDefinition.saveOrUpdate(ing1, new OptionsList());
		IngredientFormDto ing2 = dataFactory.createIngredien();
		soaProductDefinition.saveOrUpdate(ing2, new OptionsList());
		//
		form = soaProductDefinition.createProduct();
		IngredientViewDto selected = form.getIngredients().iterator().next();
		selected.setSelected(true);
		//
		IngredientFormDto ing3 = dataFactory.createIngredien();
		soaProductDefinition.saveOrUpdate(ing3, new OptionsList());
		//
		soaProductDefinition.refresh(form, new OptionsList().withOption(ProductFormOptions.Ingredients));
		Assert.assertEquals(3, form.getIngredients().size());
		boolean ok = false;
		for (IngredientViewDto ing : form.getIngredients()) {
			if (ing.getId().equals(selected.getId())) {
				ok = true;
				Assert.assertTrue(ing.isSelected());
			} else {
				Assert.assertFalse(ing.isSelected());
			}
		}
		Assert.assertTrue(ok);
	}

	@Test
	@Transactional
	public void testShouldRefreshIngredientAfterDelete() throws NoDataFoundException {
		IngredientFormDto ing1 = dataFactory.createIngredien();
		soaProductDefinition.saveOrUpdate(ing1, new OptionsList());
		IngredientFormDto ing2 = dataFactory.createIngredien();
		soaProductDefinition.saveOrUpdate(ing2, new OptionsList());
		//
		form = soaProductDefinition.createProduct();
		soaProductDefinition.refresh(form, new OptionsList().withOption(ProductFormOptions.Ingredients));
		Assert.assertEquals(2, form.getIngredients().size());
		//
		soaProductDefinition.removeIngredient(ing2.getId());
		soaProductDefinition.refresh(form, new OptionsList().withOption(ProductFormOptions.Ingredients));
		Assert.assertEquals(1, form.getIngredients().size());
	}

	@Test
	@Transactional
	public void testShouldSaveProduct() throws NoDataFoundException {
		ProductViewDto view = soaProductDefinition.saveOrUpdate(form, new OptionsList());
		Assert.assertNotNull(view.getId());
	}

	@Test
	@Transactional
	public void testShouldEditProductWithImgs() throws NoDataFoundException {
		form.getCms().getImgs().clear();
		form.getCms().add("IMG");
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		form = soaProductDefinition.editProduct(form.getId());
		Assert.assertEquals(1, form.getCms().getImgs().size());
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		form = soaProductDefinition.editProduct(form.getId());
		Assert.assertEquals(1, form.getCms().getImgs().size());
	}

	@Test
	@Transactional
	public void testShouldTransformJson() throws Exception {
		form.getCms().getImgs().clear();
		form.getCms().add("IMG");
		soaProductDefinition.saveOrUpdate(form, new OptionsList());
		String value = mapper.writeValueAsString(form);
		IngredientFormDto clone = mapper.readValue(value, IngredientFormDto.class);
		Assert.assertEquals(clone.getId(), form.getId());
	}
}
