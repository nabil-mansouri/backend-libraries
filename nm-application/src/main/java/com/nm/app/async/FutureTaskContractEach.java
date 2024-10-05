package com.nm.app.async;

import java.util.Collection;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class FutureTaskContractEach<T> extends FutureTaskContract<T> {

	public FutureTaskContractEach(T model) {
		super(model);
	}

	public abstract void execute(Collection<T> all, T current);

	@Override
	public boolean each() {
		return true;
	}

	@Override
	public void execute(Collection<T> all) {
		execute(all, getModel());
	}
}
