package com.nm.tests.geo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.nm.geo.SoaGeo;
import com.nm.geo.beans.GeoLocationPoint;
import com.nm.geo.daos.DaoAddress;
import com.nm.geo.daos.QueryAddressBuilder;
import com.nm.geo.dtos.AddressDtoImpl;
import com.nm.geo.providers.AddressProvider;
import com.nm.geo.providers.AddressProviderGoogle;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestGeo.class)
public class TestGeoProvider {
	@Autowired
	private SoaGeo soaGeo;
	@Autowired
	private DaoAddress dao;

	@Before
	public void setup() {
	}

	@Test
	@Transactional
	public void testShouldGoogleGeocode() throws Exception {
		AddressProvider provider = new AddressProviderGoogle();
		AddressDtoImpl dt = (AddressDtoImpl) provider.geocode("3 avenue albert einstein 6910 villeurbanne",
				new OptionsList());
		System.out.println(dt);
		Assert.assertNotNull(dt.getDetails());
		Assert.assertEquals("France", dt.getComponents().getCountry());
		Assert.assertEquals("Villeurbanne", dt.getComponents().getLocality());
		Assert.assertEquals("69100", dt.getComponents().getPostal());
		Assert.assertNotNull(dt.getComponents().getStreet());
		Assert.assertNotNull(dt.getComponents().getLatitude());
		Assert.assertNotNull(dt.getComponents().getLongitude());
	}

	@Test
	@Transactional
	public void testShouldSaveAddress() throws Exception {
		AddressProvider provider = new AddressProviderGoogle();
		AddressDtoImpl dt = (AddressDtoImpl) provider.geocode("3 avenue albert einstein 6910 villeurbanne",
				new OptionsList());
		dt.setName("MINE");
		soaGeo.saveOrUpdate(dt, new OptionsList());
		dao.flush();
		Assert.assertNotNull(dao.get(dt.getId()));
	}

	@Test
	@Transactional
	public void testShouldSaveAddressWithSubject() throws Exception {
		AddressProvider provider = new AddressProviderGoogle();
		AddressDtoImpl dt = (AddressDtoImpl) provider.geocode("3 avenue albert einstein 6910 villeurbanne",
				new OptionsList());
		dt.setName("MINE");
		soaGeo.saveOrUpdate(dt, new OptionsList());
		dao.flush();
		Assert.assertNotNull(dao.get(dt.getId()));
	}

	@Test
	@Transactional
	public void testShouldFIndClosestFromTheSamePoint() throws Exception {
		AddressProvider provider = new AddressProviderGoogle();
		AddressDtoImpl dt = (AddressDtoImpl) provider.geocode("3 avenue albert einstein 6910 villeurbanne",
				new OptionsList());
		dt.setName("MINE");
		soaGeo.saveOrUpdate(dt, new OptionsList());
		dao.flush();
		QueryAddressBuilder q = QueryAddressBuilder.get().withClosestInDegree(
				GeoLocationPoint.fromDegrees(dt.getComponents().getLatitude(), dt.getComponents().getLongitude()), 1);
		Assert.assertEquals(1, dao.find(q).size());
	}

	@Test
	@Transactional
	public void testShouldFIndClosestFromVilleurbanneToLyon() throws Exception {
		AddressProvider provider = new AddressProviderGoogle();
		AddressDtoImpl dt1 = (AddressDtoImpl) provider.geocode("3 avenue albert einstein 6910 villeurbanne",
				new OptionsList());
		Assert.assertNotNull(dt1.getComponents().getStreet());
		dt1.setName("MINE");
		soaGeo.saveOrUpdate(dt1, new OptionsList());
		dao.flush();
		//
		AddressDtoImpl dt2 = (AddressDtoImpl) provider.geocode("1 Place du 8 Mai 1945 Lyon 8", new OptionsList());
		Assert.assertNotNull(dt2.getComponents().getStreet());
		QueryAddressBuilder q = QueryAddressBuilder.get().withClosestInDegree(
				GeoLocationPoint.fromDegrees(dt2.getComponents().getLatitude(), dt2.getComponents().getLongitude()),
				10);
		Assert.assertEquals(1, dao.find(q).size());
	}

	@Test
	@Transactional
	public void testShouldNotFIndClosestFromVilleurbanneToCreusot() throws Exception {
		AddressProvider provider = new AddressProviderGoogle();
		AddressDtoImpl dt1 = (AddressDtoImpl) provider.geocode("3 avenue albert einstein 6910 villeurbanne",
				new OptionsList());
		Assert.assertNotNull(dt1.getComponents().getStreet());
		dt1.setName("MINE");
		soaGeo.saveOrUpdate(dt1, new OptionsList());
		dao.flush();
		//
		AddressDtoImpl dt2 = (AddressDtoImpl) provider.geocode("3 avenue de la republique 71200 Le Creusot",
				new OptionsList());
		Assert.assertNotNull(dt2.getComponents().getStreet());
		QueryAddressBuilder q = QueryAddressBuilder.get().withClosestInDegree(
				GeoLocationPoint.fromDegrees(dt2.getComponents().getLatitude(), dt2.getComponents().getLongitude()),
				10);
		Assert.assertEquals(0, dao.find(q).size());
	}

	@Test
	@Transactional
	public void testShouldFIndClosestFromVilleurbanneToCreusot() throws Exception {
		AddressProvider provider = new AddressProviderGoogle();
		AddressDtoImpl dt1 = (AddressDtoImpl) provider.geocode("3 avenue albert einstein 6910 villeurbanne",
				new OptionsList());
		Assert.assertNotNull(dt1.getComponents().getStreet());
		dt1.setName("MINE");
		soaGeo.saveOrUpdate(dt1, new OptionsList());
		dao.flush();
		//
		AddressDtoImpl dt2 = (AddressDtoImpl) provider.geocode("3 avenue de la republique 71200 Le Creusot",
				new OptionsList());
		Assert.assertNotNull(dt2.getComponents().getStreet());
		QueryAddressBuilder q = QueryAddressBuilder.get().withClosestInDegree(
				GeoLocationPoint.fromDegrees(dt2.getComponents().getLatitude(), dt2.getComponents().getLongitude()),
				150);
		Assert.assertEquals(1, dao.find(q).size());
	}

}
