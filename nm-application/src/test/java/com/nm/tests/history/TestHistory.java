package com.nm.tests.history;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.app.history.DtoHistory;
import com.nm.app.history.DtoHistoryDefault;
import com.nm.app.history.History;
import com.nm.app.history.QueryBuilderHistory;
import com.nm.app.history.SoaHistory;
import com.nm.tests.ConfigurationTestApplication;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestApplication.class)
public class TestHistory {

	//
	protected Log log = LogFactory.getLog(getClass());
	@Autowired
	private SoaHistory soaHistory;

	@Before
	public void setup() {

	}

	@Test
	@Transactional
	public void testShouldSaveHistory() throws Exception {
		DtoHistoryDefault dto = new DtoHistoryDefault();
		dto.setWhen(new Date());
		dto.setActor(new DtoHistoryTest().setName("ACTOR"));
		dto.setSubject(new DtoHistoryTest().setName("SUBJECT"));
		soaHistory.saveOrUpdate(dto, new OptionsList());
		AbstractGenericDao.get(History.class).flush();
		Collection<DtoHistory> dtos = soaHistory.fetch(QueryBuilderHistory.get(), new OptionsList(),
				new AdapterHistoryTest());
		Assert.assertEquals(1, dtos.size());
		DtoHistoryDefault firs = (DtoHistoryDefault) dtos.iterator().next();
		DtoHistoryTest ac = (DtoHistoryTest) firs.getActor();
		DtoHistoryTest su = (DtoHistoryTest) firs.getSubject();
		Assert.assertEquals("ACTOR", ac.getName());
		Assert.assertEquals("SUBJECT", su.getName());
	}

}
