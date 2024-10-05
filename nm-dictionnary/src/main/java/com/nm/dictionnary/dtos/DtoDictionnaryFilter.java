package com.nm.dictionnary.dtos;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 
 * @author Nabil
 * 
 */
public class DtoDictionnaryFilter {
	private Map<String, String> texts = Maps.newHashMap();
	private Map<String, Integer> numbers = Maps.newHashMap();

	public Map<String, String> getTexts() {
		return texts;
	}

	public void setTexts(Map<String, String> texts) {
		this.texts = texts;
	}

	public Map<String, Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(Map<String, Integer> numbers) {
		this.numbers = numbers;
	}

}
