package com.rm.buiseness.restaurants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rm.buiseness.commons.TestUrlUtils;
import com.rm.contract.commons.ContentFormDto;
import com.rm.contract.commons.beans.OptionsList;
import com.rm.contract.commons.beans.UploadViewDto;
import com.rm.contract.restaurants.beans.RestaurantFormBean;
import com.rm.dao.restaurants.DaoRestaurant;
import com.rm.model.restaurants.Restaurant;
import com.rm.soa.restaurants.SoaRestaurant;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestSOARestaurant {

	@Autowired
	private SoaRestaurant soaRestaurant;
	//
	@Autowired
	private DaoRestaurant daoRestaurant;

	//

	@Before
	public void globalSetUp() {
		assertNotNull("soaRestaurant soa can not be null.", soaRestaurant);
	}

	@Test
	@Transactional
	public void testEditAndSaveRestaurant() throws NoDataFoundException {
		//
		UploadViewDto img1View = new UploadViewDto("/test.png");
		// MUST BE DIFFERENT (HashSet)
		UploadViewDto img2View = new UploadViewDto("/test2.png");
		//
		ContentFormDto lang1Form = new ContentFormDto().setDescription("Ma description").addKeyword("a")
				.addKeyword("b").setLang("fr").setName("Nom");
		ContentFormDto lang2Form = new ContentFormDto().setDescription("My description").addKeyword("a")
				.addKeyword("b").setLang("en").setName("Name");
		//
		RestaurantFormBean prod = new RestaurantFormBean().add(lang1Form).add(lang2Form);
		prod.getImages().setImg(img1View).add(img1View).add(img2View);

		// TEST
		RestaurantFormBean view = soaRestaurant.saveOrUpdate(prod, new OptionsList("fr"));
		assertNotNull(view.getId());
		Restaurant fetched = daoRestaurant.loadById(view.getId());
		assertEquals("Ma description", fetched.getContent().getDescription("fr"));
		assertEquals("/test.png", fetched.getImage());
		assertEquals(2, fetched.getImage().getImages().size());
		assertEquals(2, fetched.getContent().getKeyword("fr").size());
		assertEquals("a", fetched.getContent().getKeyword("fr").get(0));
		assertEquals("Nom", fetched.getContent().getName("fr"));
		assertEquals(2, fetched.getContent().getNameLangage().size());
		// TEST EDIT
		RestaurantFormBean form = soaRestaurant.editRestaurant(view.getId());
		assertEquals("Ma description", form.getCms().getContents().get(0).getDescription());
		assertEquals("/test.png", form.getImages().getImg().getRelativeURL());
		assertEquals(2, form.getImages().getImgs().size());
		assertEquals(2, form.getCms().getContents().get(0).getKeywords().size());
		assertEquals("a", form.getCms().getContents().get(0).getKeywords().get(0).getText());
		assertEquals("Nom", form.getCms().getContents().get(0).getName());
		assertEquals(2, form.getCms().getContents().size());
	}

}
