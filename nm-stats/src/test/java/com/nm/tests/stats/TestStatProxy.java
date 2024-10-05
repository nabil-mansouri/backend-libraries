package com.nm.tests.stats;

import java.util.Date;
import java.util.Map;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.nm.stats.jdbc.MeasureAggregatorReplace;
import com.nm.stats.jdbc.MeasureAggregatorRetain;
import com.nm.stats.jdbc.StatMeasureDateMonth;
import com.nm.stats.jdbc.StatMeasureLong;
import com.nm.stats.jdbc.StatValuesDefault;
import com.nm.stats.jdbc.StatValuesProxy;
import com.nm.stats.jdbc.keys.GenericJdbcRowList;
import com.nm.tests.stats.QueryBuilder.Select;
//bitbucket.org/nabil_mansouri/my-libraries
import com.nm.utils.dates.DateUtilsExt;
import com.nm.utils.jdbc.GenericJdbcRow;
import com.nm.utils.jdbc.ISelect;

/**
 * 
 * @author Nabil
 * 
 */
// @RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
// @ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestStatProxy {

	@Before
	public void globalSetUp() {
	}

	@Test
	// @Transactional
	public void testShouldSplitByApporteur() throws Exception {
		GenericJdbcRowList rows = new GenericJdbcRowList();
		long total = 0l;
		long totalP = 0l;
		{
			for (int j = 1; j < 5; j++) {
				for (int i = 0; i < 5; i++) {
					GenericJdbcRow row = rows.create();
					row.put(Select.ApporteurId, j);
					row.put(Select.Age, 80 + i);
					row.put(Select.ActivMonth, DateUtilsExt.from((i + 1), 2015));
					row.put(Select.Count, i + j);
					total += (i + j);
					if (j == 1) {
						totalP += (i + j);
					}
				}
			}
		}
		System.out.println("------------------------------");
		System.out.println(rows);
		//
		StatValuesProxy config = new StatValuesProxy().with(new StatMeasureLong(Select.ApporteurId));
		config.with(new StatMeasureLong(Select.Age));
		config.with(new StatMeasureDateMonth(Select.ActivMonth));
		config.withValue(Select.Count);
		for (GenericJdbcRow ro : rows) {
			config.pushValue(ro);
		}
		System.out.println("------------------------------");
		System.out.println(config.getInner());
		Assert.assertEquals(total, config.getInner().get(StatValuesDefault.class).getTotal().longValue());
		//
		GenericJdbcRowList rowsSub = config.toRowList().getRowsHaving(Select.ApporteurId, Long.class, 1l);
		StatValuesProxy proxy = config.getCloneUsingRows(rowsSub);
		System.out.println("-------------------------------");
		System.out.println(proxy.getInner());
		Assert.assertEquals(totalP, proxy.getInner().get(StatValuesDefault.class).getTotal().longValue());
		//
		proxy = config.getCloneUsingPredicate(new Predicate<GenericJdbcRow>() {

			public boolean test(GenericJdbcRow t) {
				return t.getLong(Select.ApporteurId).equals(1l);
			}
		});
		System.out.println("-------------------------------");
		System.out.println(proxy.getInner());
		Assert.assertEquals(totalP, proxy.getInner().get(StatValuesDefault.class).getTotal().longValue());
		//
	}

	@Test
	// @Transactional
	public void testShouldDiffValues() throws Exception {
		GenericJdbcRowList rows = new GenericJdbcRowList();
		{
			for (int j = 1; j < 5; j++) {
				for (int i = 0; i < 5; i++) {
					GenericJdbcRow row = rows.create();
					row.put(Select.ApporteurId, j);
					row.put(Select.Age, 80 + i);
					row.put(Select.ActivMonth, DateUtilsExt.from((i + 1), 2015));
					row.put(Select.Count, j);
				}
			}
		}
		System.out.println("------------------------------");
		System.out.println(rows);
		//
		StatValuesProxy config = new StatValuesProxy().with(new StatMeasureLong(Select.ApporteurId));
		config.with(new StatMeasureLong(Select.Age));
		config.with(new StatMeasureDateMonth(Select.ActivMonth));
		config.withValue(Select.Count);
		for (GenericJdbcRow ro : rows) {
			config.pushValue(ro);
		}
		System.out.println("------------------------------");
		System.out.println(config.getInner());
		// BEGIN TEST
		StatValuesProxy diff = config.diffWith(config).get();
		Assert.assertEquals(rows.size(), diff.toRowList().size());
		for (GenericJdbcRow ro : diff.toRowList()) {
			Assert.assertEquals(0, ro.getNumber(Select.Count).longValue(), 0);
		}
		System.out.println("------------------------------");
		System.out.println(diff.getInner());
	}

	@Test
	// @Transactional
	public void testShouldSumValues() throws Exception {
		GenericJdbcRowList rows = new GenericJdbcRowList();
		{
			for (int j = 1; j < 5; j++) {
				for (int i = 0; i < 5; i++) {
					GenericJdbcRow row = rows.create();
					row.put(Select.ApporteurId, j);
					row.put(Select.Age, 80 + i);
					row.put(Select.ActivMonth, DateUtilsExt.from((i + 1), 2015));
					row.put(Select.Count, j);
				}
			}
		}
		System.out.println("------------------------------");
		System.out.println(rows);
		//
		StatValuesProxy config = new StatValuesProxy().with(new StatMeasureLong(Select.ApporteurId));
		config.with(new StatMeasureLong(Select.Age));
		config.with(new StatMeasureDateMonth(Select.ActivMonth));
		config.withValue(Select.Count);
		for (GenericJdbcRow ro : rows) {
			config.pushValue(ro);
		}
		System.out.println("------------------------------");
		System.out.println(config.getInner());
		// BEGIN TEST
		StatValuesProxy sum = config.sumWith(config).get();
		Assert.assertEquals(rows.size(), sum.toRowList().size());
		for (GenericJdbcRow ro : sum.toRowList()) {
			Long ap = ro.getLong(Select.ApporteurId);
			Assert.assertEquals(ap * 2, ro.getNumber(Select.Count).longValue(), 0);
		}
		System.out.println("------------------------------");
		System.out.println(sum.getInner());
	}

	@Test
	// @Transactional
	public void testShouldFetchValuesForDimension() throws Exception {
		GenericJdbcRowList rows = new GenericJdbcRowList();
		{
			for (int j = 1; j < 5; j++) {
				for (int i = 0; i < 5; i++) {
					GenericJdbcRow row = rows.create();
					row.put(Select.ApporteurId, j);
					row.put(Select.Age, 80 + i);
					row.put(Select.ActivMonth, DateUtilsExt.from((i + 1), 2015));
					row.put(Select.Count, j);
				}
			}
		}
		System.out.println("------------------------------");
		System.out.println(rows);
		//
		StatValuesProxy config = new StatValuesProxy().with(new StatMeasureLong(Select.ApporteurId));
		config.with(new StatMeasureLong(Select.Age));
		config.with(new StatMeasureDateMonth(Select.ActivMonth));
		config.withValue(Select.Count);
		for (GenericJdbcRow ro : rows) {
			config.pushValue(ro);
		}
		System.out.println("------------------------------");
		System.out.println(config.getInner());
		// BEGIN TEST
		GenericJdbcRowList list = config.getValuesForDimension(Select.ApporteurId);
		System.out.println(list);
		Assert.assertEquals(4, list.size());
		Assert.assertEquals(1, list.get(0).getLong(Select.ApporteurId), 0);
	}

	@Test
	// @Transactional
	public void testShouldAggregate() throws Exception {
		GenericJdbcRowList rows = new GenericJdbcRowList();
		{
			for (int j = 1; j < 5; j++) {
				for (int i = 0; i < 5; i++) {
					GenericJdbcRow row = rows.create();
					row.put(Select.ApporteurId, j);
					row.put(Select.Age, 80 + i);
					row.put(Select.ActivMonth, DateUtilsExt.from((i + 1), 2015));
					row.put(Select.Count, j);
				}
			}
		}
		System.out.println("------------------------------");
		System.out.println(rows);
		//
		StatValuesProxy config = new StatValuesProxy().with(new StatMeasureLong(Select.ApporteurId));
		config.with(new StatMeasureLong(Select.Age));
		config.with(new StatMeasureDateMonth(Select.ActivMonth));
		config.withValue(Select.Count);
		for (GenericJdbcRow ro : rows) {
			config.pushValue(ro);
		}
		System.out.println("------------------------------");
		System.out.println(config.getInner());
		// BEGIN TEST
		StatValuesProxy aggregate = config.aggregate(new MeasureAggregatorRetain(Select.ApporteurId));
		System.out.println(aggregate.getInner());
		Assert.assertEquals(4, aggregate.toRowList().size());
		Assert.assertEquals(1, aggregate.toRowList().get(0).getLong(Select.ApporteurId), 0);
		Assert.assertEquals(config.getInner().get(StatValuesDefault.class).getTotal().longValue(),
				aggregate.getInner().get(StatValuesDefault.class).getTotal().longValue(), 0);
	}

	@Test
	// @Transactional
	public void testShouldAggregateAndReplace() throws Exception {
		GenericJdbcRowList rows = new GenericJdbcRowList();
		{
			for (int j = 1; j < 5; j++) {
				for (int i = 0; i < 5; i++) {
					GenericJdbcRow row = rows.create();
					row.put(Select.ApporteurId, j);
					row.put(Select.Age, 80 + i);
					row.put(Select.ActivMonth, DateUtilsExt.from((i + 1), 2015));
					row.put(Select.Count, j);
				}
			}
		}
		System.out.println("------------------------------");
		System.out.println(rows);
		//
		StatValuesProxy config = new StatValuesProxy().with(new StatMeasureLong(Select.ApporteurId));
		config.with(new StatMeasureLong(Select.Age));
		config.with(new StatMeasureDateMonth(Select.ActivMonth));
		config.withValue(Select.Count);
		for (GenericJdbcRow ro : rows) {
			config.pushValue(ro);
		}
		System.out.println("------------------------------");
		System.out.println(config.getInner());
		// BEGIN TEST
		StatValuesProxy aggregate = config.aggregate(new MeasureAggregatorReplace(Select.Age) {
			Map<Long, Long> l = Maps.newConcurrentMap();

			{
				l.put(80l, 80l);
				l.put(81l, 80l);
				l.put(82l, 82l);
				l.put(83l, 82l);
				l.put(84l, 84l);
			}

			@Override
			protected void replace(ISelect select, GenericJdbcRow row) {
				if (select.equals(Select.Age)) {
					Long before = row.getLong(Select.Age);
					row.put(select, l.get(before));
				}
			}
		});
		System.out.println(aggregate.getInner());
		Assert.assertEquals(config.getInner().get(StatValuesDefault.class).getTotal().longValue(),
				aggregate.getInner().get(StatValuesDefault.class).getTotal().longValue(), 0);
	}

	@Test
	// @Transactional
	public void testShouldCumulThenAggregate() throws Exception {
		GenericJdbcRowList rows = new GenericJdbcRowList();
		{
			for (int j = 1; j < 5; j++) {
				for (int i = 0; i < 5; i++) {
					GenericJdbcRow row = rows.create();
					row.put(Select.ApporteurId, j);
					row.put(Select.ActivMonth, DateUtilsExt.from((i + 1), 2015));
					row.put(Select.Count, 1);
				}
			}
		}
		System.out.println("------------------------------");
		System.out.println(rows);
		//
		StatValuesProxy config = new StatValuesProxy().with(new StatMeasureLong(Select.ApporteurId));
		config.with(new StatMeasureDateMonth(Select.ActivMonth));
		config.withValue(Select.Count);
		for (GenericJdbcRow ro : rows) {
			config.pushValue(ro);
		}
		System.out.println("------------------------------");
		System.out.println(config.getInner());
		// BEGIN TEST
		StatValuesProxy cumul = config.toCumulate().get();
		System.out.println("------------------------------");
		System.out.println(cumul.getInner());
		StatValuesProxy aggregate = cumul.aggregate(new MeasureAggregatorRetain(Select.ActivMonth));
		System.out.println("------------------------------");
		System.out.println(aggregate.getInner());
	}

	@Test
	// @Transactional
	public void testShouldQuery() throws Exception {
		QueryBuilder query = QueryBuilder.get().withActivationLe(new Date()).withGroupByAge().withGroupByCanton();
		System.out.println(query.build());
	}
}
