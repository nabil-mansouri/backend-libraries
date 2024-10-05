package com.nm.permissions.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public enum ActionDefault implements Action {
	Access, Read, Create, Update, Export, List, Delete, Admin;

	public static Collection<ActionDefault> allExcept(Action... actions) {
		Collection<ActionDefault> act = new ArrayList<ActionDefault>(Arrays.asList(ActionDefault.values()));
		act.removeAll(Arrays.asList(actions));
		return act;
	}

}