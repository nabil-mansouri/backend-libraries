package com.nm.tests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.nm.utils.json.JsonUtils;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class JsonTest {
	@Test
	public void testShouldOverridePropertyWithMerge() {
		JSONObject object1 = new JSONObject();
		{
			object1.put("bool", true);
			object1.put("int", 1);
		}
		JSONObject object2 = new JSONObject();
		{
			object2.put("bool", false);
			object2.put("int", 2);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, true);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object2, object3, false);
	}

	@Test
	public void testShouldNotOverridePropertyWithoutMerge() {
		JSONObject object1 = new JSONObject();
		{
			object1.put("bool", true);
			object1.put("int", 1);
		}
		JSONObject object2 = new JSONObject();
		{
			object2.put("bool", false);
			object2.put("int", 2);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, false);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object1, object3, false);
	}

	@Test
	public void testShouldKeepPropertyWithMerge() {
		JSONObject object1 = new JSONObject();
		{
			object1.put("bool", false);
			object1.put("int", 2);
		}
		JSONObject object2 = new JSONObject();
		JSONObject object3 = JsonUtils.merge(object1, object2, true);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object1, object3, false);
	}

	@Test
	public void testShouldAddPropertyWithMerge() {
		JSONObject object1 = new JSONObject();
		JSONObject object2 = new JSONObject();
		{
			object2.put("bool", false);
			object2.put("int", 2);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, true);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object2, object3, false);
	}

	@Test
	public void testShouldAddPropertyWithoutMerge() {
		JSONObject object1 = new JSONObject();
		JSONObject object2 = new JSONObject();
		{
			object2.put("bool", false);
			object2.put("int", 2);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, false);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object2, object3, false);
	}

	// SUBPROPERTY
	@Test
	public void testShouldOverrideSubPropertyWithMerge() {
		JSONObject object1 = new JSONObject();
		{
			JSONObject sub = new JSONObject();
			sub.put("bool", true);
			sub.put("int", 1);
			object1.put("sub", sub);
		}
		JSONObject object2 = new JSONObject();
		{
			JSONObject sub = new JSONObject();
			sub.put("bool", false);
			sub.put("int", 2);
			object2.put("sub", sub);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, true);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object2, object3, false);
	}

	@Test
	public void testShouldNotOverrideSubPropertyWithoutMerge() {
		JSONObject object1 = new JSONObject();
		{
			JSONObject sub = new JSONObject();
			sub.put("bool", true);
			sub.put("int", 1);
			object1.put("sub", sub);
		}
		JSONObject object2 = new JSONObject();
		{
			JSONObject sub = new JSONObject();
			sub.put("bool", false);
			sub.put("int", 2);
			object2.put("sub", sub);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, false);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object1, object3, false);
	}

	@Test
	public void testShouldKeepSubPropertyWithMerge() {
		JSONObject object1 = new JSONObject();
		{
			JSONObject sub = new JSONObject();
			sub.put("bool", false);
			sub.put("int", 2);
			object1.put("sub", sub);
		}
		JSONObject object2 = new JSONObject();
		JSONObject object3 = JsonUtils.merge(object1, object2, true);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object1, object3, false);
	}

	@Test
	public void testShouldAddSubPropertyWithMerge() {
		JSONObject object1 = new JSONObject();
		JSONObject object2 = new JSONObject();
		{
			JSONObject sub = new JSONObject();
			sub.put("bool", false);
			sub.put("int", 2);
			object2.put("sub", sub);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, true);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object2, object3, false);
	}

	@Test
	public void testShouldAddSubPropertyWithoutMerge() {
		JSONObject object1 = new JSONObject();
		JSONObject object2 = new JSONObject();
		{
			JSONObject sub = new JSONObject();
			sub.put("bool", false);
			sub.put("int", 2);
			object2.put("sub", sub);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, false);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object2, object3, false);
	}
	// ARRAY

	@Test
	public void testShouldOverrideArrayWithMerge() {
		JSONObject object1 = new JSONObject();
		{
			JSONArray sub = new JSONArray();
			sub.put(true);
			sub.put(1);
			object1.put("sub", sub);
		}
		JSONObject object2 = new JSONObject();
		{
			JSONArray sub = new JSONArray();
			sub.put(false);
			sub.put(2);
			object2.put("sub", sub);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, true);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object2, object3, false);
	}

	@Test
	public void testShouldNotOverrideArrayWithoutMerge() {
		JSONObject object1 = new JSONObject();
		{
			JSONArray sub = new JSONArray();
			sub.put(true);
			sub.put(1);
			object1.put("sub", sub);
		}
		JSONObject object2 = new JSONObject();
		{
			JSONArray sub = new JSONArray();
			sub.put(false);
			sub.put(2);
			object2.put("sub", sub);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, false);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object1, object3, false);
	}

	@Test
	public void testShouldKeepArrayWithMerge() {
		JSONObject object1 = new JSONObject();
		{
			JSONArray sub = new JSONArray();
			sub.put(true);
			sub.put(1);
			object1.put("sub", sub);
		}
		JSONObject object2 = new JSONObject();
		JSONObject object3 = JsonUtils.merge(object1, object2, true);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object1, object3, false);
	}

	@Test
	public void testShouldAddArrayWithMerge() {
		JSONObject object1 = new JSONObject();
		JSONObject object2 = new JSONObject();
		{
			JSONObject sub = new JSONObject();
			sub.put("bool", false);
			sub.put("int", 2);
			object2.put("sub", sub);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, true);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object2, object3, false);
	}

	@Test
	public void testShouldAddArrayWithoutMerge() {
		JSONObject object1 = new JSONObject();
		JSONObject object2 = new JSONObject();
		{
			JSONArray sub = new JSONArray();
			sub.put(false);
			sub.put(2);
			object2.put("sub", sub);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, false);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object2, object3, false);
	}

	// ARRAY SUBOBJECT

	@Test
	public void testShouldOverrideArraySubWithMerge() {
		JSONObject object1 = new JSONObject();
		{
			JSONArray array = new JSONArray();
			JSONObject sub = new JSONObject();
			sub.put("bool", true);
			sub.put("int", 1);
			array.put(sub);
			object1.put("sub", array);
		}
		JSONObject object2 = new JSONObject();
		{
			JSONArray array = new JSONArray();
			JSONObject sub = new JSONObject();
			sub.put("bool", false);
			sub.put("int", 2);
			array.put(sub);
			object2.put("sub", array);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, true);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object2, object3, false);
	}

	@Test
	public void testShouldNotOverrideArraySubWithoutMerge() {
		JSONObject object1 = new JSONObject();
		{
			JSONArray array = new JSONArray();
			JSONObject sub = new JSONObject();
			sub.put("bool", true);
			sub.put("int", 1);
			array.put(sub);
			object1.put("sub", array);
		}
		JSONObject object2 = new JSONObject();
		{
			JSONArray array = new JSONArray();
			JSONObject sub = new JSONObject();
			sub.put("bool", false);
			sub.put("int", 2);
			array.put(sub);
			object2.put("sub", array);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, false);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object1, object3, false);
	}

	@Test
	public void testShouldKeepArraySubWithMerge() {
		JSONObject object1 = new JSONObject();
		{
			JSONArray array = new JSONArray();
			JSONObject sub = new JSONObject();
			sub.put("bool", true);
			sub.put("int", 1);
			array.put(sub);
			object1.put("sub", array);
		}
		JSONObject object2 = new JSONObject();
		JSONObject object3 = JsonUtils.merge(object1, object2, true);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object1, object3, false);
	}

	@Test
	public void testShouldAddArraySubWithMerge() {
		JSONObject object1 = new JSONObject();
		JSONObject object2 = new JSONObject();
		{
			JSONArray array = new JSONArray();
			JSONObject sub = new JSONObject();
			sub.put("bool", false);
			sub.put("int", 2);
			array.put(sub);
			object2.put("sub", array);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, true);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object2, object3, false);
	}

	@Test
	public void testShouldAddArraySubWithoutMerge() {
		JSONObject object1 = new JSONObject();
		JSONObject object2 = new JSONObject();
		{
			JSONArray array = new JSONArray();
			JSONObject sub = new JSONObject();
			sub.put("bool", false);
			sub.put("int", 2);
			array.put(sub);
			object2.put("sub", array);
		}
		JSONObject object3 = JsonUtils.merge(object1, object2, false);
		System.out.println(object3.toString(1));
		JSONAssert.assertEquals(object2, object3, false);
	}
}