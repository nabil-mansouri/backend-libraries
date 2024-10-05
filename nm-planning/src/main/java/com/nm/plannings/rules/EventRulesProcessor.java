package com.nm.plannings.rules;

import java.io.InputStream;
import java.util.Collections;

import org.apache.commons.io.IOUtils;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.nm.plannings.dtos.DtoSlotOccurrence;
import com.nm.plannings.dtos.DtoSlotOccurrenceGroup;
import com.nm.plannings.sorters.EventComparator;

/**
 * 
 * @author Nabil
 *
 */
public class EventRulesProcessor {
	private KieContainer kContainer;

	public EventRulesProcessor() throws Exception {
		KieServices ks = KieServices.Factory.get();
		KieRepository kr = ks.getRepository();
		KieFileSystem kfs = ks.newKieFileSystem();
		InputStream fis = getClass().getResourceAsStream("planning.drl");
		kfs.write("src/main/resources/planning.drl", IOUtils.toByteArray(fis));
		KieBuilder kb = ks.newKieBuilder(kfs);
		kb.buildAll(); // kieModule is automatically deployed to KieRepository
						// if successfully built.
		if (kb.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
			throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
		}
		kContainer = ks.newKieContainer(kr.getDefaultReleaseId());
	}

	public void process(DtoSlotOccurrenceGroup group, EventRulesContext context) {
		// Must be statefull => recursive
		KieSession kieSession = kContainer.newKieSession();
		// kieSession.addEventListener(new DebugAgendaEventListener());
		// kieSession.addEventListener(new DebugRuleRuntimeEventListener());
		for (DtoSlotOccurrence ev : group) {
			kieSession.insert(ev);
		}
		kieSession.insert(new SlotMergerImpl(context));
		kieSession.insert(new SlotSubstractorImpl(context));
		kieSession.insert(group);
		kieSession.fireAllRules();
		kieSession.dispose();
		Collections.sort(group, new EventComparator());
	}

	public void processAndFilter(DtoSlotOccurrenceGroup group, final EventRulesContext context) {
		process(group, context);
		org.apache.commons.collections.CollectionUtils.filter(group, new org.apache.commons.collections.Predicate() {

			public boolean evaluate(Object arg0) {
				DtoSlotOccurrence event = (DtoSlotOccurrence) arg0;
				switch (context.getFilter()) {
				case Strong:
					return context.getStrongers().contains(event.getType());
				case Weak:
					return !context.getStrongers().contains(event.getType());
				default:
					return false;
				}
			}
		});
	}

}
