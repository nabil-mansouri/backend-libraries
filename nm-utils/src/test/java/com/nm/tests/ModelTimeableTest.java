package com.nm.tests;

import org.junit.Assert;
import org.junit.Test;

import com.nm.utils.hibernate.impl.ModelTimeable;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class ModelTimeableTest {
	private static class Model extends ModelTimeable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Long id;

		@Override
		public Long getId() {
			return id;
		}

	}

	@Test
	public void testShouldTwoWithSameIdEquals() {
		Model t1 = new Model();
		t1.id = 1l;
		Model t2 = new Model();
		t2.id = 1l;
		Assert.assertEquals(t1, t2);
	}

	@Test
	public void testShouldTwoWithDifferentIdNotEquals() {
		Model t1 = new Model();
		t1.id = 1l;
		Model t2 = new Model();
		t2.id = 2l;
		Assert.assertNotEquals(t1, t2);
	}

	@Test
	public void testShouldTwoWithIdAndNotIdNotEquals() {
		Model t1 = new Model();
		t1.id = 1l;
		Model t2 = new Model();
		Assert.assertNotEquals(t1, t2);
	}

	@Test
	public void testShouldTwoWithIdAndNotIdNotEqualsEvenIfUUID() {
		Model t1 = new Model();
		t1.uniqueId("E");
		t1.id = 1l;
		Model t2 = new Model();
		t2.uniqueId("E");
		Assert.assertNotEquals(t1, t2);
	}

	@Test
	public void testShouldTwoSameNullEquals() {
		Model t1 = new Model();
		Assert.assertEquals(t1, t1);
	}

	@Test
	public void testShouldTwoDifferentNullWithSameUUIDEquals() {
		Model t1 = new Model();
		t1.uniqueId("E");
		Model t2 = new Model();
		t2.uniqueId("E");
		Assert.assertEquals(t1, t2);
	}

	@Test
	public void testShouldTwoDifferentNullNotEquals() {
		Model t1 = new Model();
		Model t2 = new Model();
		Assert.assertNotEquals(t1, t2);
	}

}
