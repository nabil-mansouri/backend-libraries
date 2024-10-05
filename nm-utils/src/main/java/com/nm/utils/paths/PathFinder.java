package com.nm.utils.paths;

import java.util.List;

/**
 * 
 * @author nabilmansouri
 *
 */
public class PathFinder<T> {

	public PathUnidirectionnal<T> findFirst(PathFinderCriteria<T> criteria, List<T> all) {
		for (int i = 0; i < all.size(); i++) {
			T a = all.get(i);
			if (criteria.test(a)) {
				return new PathUnidirectionnal<T>().push(i, a);
			}
		}
		return new PathUnidirectionnal<T>();
	}

	public PathUnidirectionnalList<T> findAll(PathFinderCriteria<T> criteria, List<T> all) {
		PathUnidirectionnalList<T> list = new PathUnidirectionnalList<T>();
		for (int i = 0; i < all.size(); i++) {
			T a = all.get(i);
			if (criteria.test(a)) {
				list.push(i, a);
			}
		}
		return list;
	}
}
