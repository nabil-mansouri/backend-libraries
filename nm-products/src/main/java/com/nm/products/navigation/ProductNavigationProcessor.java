package com.nm.products.navigation;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.drools.core.event.DebugAgendaEventListener;
import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.nm.products.converter.ProductDefinitionViewConverter;
import com.nm.products.dao.DaoProductDefinition;
import com.nm.products.dao.DaoProductDefinitionPart;
import com.nm.products.dtos.navigation.NavigationHeadItem;
import com.nm.products.dtos.navigation.NavigationNode;
import com.nm.products.dtos.navigation.NavigationStack;

/**
 * 
 * @author Nabil
 *
 */
public class ProductNavigationProcessor {
	private KieContainer kContainer;
	private DaoProductDefinition daoProduct;
	private DaoProductDefinitionPart daoPart;
	private ProductDefinitionViewConverter viewConverter;

	public void setDaoPart(DaoProductDefinitionPart daoPart) {
		this.daoPart = daoPart;
	}

	public void setDaoProduct(DaoProductDefinition daoProduct) {
		this.daoProduct = daoProduct;
	}

	public void setViewConverter(ProductDefinitionViewConverter viewConverter) {
		this.viewConverter = viewConverter;
	}

	public ProductNavigationProcessor() throws Exception {
		KieServices ks = KieServices.Factory.get();
		KieRepository kr = ks.getRepository();
		KieFileSystem kfs = ks.newKieFileSystem();
		InputStream fis = getClass().getResourceAsStream("navigation.drl");
		kfs.write("src/main/resources/navigation.drl", IOUtils.toByteArray(fis));
		KieBuilder kb = ks.newKieBuilder(kfs);
		kb.buildAll(); // kieModule is automatically deployed to KieRepository
						// if successfully built.
		if (kb.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
			throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
		}
		kContainer = ks.newKieContainer(kr.getDefaultReleaseId());
	}

	public ProductNavigationHelper helper() {
		return new ProductNavigationHelper(daoProduct, daoPart, viewConverter);
	}

	public void process(NavigationNode node) {
		// Must be statefull => recursive
		KieSession kieSession = kContainer.newKieSession();
		kieSession.addEventListener(new DebugAgendaEventListener());
		kieSession.addEventListener(new DebugRuleRuntimeEventListener());
		kieSession.insert(node);
		kieSession.insert(node.getBody());
		for (NavigationStack s : node.getHead().getStacks()) {
			for (NavigationHeadItem i : s.getItems()) {
				kieSession.insert(i);
			}
		}
		kieSession.insert(helper());
		kieSession.fireAllRules();
		kieSession.dispose();
	}

}
