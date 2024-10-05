package com.nm.app.draft;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoDraftImpl extends AbstractGenericDao<Draft, Long>implements DaoDraft {

	@Override
	protected Class<Draft> getClassName() {
		return Draft.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	@SuppressWarnings("unchecked")
	public List<Draft> getDraftsByType(DraftType type) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Draft.class);
		criteria.add(Restrictions.eq("type", type.toString()));
		return (List<Draft>) getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public Draft getLastModifiedDraft(DraftType type) throws NoDataFoundException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Draft.class);
		criteria.add(Restrictions.eq("type", type.toString()));
		criteria.addOrder(Order.desc("updatedAt"));
		List<Draft> drafts = (List<Draft>) getHibernateTemplate().findByCriteria(criteria, 0, 1);
		if (drafts.isEmpty()) {
			throw new NoDataFoundException("could not found draft");
		} else {
			return drafts.iterator().next();
		}
	}

	@SuppressWarnings("unchecked")
	public Draft getLastCreatedDraft(DraftType type) throws NoDataFoundException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Draft.class);
		criteria.add(Restrictions.eq("type", type.toString()));
		criteria.addOrder(Order.desc("updatedAt"));
		List<Draft> drafts = (List<Draft>) getHibernateTemplate().findByCriteria(criteria, 0, 1);
		if (drafts.isEmpty()) {
			throw new NoDataFoundException("could not found draft");
		} else {
			return drafts.iterator().next();
		}
	}
}
