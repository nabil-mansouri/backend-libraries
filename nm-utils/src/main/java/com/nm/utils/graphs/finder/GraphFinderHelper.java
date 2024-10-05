package com.nm.utils.graphs.finder;

import java.util.Collection;

/**
 * 
 * @author nabilmansouri
 *
 */
public class GraphFinderHelper {

	public static boolean filter(IGraphIdentifiable id, Collection<IGraphIdentifier> ids) {
		boolean managed = false;
		boolean filter = true;
		for (IGraphIdentifier i : ids) {
			if (id.manage(i)) {
				managed = true;
				if (!id.filter(i)) {
					filter = false;
				}
			}
		}
		return managed && filter;
	}

}
