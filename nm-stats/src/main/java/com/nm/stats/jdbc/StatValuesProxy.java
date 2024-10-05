package com.nm.stats.jdbc;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;

import com.google.common.collect.Maps;
import com.nm.stats.jdbc.keys.GenericJdbcRowList;
import com.nm.utils.jdbc.GenericJdbcRow;
import com.nm.utils.jdbc.ISelect;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class StatValuesProxy extends StatValuesAbstract {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ISelect value;
	private StatValuesAbstract inner = new StatValuesDefault();
	protected Map<ISelect, StatMeasure> mapping = Maps.newLinkedHashMap();

	public StatValuesProxy() {
	}

	public StatValuesProxy(StatValuesAbstract inner) {
		super();
		this.inner = inner;
	}

	@Override
	public void compute(Comparator<String> comp) {
		inner.compute(comp);
	}

	public Collection<ISelect> getAllColumns() {
		Collection<ISelect> s = new HashSet<ISelect>(mapping.keySet());
		if (value != null) {
			s.add(value);
		}
		return s;
	}

	@Override
	public Collection<String> getCategories() {
		return inner.getCategories();
	}

	@Override
	public void clear() {
		this.inner.clear();
	}

	public StatValuesProxy aggregate(MeasureAggregator aggregator) throws Exception {
		StatValuesProxy clone = this.makeClone().get();
		clone.inner = new StatValuesDefault();
		clone.mapping = aggregator.mapping(clone);
		for (GenericJdbcRow row : toRowList()) {
			aggregator.refresh(row);
			if (value == null) {
				clone.copyValue(row, this);
			} else {
				clone.pushValue(row);
			}
		}
		return clone;
	}

	protected StatValuesProxy getCloneUsingPredicate(Collection<?> founded) throws Exception {
		GenericJdbcRowList foundedObject = new GenericJdbcRowList();
		for (Object f : founded) {
			foundedObject.add((GenericJdbcRow) f);
		}
		return getCloneUsingRows(foundedObject);
	}

	public StatValuesProxy getCloneUsingPredicate(Predicate<GenericJdbcRow> keepIf) throws Exception {
		GenericJdbcRowList list = toRowList();
		List<Object> founded = list.stream().filter(keepIf).collect(Collectors.toList());
		return getCloneUsingPredicate(founded);
	}

	public Map<String, StatValuesProxy> getCloneUsingPredicate(PredicateMulti<GenericJdbcRow> keepIf) throws Exception {
		GenericJdbcRowList list = toRowList();
		list.stream().filter(keepIf).collect(Collectors.toList());
		Map<String, StatValuesProxy> map = Maps.newLinkedHashMap();
		for (PredicateUniqueId<GenericJdbcRow> key : keepIf.keySet()) {
			map.put(key.getUuid(), getCloneUsingPredicate(keepIf.get(key)));
		}
		return map;
	}

	public StatValuesProxy getCloneUsingRows(GenericJdbcRowList rows) {
		StatValuesProxy clone = SerializationUtils.clone(this);
		clone.inner = new StatValuesDefault();
		for (GenericJdbcRow m : rows) {
			StatMeasureCollection global = measure(m);
			Number count = inner.getCount(global.generate());
			clone.inner.putAndAdd(global.generate(), count);
		}
		clone.inner.compute(new MeasureComparator(this));
		return clone;
	}

	public StatValuesAbstract sumWith(StatValuesAbstract arg) {
		StatValuesProxy clone = SerializationUtils.clone(this);
		clone.inner = new StatValuesSum(this.inner, arg.get(StatValuesProxy.class).inner);
		clone.inner.compute(new MeasureComparator(this));
		return clone;
	}

	public StatValuesAbstract diffWith(StatValuesAbstract arg) {
		StatValuesProxy clone = SerializationUtils.clone(this);
		clone.inner = new StatValuesDiff(this.inner, arg.get(StatValuesProxy.class).inner);
		clone.inner.compute(new MeasureComparator(this));
		return clone;
	}

	@Override
	public Number getCount(String generate) {
		return inner.getCount(generate);
	}

	public StatValuesAbstract getInner() {
		inner.compute(new MeasureComparator(this));
		return inner;
	}

	public Map<ISelect, StatMeasure> getMapping() {
		return mapping;
	}

	public ISelect getValue() {
		return value;
	}

	public GenericJdbcRowList getValuesForDimension(ISelect select) throws Exception {
		return getValuesForDimensions(Arrays.asList(select));
	}

	public GenericJdbcRowList getValuesForDimensions(Collection<ISelect> selects) throws Exception {
		StatMeasureCollection global = measure();
		Map<String, GenericJdbcRow> map = Maps.newLinkedHashMap();
		for (String cat : this.getInner().getCategories()) {
			global = (StatMeasureCollection) global.hydrate(cat);
			GenericJdbcRow row = new GenericJdbcRow();
			// GENERATE UNIQUE KEY
			StatMeasureCollection sub = measure(selects);
			for (ISelect select : selects) {
				StatMeasure ms = global.mapping.get(select);
				if(ms==null){
					System.out.println();
				}
				row.merge(ms.getDimension());
				sub.hydrate(row, select);
			}
			map.put(sub.generate(), row);
		}
		//
		GenericJdbcRowList rows = new GenericJdbcRowList();
		rows.addAll(map.values());
		return rows;
	}

	public StatMeasureCollection measure() {
		StatMeasureCollection clone = new StatMeasureCollection();
		for (ISelect s : this.mapping.keySet()) {
			clone.with(s, this.mapping.get(s));
		}
		return clone;
	}

	public StatMeasureCollection measure(Collection<ISelect> selects) {
		StatMeasureCollection clone = new StatMeasureCollection();
		for (ISelect s : selects) {
			clone.with(s, this.mapping.get(s));
		}
		return clone;
	}

	public StatMeasureCollection measure(GenericJdbcRow row) {
		StatMeasureCollection clone = measure();
		for (ISelect s : clone.mapping.keySet()) {
			clone.mapping.get(s).hydrate(row, s);
		}
		return clone;
	}

	public String copyValue(GenericJdbcRow ro, StatValuesProxy old) {
		StatMeasureCollection measure = measure(ro);
		StatMeasureCollection oldMeasure = old.measure(ro);
		inner.putAndAdd(measure.generate(), old.getCount(oldMeasure.generate()));
		return measure.generate();
	}

	public void pushValue(GenericJdbcRowList ro) {
		for (GenericJdbcRow r : ro) {
			this.pushValue(r);
		}
	}

	public String pushValue(GenericJdbcRow ro) {
		StatMeasureCollection measure = measure(ro);
		if (value == null) {
			inner.putAndAdd(measure.generate(), 1l);
		} else {
			inner.putAndAdd(measure.generate(), value(ro));
		}
		return measure.generate();
	}

	@Override
	public void putAndAdd(String generate, Number count) {
		inner.putAndAdd(generate, count);
	}

	public StatValuesAbstract toCumulate() {
		StatValuesProxy clone = this.makeClone().get();
		clone.inner = new StatValuesCumulated(inner);
		clone.inner.compute(new MeasureComparator(this));
		return clone;
	}

	public GenericJdbcRowList toRowList() throws Exception {
		GenericJdbcRowList rows = new GenericJdbcRowList();
		StatMeasureCollection global = measure();
		StatValuesAbstract inner = this.getInner();
		Collection<String> cats = inner.getCategories();
		for (String cat : cats) {
			GenericJdbcRow row = new GenericJdbcRow();
			global = (StatMeasureCollection) global.hydrate(cat);
			row.merge(global.getDimension());
			if (this.value != null) {
				row.put(value, inner.getCount(cat));
			}
			rows.add(row);
		}
		return rows;
	}

	@Override
	public String toString() {
		return "StatValuesProxy [value=" + value + ", inner=" + inner + ", mapping=" + mapping + "]";
	}

	private Number value(GenericJdbcRow ro) {
		return ro.getNumber(value);
	}

	public StatValuesProxy with(ISelect p, StatMeasure s) {
		this.mapping.put(p, s);
		return this;
	}

	public StatValuesProxy with(Map<ISelect, StatMeasure> mapping) {
		this.mapping.putAll(mapping);
		return this;
	}

	public StatValuesProxy with(StatMeasure s) {
		this.mapping.put(s.select, s);
		return this;
	}

	public StatValuesProxy withValue(ISelect p) {
		this.value = p;
		return this;
	}

}
