package com.nm.utils.jdbc;

import java.util.Collection;

import com.google.common.collect.Lists;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class GenericJdbcListenerComposite implements GenericJdbcListener {
	private Collection<GenericJdbcListener> listeners = Lists.newArrayList();

	public GenericJdbcListenerComposite add(GenericJdbcListener l) {
		this.listeners.add(l);
		return this;
	}

	public void onRow(GenericJdbcRow row) {
		for (GenericJdbcListener l : listeners) {
			l.onRow(row);
		}
	}

}
