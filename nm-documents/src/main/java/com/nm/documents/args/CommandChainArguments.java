package com.nm.documents.args;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.nm.documents.TableModel;
import com.nm.documents.TableModelRow;
import com.nm.templates.parameters.TemplateParameterTranslation;

/**
 *
 * @author MANSOURI Nabil
 *
 */
public abstract class CommandChainArguments implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static CommandChainArguments build() {
		CommandChainArgumentsImpl args = new CommandChainArgumentsImpl();
		return args;
	}

	public static CommandChainArguments buildWithProxy(Collection<? extends IReplacor> replacors) {
		Map<String, String> replace = Maps.newHashMap();
		for (IReplacor n : replacors) {
			replace.put(n.getOther(), n.getMine());
		}
		CommandChainArgumentsImpl args = new CommandChainArgumentsImpl();
		CommandChainArgumentsProxyReplace proxy = new CommandChainArgumentsProxyReplace(args, replace);
		return proxy;
	}

	public abstract TemplateParameterTranslation getTranslation();

	public abstract boolean hasTranslation();

	public abstract void setTranslation(TemplateParameterTranslation translation);

	public abstract Map<String, List<CommandChainArgumentsImpl>> getForModels();

	public abstract void setForModels(Map<String, List<CommandChainArgumentsImpl>> forModels);

	public abstract Map<String, String> getSvgs();

	public abstract void setSvgs(Map<String, String> svgs);

	public abstract Map<String, String> getVars();

	public abstract void setVars(Map<String, String> vars);

	public abstract byte[] putImage(String key, byte[] value);

	public abstract String putVars(String key, String value);

	public abstract String putVarIfNotExists(String key, String val);

	public abstract String putVarsUnsafe(String key, String value);

	public abstract List<CommandChainArgumentsImpl> putFor(String key, List<CommandChainArgumentsImpl> value);

	public abstract String putSvg(String key, String value);

	public abstract String putTable(String key, String value);

	public abstract TableModel putTable(String key, TableModel value);

	public abstract Map<String, TableModel> getTableModels();

	public abstract Map<String, TableModelRow> getTableModelRows();

	public abstract void setTableModels(Map<String, TableModel> tableModels);

	public abstract byte[] getImage(String key);

	public abstract String getTable(String key);

	public abstract List<CommandChainArgumentsImpl> getFor(String key);

	public abstract TableModel getTableModel(String key);

	public abstract String getVars(String key);

	public abstract Map<String, byte[]> getImages();

	public abstract void setImages(Map<String, byte[]> images);

	public abstract Map<String, String> getTables();

	public abstract void setTables(Map<String, String> tables);

	public abstract String getSvg(String src);

	public abstract void merge(CommandChainArguments current);

	public abstract TableModelRow getTableModelRow(String src);

	public abstract TableModelRow putTableModelRow(String src, TableModelRow row);

}