package com.nm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.MessageSource;

public class I18nUtils {

	private static MessageSource messagesSource;

	public static String addLangage(String locale, String jsonObject, String newContent) {
		JSONObject object = new JSONObject();
		try {
			object = new JSONObject(jsonObject);
		} catch (Exception e) {
		}
		object.put(locale, newContent);
		return object.toString();
	}

	
	public static String addListLangage(String locale, String jsonObject, List<String> newContent) {
		JSONObject object = new JSONObject();
		try {
			object = new JSONObject(jsonObject);
		} catch (Exception e) {
		}
		object.put(locale.toString(), new JSONArray(newContent));
		return object.toString();
	}
	
	public static boolean contains(List<String> locales, String current){
		for(String locale : locales){
			if(current != null && current.toString().equals(locale.toString())){
				return true;
			}
		}
		return false;
	}

	public static String getByLangage(String locale, String jsonObject) {
		JSONObject object = new JSONObject();
		try {
			object = new JSONObject(jsonObject);
		} catch (Exception e) {
		}
		if (object.has(locale.toString()))
			return object.getString(locale.toString());
		else
			return null;
	}

	public static List<String> getLangages(String jsonObject) {
		JSONObject object = new JSONObject();
		try {
			object = new JSONObject(jsonObject);
		} catch (Exception e) {
		}
		List<String> locales = new ArrayList<String>();
		for (Object key : object.keySet()) {
			locales.add(key.toString());
		}
		return locales;
	}

	public static List<String> getListByLangage(String locale, String jsonObject) {
		JSONObject object = new JSONObject();
		try {
			object = new JSONObject(jsonObject);
			JSONArray array = object.getJSONArray(locale.toString());
			return toList(array);
		} catch (Exception e) {
		}
		return new ArrayList<String>();
	}

	public static String getMessage(String code, String defaultMessage, Object[] args) {
		return messagesSource.getMessage(code, args, defaultMessage, Locale.FRENCH);
	}

	protected static List<String> toList(JSONArray jsonArray) {
		List<String> list = new ArrayList<String>();
		if (jsonArray != null) {
			int len = jsonArray.length();
			for (int i = 0; i < len; i++) {
				list.add(jsonArray.getString(i));
			}
		}
		return list;
	}

	public void setMessagesSource(MessageSource messagesSource) {
		I18nUtils.messagesSource = messagesSource;
	}

}
