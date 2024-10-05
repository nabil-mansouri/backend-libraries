package com.nm.permissions.annotations;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import com.nm.permissions.constants.Action;
import com.nm.permissions.constants.ResourceType;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public interface CustomMethodSecurityListener {

	Set<Class<? extends Annotation>> classes();

	List<Annotation> multiple(Annotation m);

	Action action(Annotation s);

	ResourceType resource(Annotation s);
}
