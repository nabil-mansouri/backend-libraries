package com.nm.utils.json;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class JsonStack {
	private LinkedList<Object> stack = new LinkedList<Object>();

	public JsonStack() {

	}

	public void push(Object o) {
		stack.addLast(o);
	}

	public Object pop() {
		return stack.pollLast();
	}

	public JSONObject getCurrentObject() {
		Object o = stack.getLast();
		return (JSONObject) o;
	}

	public JSONArray getCurrentArray() {
		Object o = stack.getLast();
		return (JSONArray) o;
	}

	public void clear() {
		this.stack.clear();
	}
}
