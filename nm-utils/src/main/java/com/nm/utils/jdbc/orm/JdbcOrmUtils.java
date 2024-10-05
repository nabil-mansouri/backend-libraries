package com.nm.utils.jdbc.orm;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nm.utils.ReflectionUtils;
import com.nm.utils.jdbc.orm.JdbcOrmAssociationContext.TypeOfAssociation;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class JdbcOrmUtils {
	public static Collection<JdbcOrmAssociationContext> associations(Object o) throws Exception {
		Collection<JdbcOrmAssociationContext> associations = Lists.newArrayList();
		associations(o, associations, TypeOfAssociation.OneToMany, OneToMany.class);
		associations(o, associations, TypeOfAssociation.ManyToMany, ManyToMany.class);
		associations(o, associations, TypeOfAssociation.OneToOne, OneToOne.class);
		return associations;
	}

	public static boolean compareIds(Object o1, Object o2) throws Exception {
		MapSqlParameterSource map1 = getIdsValuesMapped(o1);
		MapSqlParameterSource map2 = getIdsValuesMapped(o2);
		// FIX COMPARE LONG AND INTEGER
		Map<String, Object> map1T = fixCompare(map1);
		Map<String, Object> map2T = fixCompare(map2);
		return map1T.equals(map2T);
	}

	private static Map<String, Object> fixCompare(MapSqlParameterSource map) {
		Map<String, Object> map1T = Maps.newHashMap();
		// FIX COMPARE INT WITH LONG (=> NORMALIZE)
		for (Entry<String, Object> entry : map.getValues().entrySet()) {
			if (entry.getValue() instanceof Integer) {
				Integer i = (Integer) entry.getValue();
				map1T.put(entry.getKey(), new Long(i));
			} else {
				map1T.put(entry.getKey(), entry.getValue());
			}
		}
		return map1T;
	}

	public static void main(String[] args) {
		MapSqlParameterSource m = new MapSqlParameterSource();
		m.addValue("RESIDENT_DEF", new Integer(295439));
		MapSqlParameterSource m1 = new MapSqlParameterSource();
		m1.addValue("RESIDENT_DEF", new Long(295439));
		System.out.println(m.getValues().equals(m1.getValues()));
	}

	private static void associations(Object o, Collection<JdbcOrmAssociationContext> all, TypeOfAssociation type,
			Class<? extends Annotation> anno) throws Exception {
		List<Field> fields = ReflectionUtils.getFieldHavingAnnotation(o.getClass(), anno);
		for (Field f : fields) {
			JoinColumns joins = f.getAnnotation(JoinColumns.class);
			JoinColumn join = f.getAnnotation(JoinColumn.class);
			if (joins != null || join != null) {
				Class<?> target = f.getType();
				if (ReflectionUtils.isCollection(f)) {
					target = ReflectionUtils.getGenericParameter(f);
				}
				JdbcOrmAssociationContext assoc = new JdbcOrmAssociationContext(type, target, f);
				//
				if (joins != null && joins.value().length > 1) {
					for (JoinColumn j : joins.value()) {
						Field field = getFieldForColumn(o.getClass(), j.referencedColumnName());
						assoc.getMap().addValue(j.name(), ReflectionUtils.get(o, field));
					}
				} else {
					join = (joins != null) ? joins.value()[0] : join;
					String referencedName = (Strings.isNullOrEmpty(join.referencedColumnName())) ? getIdsColumns(o)[0]
							: join.referencedColumnName();
					Field field = getFieldForColumn(o.getClass(), referencedName);
					assoc.getMap().addValue(join.name(), ReflectionUtils.get(o, field));
				}
				all.add(assoc);
			}

		}
	}

	public static Field getFieldForColumn(Class<?> clazz, String c) {
		for (Field f : ReflectionUtils.getAllFieldsRecursive(clazz)) {
			Column column = f.getAnnotation(Column.class);
			if (column != null && StringUtils.equalsIgnoreCase(column.name(), c)) {
				return f;
			}
		}
		return null;
	}

	public static <T> Field[] getUniqueField(Class<T> clazz) throws Exception {
		Field[] fields = {};
		String[] uniqColumns = getUniqueColumns(clazz);
		for (Field f : ReflectionUtils.getAllFieldsRecursive(clazz)) {
			Column column = f.getAnnotation(Column.class);
			if (column != null && ArrayUtils.contains(uniqColumns, column.name())) {
				fields = ArrayUtils.add(fields, f);
			}
		}
		return fields;
	}

	public static <T> String[] getUniqueColumns(Class<T> clazz) throws Exception {
		Table table = clazz.getAnnotation(Table.class);
		String[] restricted = {};
		for (UniqueConstraint u : table.uniqueConstraints()) {
			for (Serializable s : u.columnNames()) {
				restricted = ArrayUtils.add(restricted, s.toString());
			}
		}
		return restricted;
	}

	public static <T> MapSqlParameterSource getUniqueValues(Object o, boolean includeNull) throws Exception {
		String[] uniqColumns = getUniqueColumns(o.getClass());
		MapSqlParameterSource sql = new MapSqlParameterSource();
		for (Field f : ReflectionUtils.getAllFieldsRecursive(o.getClass())) {
			Column column = f.getAnnotation(Column.class);
			if (column != null && ArrayUtils.contains(uniqColumns, column.name())) {
				Object value = com.nm.utils.ReflectionUtils.get(o, f);
				if (includeNull || value != null) {
					sql.addValue(column.name(), value);
				}
			}
		}
		return sql;
	}

	public static <T> String[] getAllColumns(Object o, boolean includeNull) throws Exception {
		String[] restricted = {};
		for (Field f : ReflectionUtils.getAllFieldsRecursive(o.getClass())) {
			Column column = f.getAnnotation(Column.class);
			if (column != null) {
				Object value = com.nm.utils.ReflectionUtils.get(o, f);
				if (includeNull || value != null) {
					restricted = ArrayUtils.add(restricted, column.name());
				}
			}
		}
		return restricted;
	}

	public static <T> MapSqlParameterSource getAllValuesMapped(T o, boolean includeNull) throws Exception {
		final MapSqlParameterSource all = new MapSqlParameterSource();
		for (Field f : ReflectionUtils.getAllFieldsRecursive(o.getClass())) {
			Column column = f.getAnnotation(Column.class);
			if (column != null) {
				Object value = com.nm.utils.ReflectionUtils.get(o, f);
				if (includeNull || value != null) {
					all.addValue(column.name(), value);
				}
			}
		}
		return all;
	}

	public static <T> String getFullTableName(T o) throws Exception {
		return getFullTableName(o.getClass());
	}

	public static <T> String getFullTableName(Class<T> o) throws Exception {
		Table table = o.getAnnotation(Table.class);
		List<String> names = Lists.newArrayList();
		if (!Strings.isNullOrEmpty(table.catalog())) {
			names.add(table.catalog());
		}
		if (!Strings.isNullOrEmpty(table.schema())) {
			names.add(table.schema());
		}
		if (!Strings.isNullOrEmpty(table.name())) {
			names.add(table.name());
		}
		return StringUtils.join(names, ".");
	}

	public static <T> String[] getIdsColumns(Class<T> o) throws Exception {
		String[] restricted = {};
		for (Field f : ReflectionUtils.getAllFieldsRecursive(o)) {
			Column column = f.getAnnotation(Column.class);
			Id id = f.getAnnotation(Id.class);
			if (column != null && id != null) {
				restricted = ArrayUtils.add(restricted, column.name());
			}
		}
		return restricted;
	}

	public static <T> String[] getIdsColumns(T o) throws Exception {
		return getIdsColumns(o.getClass());
	}

	public static <T> Field[] getIdsField(Class<T> o) throws Exception {
		Field[] restricted = {};
		for (Field f : ReflectionUtils.getAllFieldsRecursive(o)) {
			Column column = f.getAnnotation(Column.class);
			Id id = f.getAnnotation(Id.class);
			if (column != null && id != null) {
				restricted = ArrayUtils.add(restricted, f);
			}
		}
		return restricted;
	}

	public static <T> Field[] getIdsField(T o) throws Exception {
		return getIdsField(o.getClass());
	}

	public static <T> List<Object> getIdsValues(T o) throws Exception {
		final List<Object> all = Lists.newArrayList();
		for (Field f : ReflectionUtils.getAllFieldsRecursive(o.getClass())) {
			Column column = f.getAnnotation(Column.class);
			Id id = f.getAnnotation(Id.class);
			if (column != null) {
				Object value = com.nm.utils.ReflectionUtils.get(o, f);
				if (id != null) {
					all.add(value);
				}
			}
		}
		return all;
	}

	public static <T> MapSqlParameterSource getIdsValuesMapped(T o) throws Exception {
		final MapSqlParameterSource all = new MapSqlParameterSource();
		for (Field f : ReflectionUtils.getAllFieldsRecursive(o.getClass())) {
			Column column = f.getAnnotation(Column.class);
			Id id = f.getAnnotation(Id.class);
			if (column != null) {
				Object value = com.nm.utils.ReflectionUtils.get(o, f);
				if (id != null) {
					all.addValue(column.name(), value);
				}
			}
		}
		return all;
	}

	public static <T> String[] getNonIdsColumns(Object o, boolean includeNull) throws Exception {
		String[] restricted = {};
		for (Field f : ReflectionUtils.getAllFieldsRecursive(o.getClass())) {
			Column column = f.getAnnotation(Column.class);
			Id id = f.getAnnotation(Id.class);
			if (column != null && id == null) {
				Object value = com.nm.utils.ReflectionUtils.get(o, f);
				if (includeNull || value != null) {
					restricted = ArrayUtils.add(restricted, column.name());
				}
			}
		}
		return restricted;
	}

	public static <T> MapSqlParameterSource getNonIdsValuesMapped(T o, boolean includeNull) throws Exception {
		final MapSqlParameterSource all = new MapSqlParameterSource();
		for (Field f : ReflectionUtils.getAllFieldsRecursive(o.getClass())) {
			Column column = f.getAnnotation(Column.class);
			Id id = f.getAnnotation(Id.class);
			if (column != null) {
				Object value = com.nm.utils.ReflectionUtils.get(o, f);
				if (id == null) {
					if (includeNull || value != null) {
						all.addValue(column.name(), value);
					}
				}
			}
		}
		return all;
	}

	public static <T> String getTableName(T o) throws Exception {
		Table table = o.getClass().getAnnotation(Table.class);
		return table.name();
	}

	public static <T> String[] isGeneratedId(Class<T> o) throws Exception {
		String[] restricted = {};
		for (Field f : ReflectionUtils.getAllFieldsRecursive(o)) {
			Column column = f.getAnnotation(Column.class);
			Id id = f.getAnnotation(Id.class);
			GeneratedValue gen = f.getAnnotation(GeneratedValue.class);
			if (column != null && id != null && gen != null) {
				restricted = ArrayUtils.add(restricted, column.name());
			}
		}
		return restricted;
	}

	public static <T> String[] isGeneratedId(T o) throws Exception {
		return isGeneratedId(o.getClass());
	}
}
