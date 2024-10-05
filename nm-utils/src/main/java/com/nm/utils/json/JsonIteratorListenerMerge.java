package com.nm.utils.json;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class JsonIteratorListenerMerge implements JsonIterator.JsonIteratorListener {

	private JsonStack stack = new JsonStack();
	private JSONObject root = new JSONObject();
	private boolean overwrite;

	public JsonIteratorListenerMerge(boolean overwrite) {
		this.overwrite = overwrite;
	}

	public JsonStack getStack() {
		return stack;
	}

	public void onJSONObject(String key, JSONObject object) {
		if (stack.getCurrentObject().optJSONObject(key) == null) {
			stack.getCurrentObject().put(key, new JSONObject());
		}
		stack.push(stack.getCurrentObject().get(key));
	}

	public void onJSONArray(String key, JSONArray array) {
		if (stack.getCurrentObject().optJSONArray(key) == null) {
			stack.getCurrentObject().put(key, new JSONArray());
		}
		stack.push(stack.getCurrentObject().get(key));
	}

	public void onObject(String key, Object value) {
		if (overwrite) {
			stack.getCurrentObject().put(key, value);
		} else if (stack.getCurrentObject().opt(key) == null) {
			stack.getCurrentObject().put(key, value);
		}
	}

	public void onJSONObject(int index, JSONObject object) {
		if (stack.getCurrentArray().optJSONObject(index) == null) {
			stack.getCurrentArray().put(index, new JSONObject());
		}
		stack.push(stack.getCurrentArray().get(index));
	}

	public void onJSONArray(int index, JSONArray array) {
		if (stack.getCurrentArray().optJSONArray(index) == null) {
			stack.getCurrentArray().put(index, new JSONArray());
		}
		stack.push(stack.getCurrentArray().get(index));
	}

	public void onObject(int index, Object value) {
		if (overwrite) {
			stack.getCurrentArray().put(index, value);
		} else if (stack.getCurrentArray().opt(index) == null) {
			stack.getCurrentArray().put(index, value);
		}
	}

	public JSONObject getRoot() {
		return root;
	}

	public void onStart(JSONObject array, boolean root) {
		// THE ROOT INIT THE STACK
		if (root) {
			stack.clear();
			stack.push(this.root);
		}
	}

	public void onStart(JSONArray array, boolean root) {

	}

	public void onEnd(JSONObject array, boolean root) {
		stack.pop();
	}

	public void onEnd(JSONArray array, boolean root) {
		stack.pop();
	}

}
