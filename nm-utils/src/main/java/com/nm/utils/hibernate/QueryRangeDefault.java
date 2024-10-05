package com.nm.utils.hibernate;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class QueryRangeDefault implements IQueryRange {
	private Long first;
	private Long count;

	public QueryRangeDefault(Long first, Long count) {
		super();
		this.first = first;
		this.count = count;
	}

	public Long getFirst() {
		return first;
	}

	public void setFirst(Long first) {
		this.first = first;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
