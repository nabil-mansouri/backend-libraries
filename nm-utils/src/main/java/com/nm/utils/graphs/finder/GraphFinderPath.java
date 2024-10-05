package com.nm.utils.graphs.finder;

/**
 * 
 * @author nabilmansouri
 *
 */
public class GraphFinderPath<T> {

	private T founded;

	public GraphFinderPath() {
	}

	public GraphFinderPath(T f) {
		setFounded(f);
	}

	public T getFounded() {
		return founded;
	}

	@SuppressWarnings("unchecked")
	public <T1 extends T> GraphFinderPath<T1> convert(Class<T1> clazz) {
		return new GraphFinderPath<T1>().setFounded((T1) founded);
	}

	@SuppressWarnings("unchecked")
	public <T1 extends T> T1 getFounded(Class<T1> clazz) {
		return (T1) founded;
	}

	public GraphFinderPath<T> setFounded(T founded) {
		this.founded = founded;
		return this;
	}

	public boolean founded() {
		return this.founded != null;
	}
}
