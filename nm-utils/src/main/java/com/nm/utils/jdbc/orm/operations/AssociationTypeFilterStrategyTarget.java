package com.nm.utils.jdbc.orm.operations;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.nm.utils.jdbc.orm.JdbcOrmAssociationContext;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class AssociationTypeFilterStrategyTarget implements AssociationTypeFilterStrategy {
	private List<Class<?>> clazz = Lists.newArrayList();

	public AssociationTypeFilterStrategyTarget(Class<?>... clazz) {
		super();
		this.clazz = Arrays.asList(clazz);
	}

	public boolean filter(JdbcOrmAssociationContext context) {
		return clazz.contains(context.getTarget());
	}

}
