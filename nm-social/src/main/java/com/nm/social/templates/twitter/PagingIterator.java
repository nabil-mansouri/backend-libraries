package com.nm.social.templates.twitter;

import java.util.List;

import org.springframework.social.twitter.api.CursoredList;

import com.google.api.client.util.Lists;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class PagingIterator<T> {

	public abstract CursoredList<T> call();

	public abstract CursoredList<T> call(long cursor);

	public List<T> iterate() {
		List<T> all = Lists.newArrayList();
		long cursor = -1l;
		CursoredList<T> friends = null;
		do {
			if (cursor == -1l) {
				friends = call();
			} else {
				friends = call(cursor);
			}
			if (friends != null) {
				for (T f : friends) {
					all.add(f);
				}
				cursor = friends.getNextCursor();
			}
		} while (friends != null && friends.hasNext());
		return all;
	}
}
