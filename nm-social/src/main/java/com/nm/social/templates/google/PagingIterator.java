package com.nm.social.templates.google;

import java.util.List;

import com.google.api.client.util.Lists;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class PagingIterator<T> {

	public abstract String nextPageToken();

	public abstract List<T> call() throws Exception;

	public abstract List<T> call(String nextPageToken) throws Exception;

	public List<T> iterate() throws Exception {
		List<T> all = Lists.newArrayList();
		List<T> friends = null;
		String nextPageToken = null;
		do {
			if (nextPageToken == null) {
				friends = call();
			} else {
				friends = call(nextPageToken);
			}
			if (friends != null) {
				for (T f : friends) {
					all.add(f);
				}
				nextPageToken = nextPageToken();
			}
		} while (nextPageToken != null && friends != null);
		return all;
	}
}
