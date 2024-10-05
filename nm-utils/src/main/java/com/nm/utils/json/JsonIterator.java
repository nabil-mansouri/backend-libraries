package com.nm.utils.json;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class JsonIterator {
	public static interface JsonIteratorListener {
		void onJSONObject(String key, JSONObject object);

		void onJSONArray(String key, JSONArray array);

		void onObject(String key, Object array);

		void onJSONObject(int index, JSONObject object);

		void onJSONArray(int index, JSONArray array);

		void onObject(int index, Object array);

		void onStart(JSONObject array, boolean root);

		void onStart(JSONArray array, boolean root);

		void onEnd(JSONObject array, boolean root);

		void onEnd(JSONArray array, boolean root);
	}

	public void iterate(JSONObject object, JsonIteratorListener listener) {
		iterate(object, listener, true);
	}

	public void iterate(JSONObject object, JsonIteratorListener listener, boolean root) {
		listener.onStart(object, root);
		@SuppressWarnings("unchecked")
		Iterator<String> it = object.keys();
		while (it.hasNext()) {
			String key = it.next();
			Object value = object.get(key);
			if (value instanceof JSONObject) {
				JSONObject json = (JSONObject) value;
				listener.onJSONObject(key, json);
				iterate(json, listener, false);
			} else if (value instanceof JSONArray) {
				JSONArray array = (JSONArray) value;
				listener.onJSONArray(key, array);
				iterate(array, listener, false);
			} else {
				listener.onObject(key, value);
			}
		}
		listener.onEnd(object, root);
	}

	public void iterate(JSONArray object, JsonIteratorListener listener) {
		iterate(object, listener, true);
	}

	public void iterate(JSONArray array, JsonIteratorListener listener, boolean root) {
		listener.onStart(array, root);
		for (int i = 0; i < array.length(); i++) {
			Object o = array.get(i);
			if (o instanceof JSONObject) {
				JSONObject json = (JSONObject) o;
				listener.onJSONObject(i, json);
				iterate(json, listener, false);
			} else if (o instanceof JSONArray) {
				JSONArray ar = (JSONArray) o;
				listener.onJSONArray(i, ar);
				iterate(ar, listener, false);
			} else {
				listener.onObject(i, o);
			}
		}
		listener.onEnd(array, root);
	}
}
