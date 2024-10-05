package com.nm.permissions.constants;

/**
 * The upper is priority the upper is granted
 * 
 * @author Nabil MANSOURI
 *
 */
public enum SubjectPriority {
	Group(1), User(2);
	private final int priority;

	private SubjectPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}
}
