package com.nm.tests.dictionnary;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.dictionnary.SoaDictionnary;
import com.nm.dictionnary.constants.EnumDictionnaryOperation;
import com.nm.dictionnary.constants.EnumDictionnaryState;
import com.nm.dictionnary.constants.EnumDictionnaryType;
import com.nm.dictionnary.constants.OptionsDictionnary;
import com.nm.dictionnary.daos.DaoDictionnary;
import com.nm.dictionnary.daos.QueryBuilderDictionnary;
import com.nm.dictionnary.daos.QueryBuilderDictionnaryEntity;
import com.nm.dictionnary.daos.QueryBuilderDictionnaryEntry;
import com.nm.dictionnary.daos.QueryBuilderDictionnaryValue;
import com.nm.dictionnary.dtos.DtoDictionnaryEntity;
import com.nm.dictionnary.dtos.DtoDictionnaryEntityDefault;
import com.nm.dictionnary.dtos.DtoDictionnaryEntry;
import com.nm.dictionnary.dtos.DtoDictionnaryEntryDefault;
import com.nm.dictionnary.dtos.DtoDictionnaryValue;
import com.nm.dictionnary.dtos.DtoDictionnaryValueDefault;
import com.nm.dictionnary.models.Dictionnary;
import com.nm.dictionnary.models.DictionnaryEntity;
import com.nm.dictionnary.models.DictionnaryEntry;
import com.nm.dictionnary.models.DictionnaryException;
import com.nm.dictionnary.models.DictionnaryValue;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestDictionnary.class)
public class TestDictionnary {

	@Autowired
	private SoaDictionnary soaDictionnary;
	@Autowired
	private DaoDictionnary dao;
	private IGenericDao<DictionnaryEntry, Long> daoEntry;
	//
	private OptionsList options = new OptionsList();
	protected Log log = LogFactory.getLog(getClass());

	@org.junit.Before
	public void setup() {
		daoEntry = AbstractGenericDao.get(DictionnaryEntry.class);
	}

	@Test
	@Transactional
	public void testShouldCreateDictionnary() throws Exception {
		Dictionnary dic = dao.getOrCreate(EnumDictionnaryDomainTest.TestDomain);
		dao.flush();
		Assert.assertNotNull(dic.getId());
		Dictionnary dic2 = dao.getOrCreate(EnumDictionnaryDomainTest.TestDomain);
		dao.flush();
		Assert.assertEquals(dic.getId(), dic2.getId());
	}

	@Test
	@Transactional
	public void testShouldCreateDictionnaries() throws Exception {
		Dictionnary dic = dao.getOrCreate(EnumDictionnaryDomainTest.TestDomain);
		dao.flush();
		Assert.assertNotNull(dic.getId());
		Dictionnary dic2 = dao.getOrCreate(EnumDictionnaryDomainTest.TestDomain2);
		dao.flush();
		Assert.assertNotEquals(dic.getId(), dic2.getId());
	}

	@Test
	@Transactional
	public void testShouldCreateEntrySingle() throws Exception {
		DtoDictionnaryEntryDefault entry = new DtoDictionnaryEntryDefault();
		entry.setDomain(EnumDictionnaryDomainTest.TestDomain2);
		entry.setState(EnumDictionnaryState.Approved);
		entry.setType(EnumDictionnaryType.Single);
		entry.setKey("Sport");
		//
		DtoDictionnaryValueDefault value = new DtoDictionnaryValueDefault();
		value.setOperation(EnumDictionnaryOperation.Add);
		value.setState(EnumDictionnaryState.Approved);
		value.setValue("Football");
		entry.getValues().add(value);
		soaDictionnary.saveOrUpdate(entry, options);
		dao.flush();
		Assert.assertNotNull(entry.getId());
		Assert.assertEquals(1, daoEntry.get(entry.getId()).getValues().size());
	}

	@Test(expected = ConstraintViolationException.class)
	@Transactional
	public void testShouldNotCreateEntrySingleWithMultiValues() throws Exception {
		DtoDictionnaryEntryDefault entry = new DtoDictionnaryEntryDefault();
		entry.setDomain(EnumDictionnaryDomainTest.TestDomain2);
		entry.setState(EnumDictionnaryState.Approved);
		entry.setType(EnumDictionnaryType.Single);
		entry.setKey("Sport");
		//
		for (int i = 0; i < 2; i++) {
			DtoDictionnaryValueDefault value = new DtoDictionnaryValueDefault();
			value.setOperation(EnumDictionnaryOperation.Add);
			value.setState(EnumDictionnaryState.Approved);
			value.setValue("Football" + i);
			entry.getValues().add(value);
		}
		soaDictionnary.saveOrUpdate(entry, options);
		dao.flush();
		dao.clear();
	}

	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	public void testShouldEntryKeyOnce() throws Exception {
		DtoDictionnaryEntryDefault entry = new DtoDictionnaryEntryDefault();
		entry.setDomain(EnumDictionnaryDomainTest.TestDomain2);
		entry.setState(EnumDictionnaryState.Approved);
		entry.setType(EnumDictionnaryType.Multi);
		entry.setKey("Sport");
		//
		for (int i = 0; i < 2; i++) {
			DtoDictionnaryValueDefault value = new DtoDictionnaryValueDefault();
			value.setOperation(EnumDictionnaryOperation.Add);
			value.setState(EnumDictionnaryState.Approved);
			value.setValue("Football" + i);
			entry.getValues().add(value);
		}
		soaDictionnary.saveOrUpdate(entry, options);
		dao.flush();
		dao.clear();
		entry = new DtoDictionnaryEntryDefault();
		entry.setDomain(EnumDictionnaryDomainTest.TestDomain2);
		entry.setState(EnumDictionnaryState.Approved);
		entry.setType(EnumDictionnaryType.Multi);
		entry.setKey("Sport");
		//
		for (int i = 0; i < 2; i++) {
			DtoDictionnaryValueDefault value = new DtoDictionnaryValueDefault();
			value.setOperation(EnumDictionnaryOperation.Add);
			value.setState(EnumDictionnaryState.Approved);
			value.setValue("Football" + i);
			entry.getValues().add(value);
		}
		soaDictionnary.saveOrUpdate(entry, options);
		dao.flush();
		dao.clear();
	}

	@Test()
	@Transactional
	public void testShouldEntryKeyOnceSafe() throws Exception {
		DtoDictionnaryEntryDefault entry = new DtoDictionnaryEntryDefault();
		entry.setDomain(EnumDictionnaryDomainTest.TestDomain2);
		entry.setState(EnumDictionnaryState.Approved);
		entry.setType(EnumDictionnaryType.Multi);
		entry.setKey("Sport");
		//
		for (int i = 0; i < 2; i++) {
			DtoDictionnaryValueDefault value = new DtoDictionnaryValueDefault();
			value.setOperation(EnumDictionnaryOperation.Add);
			value.setState(EnumDictionnaryState.Approved);
			value.setValue("Football" + i);
			entry.getValues().add(value);
		}
		soaDictionnary.saveOrUpdate(entry, options.withOption(OptionsDictionnary.DictionnarySafe));
		dao.flush();
		dao.clear();
		entry = new DtoDictionnaryEntryDefault();
		entry.setDomain(EnumDictionnaryDomainTest.TestDomain2);
		entry.setState(EnumDictionnaryState.Approved);
		entry.setType(EnumDictionnaryType.Multi);
		entry.setKey("Sport");
		//
		for (int i = 0; i < 2; i++) {
			DtoDictionnaryValueDefault value = new DtoDictionnaryValueDefault();
			value.setOperation(EnumDictionnaryOperation.Add);
			value.setState(EnumDictionnaryState.Approved);
			value.setValue("Football" + i);
			entry.getValues().add(value);
		}
		soaDictionnary.saveOrUpdate(entry, options.withOption(OptionsDictionnary.DictionnarySafe));
		dao.flush();
		dao.clear();
	}

	private void createTag(int year, EnumDictionnaryType t) throws Exception {
		DtoDictionnaryEntryDefault entry = new DtoDictionnaryEntryDefault();
		entry.setDomain(EnumDictionnaryDomainTest.TestDomain);
		entry.setState(EnumDictionnaryState.Approved);
		entry.setType(t);
		entry.setKey("TEST");
		//
		DtoDictionnaryValueDefault value = new DtoDictionnaryValueDefault();
		value.setOperation(EnumDictionnaryOperation.Add);
		value.setState(EnumDictionnaryState.Approved);
		value.setValue(year + "");
		entry.getValues().add(value);
		soaDictionnary.saveOrUpdate(entry, new OptionsList().withOption(OptionsDictionnary.DictionnarySafe));
		dao.flush();
	}

	@Test()
	@Transactional
	public void testShouldCreateTagOnlyOnceSafe() throws Exception {
		createTag(2016, EnumDictionnaryType.Multi);
		Collection<DtoDictionnaryValue> values = soaDictionnary.fetch(QueryBuilderDictionnaryValue.get()
				.withDictionaryEntry(QueryBuilderDictionnaryEntry.get().withKey("TEST"))
				.withValueStartNormalize(2016 + ""), new OptionsList());
		Assert.assertEquals(1, values.size());
		createTag(2016, EnumDictionnaryType.Multi);
		values = soaDictionnary.fetch(QueryBuilderDictionnaryValue.get()
				.withDictionaryEntry(QueryBuilderDictionnaryEntry.get().withKey("TEST"))
				.withValueStartNormalize(2016 + ""), new OptionsList());
		Assert.assertEquals(1, values.size());
	}

	@Test()
	@Transactional
	public void testShouldCreateTagOnlyOnceSafeSingleMode() throws Exception {
		createTag(2016, EnumDictionnaryType.Single);
		Collection<DtoDictionnaryValue> values = soaDictionnary.fetch(QueryBuilderDictionnaryValue.get()
				.withDictionaryEntry(QueryBuilderDictionnaryEntry.get().withKey("TEST"))
				.withValueStartNormalize(2016 + ""), new OptionsList());
		Assert.assertEquals(1, values.size());
		createTag(2016, EnumDictionnaryType.Single);
		values = soaDictionnary.fetch(QueryBuilderDictionnaryValue.get()
				.withDictionaryEntry(QueryBuilderDictionnaryEntry.get().withKey("TEST"))
				.withValueStartNormalize(2016 + ""), new OptionsList());
		Assert.assertEquals(1, values.size());
	}

	@Test(expected = DictionnaryException.class)
	@Transactional
	public void testShouldNotCreateKeyTwice() throws Exception {
		DtoDictionnaryEntryDefault entry = new DtoDictionnaryEntryDefault();
		entry.setDomain(EnumDictionnaryDomainTest.TestDomain2);
		entry.setState(EnumDictionnaryState.Approved);
		entry.setType(EnumDictionnaryType.Single);
		entry.setKey("Sport");
		soaDictionnary.saveOrUpdate(entry, options);
		//
		entry = new DtoDictionnaryEntryDefault();
		entry.setDomain(EnumDictionnaryDomainTest.TestDomain2);
		entry.setState(EnumDictionnaryState.Approved);
		entry.setType(EnumDictionnaryType.Single);
		entry.setKey("Sport");
		soaDictionnary.saveOrUpdate(entry, options);
	}

	private DtoDictionnaryEntryDefault buildMulti() {
		return buildMulti("Sport", "Football");
	}

	private DtoDictionnaryEntryDefault buildMulti(String key) {
		return buildMulti(key, "Football");
	}

	private DtoDictionnaryEntryDefault buildMulti(String key, String valueV) {
		DtoDictionnaryEntryDefault entry = new DtoDictionnaryEntryDefault();
		entry.setDomain(EnumDictionnaryDomainTest.TestDomain2);
		entry.setState(EnumDictionnaryState.Approved);
		entry.setType(EnumDictionnaryType.Multi);
		entry.setKey(key);
		//
		{
			DtoDictionnaryValueDefault value = new DtoDictionnaryValueDefault();
			value.setOperation(EnumDictionnaryOperation.Add);
			value.setState(EnumDictionnaryState.Approved);
			value.setValue(valueV);
			entry.getValues().add(value);
		}
		{
			DtoDictionnaryValueDefault value = new DtoDictionnaryValueDefault();
			value.setOperation(EnumDictionnaryOperation.Add);
			value.setState(EnumDictionnaryState.Approved);
			value.setValue("Basket");
			entry.getValues().add(value);
		}
		return entry;
	}

	private DtoDictionnaryEntryDefault buildOnTag(String key, String valueV) {
		DtoDictionnaryEntryDefault entry = new DtoDictionnaryEntryDefault();
		entry.setDomain(EnumDictionnaryDomainTest.TestDomain2);
		entry.setState(EnumDictionnaryState.Approved);
		entry.setType(EnumDictionnaryType.Multi);
		entry.setKey(key);
		//
		{
			DtoDictionnaryValueDefault value = new DtoDictionnaryValueDefault();
			value.setOperation(EnumDictionnaryOperation.Add);
			value.setState(EnumDictionnaryState.Approved);
			value.setValue(valueV);
			entry.getValues().add(value);
		}
		return entry;
	}

	private DtoDictionnaryEntryDefault buildOnTag(String key, int valueV) {
		DtoDictionnaryEntryDefault entry = new DtoDictionnaryEntryDefault();
		entry.setDomain(EnumDictionnaryDomainTest.TestDomain2);
		entry.setState(EnumDictionnaryState.Approved);
		entry.setType(EnumDictionnaryType.Multi);
		entry.setKey(key);
		//
		{
			DtoDictionnaryValueDefault value = new DtoDictionnaryValueDefault();
			value.setOperation(EnumDictionnaryOperation.Add);
			value.setState(EnumDictionnaryState.Approved);
			value.setValueInteger(valueV);
			entry.getValues().add(value);
		}
		return entry;
	}

	@Test
	@Transactional
	public void testShouldCreateEntryMultiple() throws Exception {
		DtoDictionnaryEntryDefault entry = buildMulti();
		soaDictionnary.saveOrUpdate(entry, options);
		dao.flush();
		Assert.assertNotNull(entry.getId());
		Assert.assertEquals(2, daoEntry.get(entry.getId()).getValues().size());
	}

	@Test
	@Transactional
	public void testShouldRemoveEntryMultiple() throws Exception {
		DtoDictionnaryEntryDefault entry = buildMulti();
		soaDictionnary.saveOrUpdate(entry, options);
		dao.flush();
		Assert.assertEquals(2, daoEntry.get(entry.getId()).getValues().size());
		Assert.assertEquals(2, entry.getValues().size());
		for (DtoDictionnaryValueDefault v : entry.getValues()) {
			Assert.assertNotNull(v.getIdValue());
			if (v.getValue().contains("Ba")) {
				v.setOperation(EnumDictionnaryOperation.Remove);
			}
		}
		soaDictionnary.saveOrUpdate(entry, options);
		dao.flush();
		Assert.assertEquals(1, daoEntry.get(entry.getId()).getValues().size());
		Assert.assertEquals(1, entry.getValues().size());
		for (DtoDictionnaryValueDefault v : entry.getValues()) {
			Assert.assertEquals("Football", v.getValue());
		}
	}

	@Test
	@Transactional
	public void testShouldUpdateEntryMultiple() throws Exception {
		DtoDictionnaryEntryDefault entry = buildMulti();
		soaDictionnary.saveOrUpdate(entry, options);
		dao.flush();
		Assert.assertEquals(2, daoEntry.get(entry.getId()).getValues().size());
		Assert.assertEquals(2, entry.getValues().size());
		for (DtoDictionnaryValueDefault v : entry.getValues()) {
			Assert.assertNotNull(v.getIdValue());
			if (v.getValue().contains("Ba")) {
				v.setOperation(EnumDictionnaryOperation.Add);
				v.setValue("Football1");
			}
		}
		soaDictionnary.saveOrUpdate(entry, options);
		dao.flush();
		Assert.assertEquals(2, daoEntry.get(entry.getId()).getValues().size());
		Assert.assertEquals(2, entry.getValues().size());
		for (DictionnaryValue v : daoEntry.get(entry.getId()).getValues()) {
			Assert.assertTrue(v.getValue().startsWith("Football"));
		}
	}

	@Test
	@Transactional
	public void testShouldFetch() throws Exception {
		DtoDictionnaryEntryDefault entry = buildMulti("Sport1");
		soaDictionnary.saveOrUpdate(entry, options);
		entry = buildMulti("Sport2");
		soaDictionnary.saveOrUpdate(entry, options);
		dao.flush();
		Collection<DtoDictionnaryEntry> dtos = soaDictionnary.fetch(QueryBuilderDictionnaryEntry.get().withDictionary(
				QueryBuilderDictionnary.get().withDomain(EnumDictionnaryDomainTest.TestDomain2)), options);
		Assert.assertEquals(2, dtos.size());
	}

	@Test
	@Transactional
	public void testShouldFetchValues() throws Exception {
		DtoDictionnaryEntryDefault entry = buildMulti("Sport1");
		soaDictionnary.saveOrUpdate(entry, options);
		entry = buildMulti("Sport2");
		soaDictionnary.saveOrUpdate(entry, options);
		dao.flush();
		QueryBuilderDictionnaryValue query = QueryBuilderDictionnaryValue.get().withState(EnumDictionnaryState.Approved)
				.withDictionaryEntry(QueryBuilderDictionnaryEntry.get().withKey("Sport1"));
		Collection<DtoDictionnaryValue> dtos = soaDictionnary.fetch(query, options);
		Assert.assertEquals(2, dtos.size());
		//
		query = QueryBuilderDictionnaryValue.get().withState(EnumDictionnaryState.Approved).withValueStart("Foot");
		dtos = soaDictionnary.fetch(query, options);
		Assert.assertEquals(2, dtos.size());
	}

	@Test
	@Transactional
	public void testShouldAddValueToEntityOnlyOnce() throws Exception {
		DtoDictionnaryEntryDefault entry = buildOnTag("Sport", "Football");
		soaDictionnary.saveOrUpdate(entry, options);
		//
		dao.flush();
		// CREATE ENTITY
		DtoDictionnaryEntityDefault dtoEntity = new DtoDictionnaryEntityDefault();
		dtoEntity.setDescription("EVENT");
		for (int i = 0; i < 3; i++) {
			dtoEntity.add(entry.getValues().iterator().next());
		}
		soaDictionnary.saveOrUpdate(dtoEntity, options);
		dao.flush();
		DictionnaryEntity simpl = AbstractGenericDao.get(DictionnaryEntity.class)
				.get(dtoEntity.getDictionnaryEntityId());
		Assert.assertEquals(1, simpl.getValues().size());
		// RE ATTEMPT TO ADD
		dtoEntity = (DtoDictionnaryEntityDefault) soaDictionnary
				.fetch(QueryBuilderDictionnaryEntity.get().withId(dtoEntity.getDictionnaryEntityId()), options)
				.iterator().next();
		for (int i = 0; i < 3; i++) {
			dtoEntity.getValues().add(entry.getValues().iterator().next());
		}
		soaDictionnary.saveOrUpdate(dtoEntity, options);
		dao.flush();
		simpl = AbstractGenericDao.get(DictionnaryEntity.class).get(dtoEntity.getDictionnaryEntityId());
		Assert.assertEquals(1, simpl.getValues().size());
	}

	@Test
	@Transactional
	public void testShouldFindEntityByTagCount() throws Exception {
		DtoDictionnaryEntryDefault entry1 = buildOnTag("Sport", "Football");
		soaDictionnary.saveOrUpdate(entry1, options);
		DtoDictionnaryEntryDefault entry2 = buildOnTag("Categorie", "Premium");
		soaDictionnary.saveOrUpdate(entry2, options);
		//
		dao.flush();
		// CREATE ENTITY
		DtoDictionnaryEntityDefault dtoEntity = new DtoDictionnaryEntityDefault();
		dtoEntity.setDescription("EVENT");
		dtoEntity.add(entry1.getValues().iterator().next());
		dtoEntity.add(entry2.getValues().iterator().next());
		soaDictionnary.saveOrUpdate(dtoEntity, options);
		dao.flush();
		int fetched = soaDictionnary.fetch(QueryBuilderDictionnaryEntity.get()//
				.withValueHaving(2,
						QueryBuilderDictionnaryValue.get()//
								.withDisjunction()//
								.withDisNormalizedEq("Sport", "Football")//
								.withDisNormalizedEq("Categorie", "Premium")),
				options).size();
		Assert.assertEquals(1, fetched);
		//
		fetched = soaDictionnary.fetch(QueryBuilderDictionnaryEntity.get()//
				.withValueHaving(2,
						QueryBuilderDictionnaryValue.get()//
								.withDisjunction()//
								.withDisNormalizedEq("Sport", "Football")//
								.withDisNormalizedEq("Categorie", "BAD")),
				options).size();
		Assert.assertEquals(0, fetched);
		//
		fetched = soaDictionnary.fetch(QueryBuilderDictionnaryEntity.get()//
				.withValueHaving(1,
						QueryBuilderDictionnaryValue.get()//
								.withDisjunction()//
								.withDisNormalizedEq("Sport", "Football")),
				options).size();
		Assert.assertEquals(1, fetched);
	}

	@Test
	@Transactional
	public void testShouldFindEntityByTagCountWithTagNumber() throws Exception {
		DtoDictionnaryEntryDefault entry1 = buildOnTag("Sport", 1);
		soaDictionnary.saveOrUpdate(entry1, options);
		DtoDictionnaryEntryDefault entry2 = buildOnTag("Categorie", 2);
		soaDictionnary.saveOrUpdate(entry2, options);
		//
		dao.flush();
		// CREATE ENTITY
		DtoDictionnaryEntityDefault dtoEntity = new DtoDictionnaryEntityDefault();
		dtoEntity.setDescription("EVENT");
		dtoEntity.add(entry1.getValues().iterator().next());
		dtoEntity.add(entry2.getValues().iterator().next());
		soaDictionnary.saveOrUpdate(dtoEntity, options);
		dao.flush();
		int fetched = soaDictionnary.fetch(QueryBuilderDictionnaryEntity.get()//
				.withValueHaving(2,
						QueryBuilderDictionnaryValue.get()//
								.withDisjunction()//
								.withDisNormalizedEq("Sport", 1)//
								.withDisNormalizedEq("Categorie", 2)),
				options).size();
		Assert.assertEquals(1, fetched);
		// FETCH WITH BAD VALUE
		fetched = soaDictionnary.fetch(QueryBuilderDictionnaryEntity.get()//
				.withValueHaving(2,
						QueryBuilderDictionnaryValue.get()//
								.withDisjunction()//
								.withDisNormalizedEq("Sport", 1)//
								.withDisNormalizedEq("Categorie", 4)),
				options).size();
		Assert.assertEquals(0, fetched);
		// FETCH WITH HALF OF GOOD VALUES
		fetched = soaDictionnary.fetch(QueryBuilderDictionnaryEntity.get()//
				.withValueHaving(1,
						QueryBuilderDictionnaryValue.get()//
								.withDisjunction()//
								.withDisNormalizedEq("Sport", 1)),
				options).size();
		Assert.assertEquals(1, fetched);
	}

	@Test
	@Transactional
	public void testShouldFetchValuesNormalized() throws Exception {
		DtoDictionnaryEntryDefault entry = buildMulti("Sport1", "KEWI");
		soaDictionnary.saveOrUpdate(entry, options);
		entry = buildMulti("Sport2");
		soaDictionnary.saveOrUpdate(entry, options);
		dao.flush();
		//
		QueryBuilderDictionnaryValue query = QueryBuilderDictionnaryValue.get().withState(EnumDictionnaryState.Approved)
				.withValueStartNormalize("ke");
		Collection<DtoDictionnaryValue> dtos = soaDictionnary.fetch(query, options);
		Assert.assertEquals(1, dtos.size());
	}

	@Test
	@Transactional
	public void testShouldSaveDictionnaryEntity() throws Exception {
		DtoDictionnaryEntryDefault entry1 = buildMulti("Sport1");
		soaDictionnary.saveOrUpdate(entry1, options);
		dao.flush();
		DtoDictionnaryEntityDefault dtoEntity = new DtoDictionnaryEntityDefault();
		dtoEntity.setDescription("EVENT");
		dtoEntity.add(entry1.getValues().iterator().next());
		soaDictionnary.saveOrUpdate(dtoEntity, options);
		dao.flush();
		Assert.assertNotNull(dtoEntity.getDictionnaryEntityId());
		Assert.assertEquals(1, dtoEntity.getValues().size());
	}

	@Test
	@Transactional
	public void testShouldFetchDictionnaryEntity() throws Exception {
		DtoDictionnaryEntryDefault entry1 = buildMulti("Sport1");
		soaDictionnary.saveOrUpdate(entry1, options);
		DtoDictionnaryEntryDefault entry2 = buildMulti("Sport2");
		soaDictionnary.saveOrUpdate(entry2, options);
		dao.flush();
		//
		DtoDictionnaryEntityDefault dtoEntity = new DtoDictionnaryEntityDefault();
		dtoEntity.setDescription("EVENT");
		for (DtoDictionnaryValueDefault v : entry1.getValues()) {
			if (v.getValue().contains("Ba")) {
				dtoEntity.add(v);
			}
		}
		soaDictionnary.saveOrUpdate(dtoEntity, options);
		dao.flush();
		//
		QueryBuilderDictionnaryValue query = QueryBuilderDictionnaryValue.get().withState(EnumDictionnaryState.Approved)
				.withDictionaryEntry(QueryBuilderDictionnaryEntry.get().withKey("Sport1")).withValueStart("Ba");
		QueryBuilderDictionnaryEntity queryV = QueryBuilderDictionnaryEntity.get().withValue(query);
		Collection<DtoDictionnaryEntity> dtos = soaDictionnary.fetch(queryV, options);
		Assert.assertEquals(1, dtos.size());
		//
	}

	@Test
	@Transactional
	public void testShouldFetchEmpty() throws Exception {
		DtoDictionnaryEntryDefault entry = buildMulti("Sport1");
		soaDictionnary.saveOrUpdate(entry, options);
		entry = buildMulti("Sport2");
		soaDictionnary.saveOrUpdate(entry, options);
		dao.flush();
		Collection<DtoDictionnaryEntry> dtos = soaDictionnary.fetch(QueryBuilderDictionnaryEntry.get().withDictionary(
				QueryBuilderDictionnary.get().withDomain(EnumDictionnaryDomainTest.TestDomain)), options);
		Assert.assertEquals(0, dtos.size());
	}
}
