package com.nm.utils.jdbc.orm;

import java.util.Collection;

import javax.persistence.Table;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.nm.utils.jdbc.AbstractJdbcDao;
import com.nm.utils.jdbc.JdbcDaoException;
import com.nm.utils.jdbc.JdbcQueryBuilderSelect;
import com.nm.utils.jdbc.insert.SimpleJdbcInsertAdapter;
import com.nm.utils.jdbc.orm.operations.AssociationListFilterStrategy;
import com.nm.utils.jdbc.orm.operations.AssociationTypeFilterStrategy;
import com.nm.utils.jdbc.orm.operations.AssociationTypeFilterStrategyTarget;
import com.nm.utils.jdbc.orm.operations.AssociationUnattachedStrategy;
import com.nm.utils.jdbc.orm.operations.OperationCleanUnAttached;
import com.nm.utils.jdbc.orm.operations.OperationCleanUnPersisted;
import com.nm.utils.jdbc.orm.operations.OperationDelete;
import com.nm.utils.jdbc.orm.operations.OperationGetByExample;
import com.nm.utils.jdbc.orm.operations.OperationGetById;
import com.nm.utils.jdbc.orm.operations.OperationGetByMap;
import com.nm.utils.jdbc.orm.operations.OperationGetUniq;
import com.nm.utils.jdbc.orm.operations.OperationInsert;
import com.nm.utils.jdbc.orm.operations.OperationRefresh;
import com.nm.utils.jdbc.orm.operations.OperationSaveOrUpdate;
import com.nm.utils.jdbc.orm.operations.OperationSaveUniq;
import com.nm.utils.jdbc.orm.operations.OperationUpdate;
import com.nm.utils.jdbc.select.RowMapperObject;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public abstract class AbstractJdbcOrmDao extends AbstractJdbcDao {
	private NamedParameterJdbcTemplate namedTemplate;

	public <T> Collection<Object> cleanUnpersisted(final T o) throws JdbcDaoException {
		OperationCleanUnPersisted<T> op = new OperationCleanUnPersisted<T>(o, getJdbcTemplate());
		op.operation();
		return op.getUnpersisted();
	}

	public <T> Collection<Object> cleanUnpersisted(final T o, Class<?>... filters) throws JdbcDaoException {
		OperationCleanUnPersisted<T> op = new OperationCleanUnPersisted<T>(o, getJdbcTemplate(),
				new AssociationTypeFilterStrategyTarget(filters));
		op.operation();
		return op.getUnpersisted();
	}

	public <T> Collection<Object> cleanUnattached(final T o) throws JdbcDaoException {
		OperationCleanUnAttached<T> op = new OperationCleanUnAttached<T>(o, getJdbcTemplate());
		op.operation();
		return op.getUnattached();
	}

	public <T> Collection<Object> cleanUnattached(final T o, AssociationTypeFilterStrategy filterType)
			throws JdbcDaoException {
		OperationCleanUnAttached<T> op = new OperationCleanUnAttached<T>(o, getJdbcTemplate(), filterType);
		op.operation();
		return op.getUnattached();
	}

	public <T> Collection<Object> cleanUnattached(final T o, AssociationTypeFilterStrategy filterType,
			AssociationUnattachedStrategy strategy) throws JdbcDaoException {
		OperationCleanUnAttached<T> op = new OperationCleanUnAttached<T>(o, getJdbcTemplate(), filterType, strategy);
		op.operation();
		return op.getUnattached();
	}

	public <T> Collection<Object> cleanUnattached(final T o, Class<?>... filters) throws JdbcDaoException {
		OperationCleanUnAttached<T> op = new OperationCleanUnAttached<T>(o, getJdbcTemplate(),
				new AssociationTypeFilterStrategyTarget(filters));
		op.operation();
		return op.getUnattached();
	}

	public <T> void delete(T o) throws JdbcDaoException {
		new OperationDelete<T>(getJdbcTemplate(), o).operation();
	}

	public final <T> Collection<T> findAll(Class<T> clazz) throws JdbcDaoException {
		try {
			Table table = clazz.getAnnotation(Table.class);
			String sql = String.format("SELECT * FROM %s", table.name());
			return getJdbcTemplate().query(sql, new RowMapperObject<T>(clazz));
		} catch (Exception e) {
			throw new JdbcDaoException(e);
		}
	}

	public final <T> Collection<T> generic(final JdbcQueryBuilderSelect query, Class<T> clazz) throws JdbcDaoException {
		return generic(query, new RowMapperObject<T>(clazz));
	}

	public <T> T get(Class<T> clazz, MapSqlParameterSource id) throws JdbcDaoException {
		return new OperationGetByMap<T>(getJdbcTemplate(), clazz, id).operation();
	}

	public <T> Collection<T> getByIds(Class<T> class1, Collection<?> ids) {
		// TODO implement
		return null;
	}

	public <T> T get(Class<T> clazz, Object id) throws JdbcDaoException {
		return new OperationGetById<T>(getJdbcTemplate(), clazz, id).operation();
	}

	public <T> T getByExample(T example) throws JdbcDaoException {
		return new OperationGetByExample<T>(getJdbcTemplate(), example).operation();
	}

	public NamedParameterJdbcTemplate getNamedTemplate() {
		if (namedTemplate == null) {
			return new NamedParameterJdbcTemplate(getJdbcTemplate());
		}
		return namedTemplate;
	}

	public <T> T getUniq(T o) throws JdbcDaoException {
		return new OperationGetUniq<T>(getJdbcTemplate(), o).operation();
	}

	public <T> void insert(T o) throws JdbcDaoException {
		new OperationInsert<T>(o, insertAdapter(), getJdbcTemplate()).operation();
	}

	protected SimpleJdbcInsertAdapter insertAdapter() {
		return new SimpleJdbcInsertAdapter() {

			public SimpleJdbcInsert build(JdbcTemplate template) throws Exception {
				return new SimpleJdbcInsert(template);
			}
		};
	}

	public <T> void refresh(final T o) throws JdbcDaoException {
		new OperationRefresh<T>(o, getJdbcTemplate()).operation();
	}

	public <T> void refresh(final T o, final AssociationListFilterStrategy s1, AssociationTypeFilterStrategy s2)
			throws JdbcDaoException {
		new OperationRefresh<T>(o, getJdbcTemplate(), s1, s2).operation();
	}

	public <T> void refresh(final T o, Class<?>... filters) throws JdbcDaoException {
		new OperationRefresh<T>(o, getJdbcTemplate(), null, new AssociationTypeFilterStrategyTarget(filters))
				.operation();
	}

	public <T> void refresh(final Collection<T> o, Class<?>... filters) throws JdbcDaoException {
		for (T oo : o) {
			refresh(oo, filters);
		}
	}

	public <T> void saveOrUpdate(T o) throws JdbcDaoException {
		new OperationSaveOrUpdate<T>(getJdbcTemplate(), o, insertAdapter()).operation();
	}

	public <T> void saveOrUpdateUniq(T o) throws JdbcDaoException {
		new OperationSaveUniq<T>(o, insertAdapter(), getJdbcTemplate()).operation();
	}

	public <T> void update(Collection<T> o) throws JdbcDaoException {
		for (T oo : o) {
			update(oo);
		}
	}

	public <T> void update(T o) throws JdbcDaoException {
		new OperationUpdate<T>(o, getJdbcTemplate()).operation();
	}

	public <T> void update(T o, boolean ignoreNull) throws JdbcDaoException {
		new OperationUpdate<T>(o, getJdbcTemplate(), ignoreNull).operation();
	}
}
