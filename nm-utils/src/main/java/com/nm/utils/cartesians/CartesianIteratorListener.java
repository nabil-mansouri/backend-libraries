package com.nm.utils.cartesians;

import java.util.List;

/**
 * 
 * @author nabilmansouri
 *
 */
public interface CartesianIteratorListener<T> {
	public void onRow(List<T> value);
}
