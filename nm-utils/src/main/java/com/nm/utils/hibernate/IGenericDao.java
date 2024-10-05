package com.nm.utils.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate5.HibernateTemplate;

/**
 * 
 * @author Nabil
 * 
 * @param <BEAN>
 * @param <IDTYPE>
 */
public interface IGenericDao<BEAN, IDTYPE extends Serializable> {
	public Map<IDTYPE, BEAN> toMapOfID(Collection<BEAN> all) throws Exception;

	public <BEAN2> IGenericDao<BEAN2, IDTYPE> clone(Class<BEAN2> clazz);

	public <BEAN2, IDTYPE2 extends Serializable> IGenericDao<BEAN2, IDTYPE2> clone(Class<BEAN2> clazz,
			Class<IDTYPE2> clazz2);

	public void clear();

	public Number count();

	public BEAN findFirstOrNull(IQueryBuilder query);

	public BEAN findFirstOrDefault(IQueryBuilder query, BEAN def);

	public BEAN findFirst(IQueryBuilder query) throws NoDataFoundException;

	public BEAN get(IDTYPE ident) throws NoDataFoundException;

	public Number count(DetachedCriteria criteria);

	public void delete(IDTYPE bean);

	public void delete(BEAN bean);

	public void deleteAll(Collection<BEAN> beans);

	public void evic(BEAN bean);

	Collection<BEAN> findByCriteria(DetachedCriteria query);

	public Collection<BEAN> find(DetachedCriteria criteria);

	public Collection<BEAN> find(DetachedCriteria criteria, long first, long count);

	public Collection<BEAN> find(IQueryBuilder query);

	public Collection<IDTYPE> findIds(IQueryBuilder query);

	public Collection<IDTYPE> findIds(DetachedCriteria query);

	public Collection<BEAN> find(long first, long count);

	public List<BEAN> findAll();

	public BEAN loadById(IDTYPE ident) throws NoDataFoundException;

	public Collection<BEAN> findByIds(Collection<IDTYPE> ids);

	public void flush();

	public void insert(BEAN bean);

	public BEAN load(IDTYPE ident);

	public BEAN merge(BEAN bean);

	public void refresh(BEAN bean);

	public void save(BEAN bean);

	public void saveOrUpdate(BEAN bean);

	public void saveOrUpdate(Collection<BEAN> bean);

	public BEAN unproxy(BEAN proxied);

	public HibernateTemplate getHibernateTemplate();

	public void update(BEAN bean);

}
