package com.nm.stats.jdbc.keys;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nm.stats.jdbc.PredicateMulti;
import com.nm.stats.jdbc.PredicateUniqueId;
import com.nm.utils.ByteUtils;
import com.nm.utils.jdbc.GenericJdbcRow;
import com.nm.utils.jdbc.ISelect;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class GenericJdbcRowList extends ArrayList<GenericJdbcRow> implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GenericJdbcRowList() {
	}

	public GenericJdbcRowList(List<GenericJdbcRow> list) {
		this.addAll(list);
	}

	public GenericJdbcRowList(Collection<GenericJdbcRow> generic) {
		this.addAll(generic);
	}

	public void toCsv(OutputStream out) throws IOException {
		int i = 0;
		for (GenericJdbcRow r : this) {
			if (i == 0) {
				IOUtils.write(StringUtils.join(r.keySet(), ";") + "\n", out);
			}
			i++;
			List<String> list = Lists.newArrayList();
			for (ISelect s : r.keySet()) {
				list.add(r.getString(s));
			}
			IOUtils.write(StringUtils.join(list, ";") + "\n", out);
		}
	}

	public String toCsv() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		toCsv(out);
		return ByteUtils.toStrings(out.toByteArray());
	}

	public GenericJdbcRow create() {
		GenericJdbcRow row = new GenericJdbcRow();
		this.add(row);
		return row;
	}

	public <T> GenericJdbcRowList getRowsHaving(ISelect key, Class<T> clazz, T... value) {
		return getRowsHaving(key, clazz, Arrays.asList(value));
	}

	public <T> GenericJdbcRowList getRowsHaving(ISelect key, Class<T> clazz, Collection<T> value) {
		GenericJdbcRowList list = new GenericJdbcRowList();
		for (GenericJdbcRow c : this) {
			T temp = c.getValue(key, clazz);
			if (temp != null) {
				for (T t : value) {
					if (temp.equals(t)) {
						list.add(c);
					}
				}
			}
		}
		return list;
	}

	public GenericJdbcRowList makeClone() {
		return (GenericJdbcRowList) this.clone();
	}

	public Map<String, GenericJdbcRowList> getCloneUsingPredicate(PredicateMulti<GenericJdbcRow> keepIf) throws Exception {
		this.stream().filter(keepIf).collect(Collectors.toList());
		Map<String, GenericJdbcRowList> map = Maps.newLinkedHashMap();
		for (PredicateUniqueId<GenericJdbcRow> key : keepIf.keySet()) {
			map.put(key.getUuid(), getCloneUsingPredicate(keepIf.get(key)));
		}
		return map;
	}

	protected GenericJdbcRowList getCloneUsingPredicate(Collection<?> founded) throws Exception {
		GenericJdbcRowList foundedObject = new GenericJdbcRowList();
		for (Object f : founded) {
			foundedObject.add((GenericJdbcRow) f);
		}
		return foundedObject;
	}

	public GenericJdbcRowList getCloneUsingPredicate(Predicate<GenericJdbcRow> keepIf) throws Exception {
		List<Object> founded = this.makeClone().stream().filter(keepIf).collect(Collectors.toList());
		GenericJdbcRowList foundedObject = new GenericJdbcRowList();
		for (Object f : founded) {
			foundedObject.add((GenericJdbcRow) f);
		}
		return foundedObject;
	}

	public <T> Set<T> getValuesForColumns(ISelect dimKey, Class<T> clazz) {
		Set<T> set = Sets.newHashSet();
		for (GenericJdbcRow c : this) {
			T temp = c.getValue(dimKey, clazz);
			set.add(temp);
		}
		return set;
	}

}
