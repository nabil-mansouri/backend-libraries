package com.nm.tests.utils;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.nm.app.utils.StringMoreUtils;
import com.nm.app.utils.StringMoreUtils.ReplaceResult;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class TestStringUtils {
	@Test
	public void testMatchedCaseSensitive() {
		String text = "Je suis pas encore pret à comprendre ce que tu racontes...";
		String ref = "Tu suis pas encore pret à comprendre ce que tu dis...";
		Map<String, String> translation = Maps.newHashMap();
		translation.put("Je", "Tu");
		translation.put("racontes", "dis");
		ReplaceResult res = StringMoreUtils.replace(text, translation);
		Assert.assertEquals(2, res.getCount());
		Assert.assertEquals(ref, res.getResult());
	}

	@Test
	public void testMatchedCaseInSensitive() {
		String text = "Je suis pas encore pret à comprendre ce que tu racontes...";
		String ref = "Tu suis pas encore pret à comprendre ce que tu dis...";
		Map<String, String> translation = Maps.newHashMap();
		translation.put("JE", "Tu");
		translation.put("RACONTES", "dis");
		ReplaceResult res = StringMoreUtils.replaceIgnoreCase(text, translation);
		Assert.assertEquals(2, res.getCount());
		Assert.assertEquals(ref, res.getResult());
	}

	@Test
	public void testDoesNotMatch() {
		String text = "Je suis pas encore pret à comprendre ce que tu racontes...";
		Map<String, String> translation = Maps.newHashMap();
		translation.put("OUAH", "Tu");
		translation.put("BLAH", "dis");
		ReplaceResult res = StringMoreUtils.replaceIgnoreCase(text, translation);
		Assert.assertEquals(0, res.getCount());
		Assert.assertEquals(text, res.getResult());
	}
}
