package com.nm.app.async;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class FutureTaskContractUnique<T> extends FutureTaskContract<T> {

	public FutureTaskContractUnique(T model) {
		super(model);
	}

	@Override
	public boolean each() {
		return false;
	}

}
