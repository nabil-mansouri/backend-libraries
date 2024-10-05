package com.nm.utils.hibernate.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.google.common.collect.Maps;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.ReflectionUtils;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.IQueryBuilder;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 * @param <BEAN>
 * @param <IDTYPE>
 */
public abstract class AbstractGenericDao<BEAN, IDTYPE extends Serializable> extends HibernateDaoSupport
		implements IGenericDao<BEAN, IDTYPE> {

	public static <BEAN2> IGenericDao<BEAN2, Long> get(final Class<BEAN2> clazz) {
		return get(clazz, Long.class);
	}

	public void delete(IDTYPE bean) {
		try {
			delete(get(bean));
		} catch (NoDataFoundException e) {
		}
	}

	public Map<IDTYPE, BEAN> toMapOfID(Collection<BEAN> all) throws Exception {
		Map<IDTYPE, BEAN> map = Maps.newHashMap();
		for (BEAN a : all) {
			@SuppressWarnings("unchecked")
			IDTYPE id = (IDTYPE) ReflectionUtils.get(a, getIdPropertyName());
			map.put(id, a);
		}
		return map;
	}

	public BEAN findFirstOrDefault(IQueryBuilder query, BEAN def) {
		BEAN res = findFirstOrNull(query);
		if (res == null) {
			return def;
		}
		return res;
	}

	public static <BEAN2, IDTYPE2 extends Serializable> IGenericDao<BEAN2, IDTYPE2> get(final Class<BEAN2> clazz, Class<IDTYPE2> clazz2) {
		HibernateTemplate temp = ApplicationUtils.getBean(HibernateTemplate.class);
		AbstractGenericDao<BEAN2, IDTYPE2> a = new AbstractGenericDao<BEAN2, IDTYPE2>() {

			@Override
			protected Class<BEAN2> getClassName() {
				return clazz;
			}

		};
		a.setHibernateTemplate(temp);
		return a;
	}

	public void clear() {
		getHibernateTemplate().clear();
	}

	public <BEAN2> IGenericDao<BEAN2, IDTYPE> clone(final Class<BEAN2> clazz) {
		AbstractGenericDao<BEAN2, IDTYPE> a = new AbstractGenericDao<BEAN2, IDTYPE>() {

			@Override
			protected Class<BEAN2> getClassName() {
				return clazz;
			}

		};
		a.setSessionFactory(getSessionFactory());
		a.setHibernateTemplate(getHibernateTemplate());
		return a;
	}

	public <BEAN2, IDTYPE2 extends Serializable> IGenericDao<BEAN2, IDTYPE2> clone(final Class<BEAN2> clazz, Class<IDTYPE2> clazz2) {
		AbstractGenericDao<BEAN2, IDTYPE2> a = new AbstractGenericDao<BEAN2, IDTYPE2>() {

			@Override
			protected Class<BEAN2> getClassName() {
				return clazz;
			}

		};
		a.setSessionFactory(getSessionFactory());
		a.setHibernateTemplate(getHibernateTemplate());
		return a;
	}

	public Number count() {
		return getHibernateTemplate().execute(new HibernateCallback<Number>() {

			public Number doInHibernate(Session session) throws HibernateException {
				return (Number) session.createCriteria(getClassName()).setProjection(Projections.rowCount()).uniqueResult();
			}
		});
	}

	public Number count(final DetachedCriteria criteria) {
		criteria.setProjection(Projections.rowCount());
		// NEEDED in doInHibernate
		getHibernateTemplate().setExposeNativeSession(true);
		return getHibernateTemplate().execute(new HibernateCallback<Number>() {

			public Number doInHibernate(Session session) throws HibernateException {
				return (Number) criteria.getExecutableCriteria(session).setProjection(Projections.rowCount()).uniqueResult();
			}
		});
	}

	public void delete(BEAN bean) {
		getHibernateTemplate().delete(bean);
	}

	public void deleteAll(Collection<BEAN> beans) {
		getHibernateTemplate().deleteAll(beans);
	}

	public void evic(BEAN bean) {
		getHibernateTemplate().evict(bean);
	}

	/**
	 * Execute HQL query.
	 * 
	 * @param hql
	 *            HQL query string (eg.:
	 *            <code>SELECT b FROM Bean WHERE b.property = :property</code>)
	 * @param namedParam
	 *            The named parameter (eg.: <code>property</code>)
	 * @param paramValue
	 *            The parameter value
	 * @return A collection of found bean
	 * @throws NoDataFoundException
	 */

	protected Collection<BEAN> executeQuery(String hql, String namedParam, Object paramValue) throws NoDataFoundException {
		return executeQuery(hql, new String[] { namedParam }, new Object[] { paramValue });
	}

	/**
	 * Execute HQL query.
	 * 
	 * @param hql
	 *            HQL query string (eg.:
	 *            <code>SELECT b FROM Bean WHERE b.property1 = :property1 AND b.property2 = :property2</code>
	 *            )
	 * @param namedParams
	 *            Array of named parameters (eg.:
	 *            <code>{property1, property2}</code>
	 * @param paramsValue
	 *            Array of values of named parameters (eg.:
	 *            <code>{value1, value2}</code>)
	 * @return A collection of found bean
	 * @throws NoDataFoundException
	 */
	@SuppressWarnings("unchecked")

	protected Collection<BEAN> executeQuery(String hql, String[] namedParams, Object[] paramsValue) throws NoDataFoundException {
		try {
			return (Collection<BEAN>) getHibernateTemplate().findByNamedQueryAndNamedParam(hql, namedParams, paramsValue);
		} catch (DataAccessException e) {
			throw new NoDataFoundException("No data found");
		}
	}

	@SuppressWarnings("unchecked")

	public Collection<BEAN> find(DetachedCriteria criteria) {
		return (Collection<BEAN>) getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")

	public Collection<BEAN> find(DetachedCriteria criteria, long first, long count) {
		return (Collection<BEAN>) getHibernateTemplate().findByCriteria(criteria, (int) first, (int) count);
	}

	public Collection<BEAN> find(IQueryBuilder query) {
		if (query.hasLimit()) {
			return find(query.getQuery(), query.getFirst(true), query.getLimit());
		} else {
			return find(query.getQuery());
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<BEAN> find(long first, long count) {
		DetachedCriteria criteria = DetachedCriteria.forClass(getClassName());
		return (Collection<BEAN>) getHibernateTemplate().findByCriteria(criteria, (int) first, (int) count);
	}

	@SuppressWarnings("unchecked")
	public List<BEAN> findAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(getClassName());
		return (List<BEAN>) getHibernateTemplate().findByCriteria(criteria);
	}

	public Collection<BEAN> findByCriteria(DetachedCriteria criteria) {
		return find(criteria);
	}

	@SuppressWarnings("unchecked")

	public Collection<BEAN> findByIds(Collection<IDTYPE> ids) {
		if (ids.isEmpty()) {
			return new ArrayList<BEAN>();
		} else {
			DetachedCriteria criteria = DetachedCriteria.forClass(getClassName());
			criteria.add(Restrictions.in(getIdPropertyName(), ids));
			return (Collection<BEAN>) getHibernateTemplate().findByCriteria(criteria);
		}
	}

	public BEAN findFirst(DetachedCriteria query) throws NoDataFoundException {
		Collection<BEAN> all = find(query);
		if (all.isEmpty()) {
			throw new NoDataFoundException("No data founded");
		} else {
			return all.iterator().next();
		}
	}

	public BEAN findFirst(IQueryBuilder query) throws NoDataFoundException {
		Collection<BEAN> all = find(query);
		if (all.isEmpty()) {
			throw new NoDataFoundException("No data founded");
		} else {
			return all.iterator().next();
		}
	}

	public BEAN findFirstOrNull(IQueryBuilder query) {
		Collection<BEAN> all = find(query);
		return findFirstOrNull(all);
	}

	public BEAN findFirstOrNull(Collection<BEAN> beans) {
		if (beans.isEmpty()) {
			return null;
		} else {
			return beans.iterator().next();
		}
	}

	@SuppressWarnings("unchecked")

	public Collection<IDTYPE> findIds(DetachedCriteria query) {
		return (Collection<IDTYPE>) getHibernateTemplate().findByCriteria(query);
	}

	@SuppressWarnings("unchecked")

	public Collection<IDTYPE> findIds(IQueryBuilder query) {
		return (Collection<IDTYPE>) getHibernateTemplate().findByCriteria(query.withIdProjection().getQuery());
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	public BEAN get(IDTYPE ident) throws NoDataFoundException {
		BEAN bean = getHibernateTemplate().get(getClassName(), ident);
		if (bean == null) {
			throw new NoDataFoundException("No data found");
		}
		return bean;
	}

	/**
	 * Get Bean classe.
	 * 
	 * @return
	 */
	protected abstract Class<BEAN> getClassName();

	/**
	 * Get ID property Name
	 * 
	 * @return
	 */
	protected String getIdPropertyName() {
		return "id";
	}

	public void init(SessionFactory factory) {
		setSessionFactory(factory);
	}

	public void insert(BEAN bean) {
		getHibernateTemplate().persist(bean);
	}

	public BEAN load(IDTYPE ident) {
		return getHibernateTemplate().load(getClassName(), ident);
	}

	public BEAN loadById(IDTYPE ident) throws NoDataFoundException {
		BEAN bean = null;
		try {
			bean = getHibernateTemplate().load(getClassName(), ident);
		} catch (DataAccessException e) {
			throw new NoDataFoundException("No data found");
		}
		return bean;
	}

	public BEAN merge(BEAN bean) {
		return getHibernateTemplate().merge(bean);
	}

	public void refresh(BEAN bean) {
		getHibernateTemplate().refresh(bean);
	}

	public void save(BEAN bean) {
		getHibernateTemplate().save(bean);
	}

	public void saveOrUpdate(BEAN bean) {
		getHibernateTemplate().saveOrUpdate(bean);
	}

	public void saveOrUpdate(Collection<BEAN> bean) {
		for (BEAN b : bean) {
			getHibernateTemplate().saveOrUpdate(b);
		}

	}

	public void setSpringSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public BEAN unproxy(BEAN proxied) {
		BEAN entity = proxied;
		if (entity != null && entity instanceof HibernateProxy) {
			Hibernate.initialize(entity);
			entity = (BEAN) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
		}
		return entity;
	}

	public void update(BEAN bean) {
		getHibernateTemplate().update(bean);
	}

}
