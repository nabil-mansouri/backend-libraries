package com.nm.templates.contexts;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.SerializationUtils;
import org.apache.velocity.VelocityContext;

import com.google.common.collect.Sets;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class TemplateContextVelocity extends TemplateContextAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient VelocityContext arguments = new VelocityContext();

	public TemplateContextVelocity() {
		super();
	}

	@Override
	public TemplateContext factory() {
		return new TemplateContextVelocity();
	}

	@Override
	public TemplateContext clone() {
		TemplateContextVelocity v = (TemplateContextVelocity) SerializationUtils.clone(this);
		v.arguments = (VelocityContext) this.arguments.clone();
		return v;
	}

	public TemplateContextResults cloneResult() {
		return (TemplateContextResults) clone();
	}

	public Set<String> keysetResult() {
		Set<String> keys = Sets.newHashSet();
		for (Object key : this.arguments.getKeys()) {
			keys.add(key.toString());
		}
		return keys;
	}

	public <T extends Serializable> T putResult(String key, T value) {
		arguments.put(key, value);
		return value;
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getResult(String key) {
		return (T) this.arguments.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getResult(String key, T defau) {

		T temp = (T) this.arguments.get(key);
		if (temp == null) {
			return defau;
		}
		return temp;
	}

	public VelocityContext getArguments() {
		return arguments;
	}
}
