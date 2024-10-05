package com.nm.tests.graphs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.tests.ConfigurationTestApplication;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestApplication.class)
public class GraphTest {

	//
	protected Log log = LogFactory.getLog(getClass());
	@Autowired
	private HibernateTemplate template;
	//
	GraphNode parent = new GraphNodeComposed();

	@Before
	public void setup() {
		parent.setAmount(2d);
		for (int i = 0; i < 3; i++) {
			GraphNode child = new GraphNodeComposed();
			child.setAmount(2d);
			parent.addChild(child);
			for (int j = 0; j < 3; j++) {
				GraphNode sub = new GraphNodeSimple();
				sub.setAmount((double) (i + j));
				child.addChild(sub);
			}
		}
	}

	@Test
	@Transactional
	public void testShouldSaveOptimized() throws Exception {
		template.save(parent);
		template.flush();
	}

	@Test
	@Transactional
	public void testShouldLoadOptimized() throws Exception {
		template.save(parent);
		template.flush();
		String uuid = parent.getUuid();
		template.clear();
		GraphNodeComposed compos = (GraphNodeComposed) template.get(GraphNode.class, uuid);
		Assert.assertEquals(3, compos.getChildren().size());
		for (GraphNode n : compos.getChildren()) {
			Assert.assertEquals(3, n.getChildren().size());
			Assert.assertTrue(n instanceof GraphNodeComposed);
			Assert.assertFalse(n.getChildren().isEmpty());
			for (GraphNode ns : n.getChildren()) {
				Assert.assertEquals(0, ns.getChildren().size());
				Assert.assertTrue(ns instanceof GraphNodeSimple);
			}
		}
	}
}
