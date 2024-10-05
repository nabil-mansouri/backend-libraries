package com.nm.utils.node;

import java.math.BigInteger;
import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoModelNodeImpl extends AbstractGenericDao<ModelNode, BigInteger> implements DaoModelNode {

	public DaoModelNodeImpl(HibernateTemplate session) {
		super();
		setHibernateTemplate(session);
	}

	@SuppressWarnings("unchecked")
	public Collection<ModelNodeType> types() {
		return (Collection<ModelNodeType>) getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(ModelNodeType.class));
	}

	public ModelNodeType types(Class<?> clazz) {
		String name = clazz.getName();
		Collection<ModelNodeType> types = types();
		for (ModelNodeType t : types) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}

	@Override
	protected Class<ModelNode> getClassName() {
		return ModelNode.class;
	}

}
