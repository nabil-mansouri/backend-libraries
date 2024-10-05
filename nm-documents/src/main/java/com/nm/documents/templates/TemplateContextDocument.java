package com.nm.documents.templates;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.SerializationUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nm.documents.TableModel;
import com.nm.documents.TableModelRow;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.args.CommandChainArgumentsImpl;
import com.nm.templates.contexts.TemplateContext;
import com.nm.templates.contexts.TemplateContextAbstract;
import com.nm.templates.contexts.TemplateContextResults;
import com.nm.templates.contexts.TemplateContextResultsCollection;
import com.nm.templates.parameters.TemplateParameterTranslation;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class TemplateContextDocument extends TemplateContextAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CommandChainArguments arguments;

	public TemplateContextDocument(CommandChainArguments args) {
		this.arguments = args;
	}

	@Override
	public TemplateContext factory() {
		return new TemplateContextDocument(CommandChainArguments.build());
	}

	public Set<String> keysetResult() {
		Set<String> keys = Sets.newHashSet();
		keys.addAll(this.arguments.getForModels().keySet());
		keys.addAll(this.arguments.getImages().keySet());
		keys.addAll(this.arguments.getSvgs().keySet());
		keys.addAll(this.arguments.getTableModels().keySet());
		keys.addAll(this.arguments.getTableModelRows().keySet());
		keys.addAll(this.arguments.getTables().keySet());
		keys.addAll(this.arguments.getVars().keySet());
		return keys;
	}

	@Override
	public TemplateContext clone() {
		TemplateContextDocument v = (TemplateContextDocument) SerializationUtils.clone(this);
		v.arguments = (CommandChainArguments) SerializationUtils.clone(this.arguments);
		return v;
	}

	public TemplateContextResults cloneResult() {
		return (TemplateContextResults) clone();
	}

	public <T extends Serializable> T putResult(String key, T value) {
		if (value instanceof TableModel) {
			arguments.putTable(key, (TableModel) value);
		} else if (value instanceof TableModelRow) {
			arguments.putTableModelRow(key, (TableModelRow) value);
		} else if (value instanceof byte[]) {
			arguments.putImage(key, (byte[]) value);
		} else if (value instanceof String) {
			arguments.putVars(key, (String) value);
		} else if (value instanceof TemplateContextResultsCollection) {
			List<CommandChainArgumentsImpl> values = Lists.newArrayList();
			TemplateContextResultsCollection col = (TemplateContextResultsCollection) value;
			for (TemplateContextResults r : col) {
				TemplateContextDocument doc = (TemplateContextDocument) r;
				values.add((CommandChainArgumentsImpl) doc.arguments);
			}
			arguments.putFor(key, values);
		} else if (value instanceof TemplateContextResults) {
			TemplateContextDocument doc = (TemplateContextDocument) value;
			arguments.getFor(key).add((CommandChainArgumentsImpl) doc.arguments);
		} else if (value instanceof TemplateParameterTranslation) {
			arguments.setTranslation((TemplateParameterTranslation) value);
		} else {
			throw new IllegalArgumentException("Could not determine type of :" + value);
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getResult(String key) {
		if (arguments.getTableModel(key) != null) {
			return (T) arguments.getTableModel(key);
		} else if (arguments.getTableModelRow(key) != null) {
			return (T) arguments.getTableModelRow(key);
		} else if (arguments.getImage(key) != null) {
			return (T) arguments.getImage(key);
		} else if (arguments.getVars(key) != null) {
			return (T) arguments.getVars(key);
		} else if (arguments.getFor(key) != null) {
			TemplateContextResultsCollection res = new TemplateContextResultsCollection();
			List<CommandChainArgumentsImpl> aa = arguments.getFor(key);
			for (CommandChainArgumentsImpl a : aa) {
				TemplateContextDocument doc = new TemplateContextDocument(a);
				res.add(doc);
			}
			return (T) res;
		} else {
			throw new IllegalArgumentException("Could not found element with key:" + key);
		}
	}

	public <T extends Serializable> T getResult(String key, T defau) {
		try {
			T temp = getResult(key);
			if (temp == null) {
				return defau;
			}
			return temp;
		} catch (Exception e) {
			return defau;
		}
	}

	public CommandChainArguments getArguments() {
		return arguments;
	}
}
