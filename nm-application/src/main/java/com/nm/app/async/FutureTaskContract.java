package com.nm.app.async;

import java.util.Collection;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class FutureTaskContract<T> {
	private final T model;

	public String uuid() {
		return getClass().getName();
	}

	public FutureTaskContract(T model) {
		super();
		this.model = model;
	}

	public T getModel() {
		return model;
	}

	public abstract boolean each();

	public abstract void execute(Collection<T> all);
}
