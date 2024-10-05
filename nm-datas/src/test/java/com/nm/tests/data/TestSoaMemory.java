package com.nm.tests.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.config.dao.DaoAppConfig;
import com.nm.datas.SoaAppMemory;
import com.nm.datas.dtos.MemoryKeyDto;
import com.nm.utils.hibernate.NotFoundException;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestDatas.class)
public class TestSoaMemory {

	@Autowired
	private SoaAppMemory soaAppMemory;
	@Autowired
	private DaoAppConfig daoAppConfig;
	//
	protected Log log = LogFactory.getLog(getClass());

	@Test
	@Transactional
	public void testSHouldSaveMemory() throws Exception {
		soaAppMemory.setInt(new MemoryKeyDto(this, "YEAH"), 1);
		daoAppConfig.flush();
		Assert.assertEquals(1, soaAppMemory.getInt(new MemoryKeyDto(this, "YEAH")));
	}

	@Test(expected = NotFoundException.class)
	@Transactional
	public void testSHouldNotSaveMemory() throws Exception {
		soaAppMemory.setInt(new MemoryKeyDto(this, "YEAH"), 1);
		daoAppConfig.flush();
		Assert.assertEquals(1, soaAppMemory.getInt(new MemoryKeyDto(new Integer(1), "YEAH")));
	}

}
