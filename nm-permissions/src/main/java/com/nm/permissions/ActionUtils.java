package com.nm.permissions;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;
import com.nm.permissions.constants.Action;
import com.nm.permissions.models.Resource;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class ActionUtils {

	public static Set<Action> available(Collection<Resource> resources) {
		final Set<Action> actions = Sets.newLinkedHashSet();
		for (Resource r : resources) {
			actions.addAll(r.getAvailable());
		}
		return actions;
	}

}
