package com.nm.utils.json;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class JsonUtils {
	public static interface MergeListener {

	}

	@SuppressWarnings("unchecked")
	public static <T> T merge(T object1, T object2, boolean overwrite) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JSONObject o1 = new JSONObject(object1);
		JSONObject o2 = new JSONObject(object2);
		JSONObject merged = merge(o1, o2, overwrite);
		return (T) mapper.readValue(merged.toString(), object1.getClass());
	}

	public static String merge(String object1, String object2, boolean overwrite) {
		JsonIteratorListenerMerge merge = new JsonIteratorListenerMerge(overwrite);
		JsonIterator it = new JsonIterator();
		it.iterate(new JSONObject(object1), merge);
		it.iterate(new JSONObject(object2), merge);
		return merge.getRoot().toString();
	}

	public static JSONObject merge(JSONObject object1, JSONObject object2, boolean overwrite) {
		JsonIteratorListenerMerge merge = new JsonIteratorListenerMerge(overwrite);
		JsonIterator it = new JsonIterator();
		it.iterate(object1, merge);
		it.iterate(object2, merge);
		return merge.getRoot();
	}
}
