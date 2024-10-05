package com.rm.buiseness.discounts;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rm.buiseness.commons.TestUrlUtils;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestDaoDiscount {

	@Before
	public void globalSetUp() throws NoDataFoundException {

	}

	@Test
	@Transactional
	public void test() throws NoDataFoundException {

	}

}
