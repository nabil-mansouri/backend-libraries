package com.nm.products.dtos.navigation;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class NavigationPath implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private NavigationStack stack;
	private NavigationHeadItem item;
	private int indexStack = -1;
	private int indexItem = -1;

	public void push(int index, NavigationStack stack) {
		this.indexStack = index;
		this.stack = stack;
	}

	public void push(int index, NavigationHeadItem item) {
		this.indexItem = index;
		this.item = item;
	}

	public boolean founded() {
		return this.item != null && this.stack != null;
	}

	public NavigationStack getStack() {
		return stack;
	}

	public void setStack(NavigationStack stack) {
		this.stack = stack;
	}

	public NavigationHeadItem getItem() {
		return item;
	}

	public void setItem(NavigationHeadItem item) {
		this.item = item;
	}

	public int getIndexStack() {
		return indexStack;
	}

	public void setIndexStack(int indexStack) {
		this.indexStack = indexStack;
	}

	public int getIndexItem() {
		return indexItem;
	}

	public void setIndexItem(int indexItem) {
		this.indexItem = indexItem;
	}

	public boolean same(NavigationPath path) {
		if (this.founded() && path.founded()) {
			return this.indexItem == path.getIndexItem() && this.indexStack == path.getIndexStack();
		} else {
			return this.founded() == path.founded();
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "NavigationPath [stack=" + stack + ", item=" + item + ", indexStack=" + indexStack + ", indexItem="
				+ indexItem + "]";
	}

}
