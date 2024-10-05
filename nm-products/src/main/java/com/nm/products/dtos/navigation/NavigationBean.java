package com.nm.products.dtos.navigation;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.products.constants.NavigationHeadState;
import com.nm.products.constants.NavigationState;
import com.nm.products.dtos.views.ProductViewDto;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class NavigationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private NavigationState state;
	private Deque<NavigationNode> history = new LinkedList<NavigationNode>();
	private NavigationNode current;
	private NavigationNode last;
	private ProductViewDto root;

	public ProductViewDto getRoot() {
		return root;
	}

	public void setRoot(ProductViewDto root) {
		this.root = root;
	}

	public NavigationNode getLast() {
		return last;
	}

	public void setLast(NavigationNode last) {
		this.last = last;
	}

	public void push(NavigationNode node) {
		history.addLast(node);
		this.last = node;
		this.current = node;
	}

	public void replace(NavigationNode node) {
		history.removeLast();
		history.addLast(node);
		this.last = node;
		this.current = node;
	}

	public NavigationNode getCurrent() {
		return current;
	}

	public void setCurrent(NavigationNode current) {
		this.current = current;
	}

	public NavigationState getState() {
		return state;
	}

	public void goToLast() {
		this.setCurrent(this.getLast());
	}

	public void setState(NavigationState state) {
		this.state = state;
	}

	public Deque<NavigationNode> getHistory() {
		return history;
	}

	public void setHistory(Deque<NavigationNode> history) {
		this.history = history;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public NavigationNode createNode() {
		NavigationNode item = new NavigationNode();
		push(item);
		return item;
	}

	public NavigationNode findRoot() {
		for (NavigationNode node : this.getHistory()) {
			NavigationPath path = node.find(NavigationHeadState.Current);
			if (path.founded()) {
				if (path.getItem().isRoot()) {
					return node;
				}
			}
		}
		return null;
	}
}
