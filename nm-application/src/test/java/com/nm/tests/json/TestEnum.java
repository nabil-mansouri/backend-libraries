package com.nm.tests.json;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nm.tests.ConfigurationTestApplication;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestApplication.class)
public class TestEnum {

	//
	protected Log log = LogFactory.getLog(getClass());

	@Test
	@Transactional
	public void testSaveDefault() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonTest json = new JsonTest();
		json.setTest(JsonEnumType.Test);
		String val = mapper.writeValueAsString(json);
		JsonTest test = mapper.readValue(val, JsonTest.class);
		Assert.assertEquals(json.getTest(), test.getTest());
	}
}
