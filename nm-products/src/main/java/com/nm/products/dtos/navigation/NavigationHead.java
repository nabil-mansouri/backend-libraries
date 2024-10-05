package com.nm.products.dtos.navigation;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class NavigationHead implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Deque<NavigationStack> stacks = new LinkedList<NavigationStack>();

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Deque<NavigationStack> getStacks() {
		return stacks;
	}

	public void setStacks(Deque<NavigationStack> stacks) {
		this.stacks = stacks;
	}

	public NavigationStack createStack() {
		NavigationStack item = new NavigationStack();
		stacks.add(item);
		return item;
	}
}
