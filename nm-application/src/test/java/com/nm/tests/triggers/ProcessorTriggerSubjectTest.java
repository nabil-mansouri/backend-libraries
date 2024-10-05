package com.nm.tests.triggers;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.nm.app.triggers.ProcessorTriggerSubject;
import com.nm.app.triggers.TriggerSubject;

/**
 * 
 * @author Nabil
 * 
 */
@Component
public class ProcessorTriggerSubjectTest implements ProcessorTriggerSubject {
	private final Collection<TriggerSubject> all = Lists.newArrayList();

	public boolean accept(TriggerSubject subject) {
		return subject instanceof TriggerSubjectTest;
	}

	public void process(TriggerSubject subject) throws Exception {
		all.add(subject);
	}

	public Collection<TriggerSubject> getAll() {
		return all;
	}

}
