package com.nm.app.planning;

import java.io.InputStream;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class MergeProcessor {
	private KieContainer kContainer;

	public MergeProcessor() throws Exception {
		KieServices ks = KieServices.Factory.get();
		KieRepository kr = ks.getRepository();
		KieFileSystem kfs = ks.newKieFileSystem();
		InputStream fis = getClass().getResourceAsStream("merge.drl");
		kfs.write("src/main/resources/merge.drl", IOUtils.toByteArray(fis));
		KieBuilder kb = ks.newKieBuilder(kfs);
		kb.buildAll(); // kieModule is automatically deployed to KieRepository
						// if successfully built.
		if (kb.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
			throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
		}
		kContainer = ks.newKieContainer(kr.getDefaultReleaseId());
	}

	public void process(Collection<? extends DtoNodeMerge> all) {
		// Must be statefull => recursive
		KieSession kieSession = kContainer.newKieSession();
		// kieSession.addEventListener(new DebugAgendaEventListener());
		// kieSession.addEventListener(new DebugRuleRuntimeEventListener());
		for (DtoNodeMerge node : all) {
			kieSession.insert(node);
		}
		kieSession.insert(new NodeMerger());
		kieSession.insert(all);
		kieSession.fireAllRules();
		kieSession.dispose();
	}

}
