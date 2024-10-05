package com.nm.utils.jdbc.orm;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.nm.utils.ReflectionUtils;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public abstract class JdbcOrmAssociationProcessorChecker extends JdbcOrmAssociationProcessor {

	@Override
	@SuppressWarnings("unchecked")
	protected final void onFoundedList(Object o, List<Object> founded, JdbcOrmAssociationContext context) throws Exception {
		onFoundedListBefore(o, founded, context);
		Object attached = ReflectionUtils.get(o, context.getField());
		Collection<Object> attachedList = Arrays.asList(attached);
		if (attached instanceof Collection) {
			attachedList = (Collection<Object>) attached;
		}
		// ITERATE ATTACHED
		for (final Object a : attachedList) {
			Optional<Object> opt = founded.stream().filter(new Predicate<Object>() {
				public boolean test(Object t) {
					try {
						return JdbcOrmUtils.compareIds(t, a);
					} catch (Exception e) {
						throw new IllegalArgumentException(e);
					}
				}
			}).findFirst();
			if (!opt.isPresent()) {
				notPersisted(a, context);
			}
		}
		// ITERATE PERSISTED
		for (final Object a : founded) {
			Optional<Object> opt = attachedList.stream().filter(new Predicate<Object>() {
				public boolean test(Object t) {
					try {
						return JdbcOrmUtils.compareIds(t, a);
					} catch (Exception e) {
						throw new IllegalArgumentException(e);
					}
				}
			}).findFirst();
			if (!opt.isPresent()) {
				notAttach(a, context);
			}
		}
		onFoundedListAfter(o, founded, context);
	}

	protected abstract void notPersisted(Object o, JdbcOrmAssociationContext context) throws Exception;

	protected abstract void notAttach(Object o, JdbcOrmAssociationContext context) throws Exception;

	protected void onFoundedListBefore(Object root, List<Object> founded, JdbcOrmAssociationContext context) throws Exception {

	}

	protected void onFoundedListAfter(Object root, List<Object> founded, JdbcOrmAssociationContext context) throws Exception {

	}
}
