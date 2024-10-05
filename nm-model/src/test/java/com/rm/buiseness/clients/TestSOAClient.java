package com.rm.buiseness.clients;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rm.app.geo.AddressFormDto;
import com.rm.app.geo.AddressFormComponentsBean;
import com.rm.buiseness.commons.TestUrlUtils;
import com.rm.contract.clients.beans.ClientForm;
import com.rm.dao.clients.DaoClient;
import com.rm.soa.clients.SoaClient;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestSOAClient {

	@Autowired
	private SoaClient soaClient;
	//
	@Autowired
	private DaoClient daoClient;

	//

	@Test
	@Transactional
	public void testEditAndSaveClient() throws NoDataFoundException {
		// TEST
		ClientForm clientForm = new ClientForm();
		clientForm.setBirthDate(new Date());
		clientForm.setEmail("nab@nab.com");
		clientForm.setFirstname("ddd");
		clientForm.setName("name");
		clientForm.setPhone("phone");
		{
			AddressFormDto address = new AddressFormDto();
			address.setComplement("comple");
			address.setDetails(new JSONObject());
			address.setGeocode("Geocode");
			{
				AddressFormComponentsBean comp = new AddressFormComponentsBean();
				comp.setCountry("FR");
				comp.setLatitude(4d);
				comp.setLocality("loc");
				comp.setLongitude(4d);
				comp.setStreet("street");
				comp.setPostal("71200");
				address.setComponents(comp);
			}
			clientForm.setAddress(address);
		}
		//
		ClientForm view = soaClient.save(clientForm);
		assertNotNull(view.getId());
		// TEST EDIT
		ClientForm form = soaClient.edit(view.getId());
		form.getAddress().setGeocode("fdsfdsf");
		soaClient.save(form);
		//
		soaClient.delete(form.getId());
		//
		daoClient.flush();
	}



}
