package com.nm.utils.jdbc.orm.operations;

import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.collect.Lists;
import com.nm.utils.jdbc.JdbcDaoException;
import com.nm.utils.jdbc.orm.JdbcOrmAssociationContext;
import com.nm.utils.jdbc.orm.JdbcOrmAssociationProcessorChecker;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class OperationCleanUnPersisted<T> extends OperationAbstract<T> {
	private final T entity;
	private final AssociationTypeFilterStrategy strategyType;
	private AssociationUnattachedStrategy strategyOperation;
	private Collection<Object> unpersisted = Lists.newArrayList();

	public OperationCleanUnPersisted(T entity, JdbcTemplate template) {
		this(entity, template, null, null);
	}

	public OperationCleanUnPersisted(T entity, JdbcTemplate template, AssociationTypeFilterStrategy strategyType) {
		this(entity, template, strategyType, null);
	}

	public OperationCleanUnPersisted(T entity, JdbcTemplate template, AssociationTypeFilterStrategy strategyType,
			AssociationUnattachedStrategy strategyUn) {
		super(template);
		this.entity = entity;
		this.strategyType = strategyType;
		if (strategyUn == null) {
			// DELETE BY DEFAULT
			this.strategyOperation = new AssociationUnattachedStrategy() {

				public void notAttach(Object o, JdbcOrmAssociationContext context) {
					// DO NOTHING
				}
			};
		} else {
			this.strategyOperation = strategyUn;
		}
	}

	public T operation() throws JdbcDaoException {
		try {
			unpersisted.clear();
			new JdbcOrmAssociationProcessorChecker() {

				@Override
				protected boolean isOk(JdbcOrmAssociationContext a) throws Exception {
					if (strategyType != null) {
						return strategyType.filter(a);
					}
					return true;
				}

				@Override
				protected void notAttach(Object o, JdbcOrmAssociationContext context) {
				}

				@Override
				protected void notPersisted(Object o, JdbcOrmAssociationContext context) throws Exception {
					unpersisted.add(o);
					strategyOperation.notAttach(o, context);
				}
			}.process(entity, getTemplate());
			return entity;
		} catch (Exception e) {
			throw new JdbcDaoException(e);
		}
	}

	public Collection<Object> getUnpersisted() {
		return unpersisted;
	}
}
