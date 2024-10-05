package com.nm.app.utils;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class StringMoreUtils {
	public static class ContainsResult {
		public boolean founded;
		public String left;
		public String right;
	}

	public static class ReplaceResult {
		String result;
		int count;

		public int getCount() {
			return count;
		}

		public String getResult() {
			return result;
		}
	}

	final static String lexiconForName = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	final static java.util.Random randForName = new java.util.Random();
	final static Set<String> identifiersForName = new HashSet<String>();

	public static String randomName() {
		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			int length = randForName.nextInt(5) + 5;
			for (int i = 0; i < length; i++) {
				builder.append(lexiconForName.charAt(randForName.nextInt(lexiconForName.length())));
			}
			if (identifiersForName.contains(builder.toString())) {
				builder = new StringBuilder();
			}
		}
		return builder.toString();
	}

	public static ReplaceResult replace(String text, Map<String, String> translation) {
		ReplaceResult res = new ReplaceResult();
		String lstr = translation.keySet().toString();
		String regex = lstr.substring(1, lstr.length() - 1).replace(", ", "|");
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String matched = m.toMatchResult().group();
			m.appendReplacement(sb, translation.get(matched));
			res.count++;
		}
		m.appendTail(sb);
		res.result = sb.toString();
		return res;
	}

	public static ReplaceResult replaceIgnoreCase(String text, Map<String, String> translation) {
		ReplaceResult res = new ReplaceResult();
		String lstr = translation.keySet().toString();
		String regex = lstr.substring(1, lstr.length() - 1).replace(", ", "|");
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(text);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String matched = m.toMatchResult().group();
			String value = translation.get(matched.toLowerCase());
			m.appendReplacement(sb, value);
			res.count++;
		}
		m.appendTail(sb);
		res.result = sb.toString();
		return res;
	}

	public static double similarityCase(String s1, String s2) {
		s1 = StringUtils.trim(s1);
		s2 = StringUtils.trim(s2);
		//
		String longer = s1, shorter = s2;
		if (s1.length() < s2.length()) { // longer should always have greater
											// length
			longer = s2;
			shorter = s1;
		}
		int longerLength = longer.length();
		if (longerLength == 0) {
			return 1.0;
			/* both strings are zero length */ }
		/*
		 * // If you have StringUtils, you can use it to calculate the edit
		 * distance: return (longerLength -
		 * StringUtils.getLevenshteinDistance(longer, shorter)) / (double)
		 * longerLength;
		 */
		return (longerLength - editDistanceCase(longer, shorter)) / (double) longerLength;

	}

	public static double similarityIgnoreCase(String s1, String s2) {
		s1 = StringUtils.trim(s1);
		s2 = StringUtils.trim(s2);
		//
		String longer = s1, shorter = s2;
		if (s1.length() < s2.length()) { // longer should always have greater
											// length
			longer = s2;
			shorter = s1;
		}
		int longerLength = longer.length();
		if (longerLength == 0) {
			return 1.0;
			/* both strings are zero length */ }
		/*
		 * // If you have StringUtils, you can use it to calculate the edit
		 * distance: return (longerLength -
		 * StringUtils.getLevenshteinDistance(longer, shorter)) / (double)
		 * longerLength;
		 */
		return (longerLength - editDistanceIgnoreCase(longer, shorter)) / (double) longerLength;

	}

	public static int editDistanceCase(String s1, String s2) {

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}

	// Example implementation of the Levenshtein Edit Distance
	// See http://rosettacode.org/wiki/Levenshtein_distance#Java
	public static int editDistanceIgnoreCase(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}

	public static ContainsResult containsBidir(Collection<String> left, Collection<String> right) {
		ContainsResult res = new ContainsResult();
		for (String l : left) {
			String ll = normalize(l);
			for (String r : right) {
				String rr = normalize(r);
				if (rr.contains(ll) || ll.contains(rr)) {
					res.founded = true;
					res.left = l;
					res.right = r;
				}
			}
		}
		return res;
	}

	public static ContainsResult containsBidir(String left, Collection<String> right) {
		return containsBidir(Arrays.asList(left), right);
	}

	//
	public static String deAccent(String str) {
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}

	public static String normalize(String str) {
		String r = StringUtils.replace(deAccent(str), " ", "");
		r = StringUtils.trim(r);
		r = StringUtils.replace(r, "-", "");
		r = StringUtils.upperCase(r);
		r = StringUtils.replace(r, "'", "");
		r = StringUtils.replace(r, "\\", "");
		r = StringUtils.replace(r, "\\\\", "");
		r = StringUtils.replace(r, "/", "");
		r = StringUtils.replace(r, "//", "");
		// TODO what is?
		// r = StringUtils.replace(r, "�", "");
		// r = StringUtils.replace(r, "�", "E");
		return r;
	}

	public static boolean isNullOrEmpty(String str) {
		return Strings.isNullOrEmpty(str) || str.trim().equalsIgnoreCase("null");
	}

	public static String strReplace(String[] from, String[] to, String s) {
		for (int i = 0; i < from.length; i++) {
			s = s.replaceAll(from[i], to[i]);
		}
		return s;
	}

	public static boolean endsWithOneOf(String[] from, String... all) {
		for (String ss : all) {
			ss = StringUtils.lowerCase(ss);
			if (StringUtils.endsWithAny(ss, all)) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsOneOf(String[] from, String... all) {
		for (String ss : all) {
			if (contains(from, ss)) {
				return true;
			}
		}
		return false;
	}

	public static boolean inIgnoreCase(String value, String... toTest) {
		value = StringUtils.trim(value);
		for (String test : toTest) {
			test = StringUtils.trim(test);
			if (StringUtils.equalsIgnoreCase(value, test)) {
				return true;
			}
		}
		return false;
	}

	public static boolean inIgnoreCase(String value, Collection<String> toTest) {
		String[] t = toTest.toArray(new String[toTest.size()]);
		return inIgnoreCase(value, t);
	}

	public static boolean containsAllOf(String[] from, String... all) {
		for (String ss : all) {
			if (!contains(from, ss)) {
				return false;
			}
		}
		return true;
	}

	public static boolean contains(String[] from, String s) {
		Collection<String> columns = Arrays.asList(from);
		s = org.apache.commons.lang3.StringUtils.trim(s);
		for (String c : columns) {
			c = org.apache.commons.lang3.StringUtils.trim(c);
			if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(c, s)) {
				return true;
			}
		}
		return false;
	}

	public static boolean startsWithAnyIgnoreCase(String cgNum, Collection<String> starts) {
		for (String s : starts) {
			if (StringUtils.startsWithIgnoreCase(cgNum, s)) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsAnyIgnoreCase(String cgNum, Collection<String> starts) {
		for (String s : starts) {
			if (StringUtils.containsIgnoreCase(cgNum, s)) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsAnyIgnoreCase(String cgNum, String... starts) {
		for (String s : starts) {
			if (StringUtils.containsIgnoreCase(cgNum, s)) {
				return true;
			}
		}
		return false;
	}
}
