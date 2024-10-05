package com.nm.utils.node;

import java.math.BigInteger;
import java.util.Collection;

import com.nm.utils.hibernate.IGenericDao;

/**
 * 
 * @author MANSOURI Nabil
 * 
 */
public interface DaoModelNode extends IGenericDao<ModelNode, BigInteger> {
	public Collection<ModelNodeType> types();

	public ModelNodeType types(Class<?> clazz);

}
