package com.nm.stats.jdbc;

import java.util.function.Predicate;

import com.nm.app.utils.UUIDUtils;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class PredicateUniqueId<T> implements java.util.function.Predicate<T> {
	private Predicate<T> predicate;
	private String uuid = UUIDUtils.uuid(16);

	public PredicateUniqueId(Predicate<T> predicate) {
		super();
		this.predicate = predicate;
	}

	public PredicateUniqueId(Predicate<T> predicate, String uuid) {
		super();
		this.uuid = uuid;
		this.predicate = predicate;
	}

	public String getUuid() {
		return uuid;
	}

	public boolean test(T t) {
		return this.predicate.test(t);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PredicateUniqueId<?> other = (PredicateUniqueId<?>) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
