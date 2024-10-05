package com.nm.social.templates.fb;

import java.util.List;

import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;

import com.google.api.client.util.Lists;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class PagingIterator<T> {

	public abstract PagedList<T> call();

	public abstract PagedList<T> call(PagingParameters p);

	public List<T> iterate() {
		List<T> all = Lists.newArrayList();
		PagingParameters param = null;
		PagedList<T> friends = null;
		do {
			if (param == null) {
				friends = call();
			} else {
				friends = call(param);
			}
			if (friends != null) {
				for (T f : friends) {
					all.add(f);
				}
				param = friends.getNextPage();
			}
		} while (param != null && friends != null);
		return all;
	}
}
