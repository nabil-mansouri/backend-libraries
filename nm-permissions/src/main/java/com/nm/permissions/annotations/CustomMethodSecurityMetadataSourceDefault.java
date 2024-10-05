package com.nm.permissions.annotations;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nm.permissions.constants.Action;
import com.nm.permissions.constants.ResourceType;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class CustomMethodSecurityMetadataSourceDefault implements CustomMethodSecurityListener {

	@Override
	public Set<Class<? extends Annotation>> classes() {
		Set<Class<? extends Annotation>> s = Sets.newHashSet();
		s.add(SecuredDefault.class);
		s.add(SecuredDefaultList.class);
		return s;
	}

	@Override
	public List<Annotation> multiple(Annotation annotation) {
		List<Annotation> l = Lists.newArrayList();
		if (SecuredDefault.class.isInstance(annotation)) {
			SecuredDefault m = (SecuredDefault) annotation;
			l.add(m);
		} else if (SecuredDefaultList.class.isInstance(annotation)) {
			SecuredDefaultList m = (SecuredDefaultList) annotation;
			for (SecuredDefault mm : m.values()) {
				l.add(mm);
			}
		}
		return l;
	}

	@Override
	public Action action(Annotation annotation) {
		SecuredDefault m = (SecuredDefault) annotation;
		return m.action();
	}

	@Override
	public ResourceType resource(Annotation annotation) {
		SecuredDefault m = (SecuredDefault) annotation;
		return new ResourceType() {

			@Override
			public Collection<Action> selectable() {
				return null;
			}

			@Override
			public ResourceType parent() {
				return null;
			}

			@Override
			public String name() {
				return m.resource();
			}

			@Override
			public Collection<Action> master() {
				return null;
			}

			@Override
			public Collection<Action> lower() {
				return null;
			}
		};
	}
}
