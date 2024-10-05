package com.nm.documents.args;

import java.util.HashMap;
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
public class CommandChainArgumentsImpl extends CommandChainArguments {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2541239002352179278L;
	private Map<String, byte[]> images = new HashMap<String, byte[]>();
	private Map<String, String> svgs = new HashMap<String, String>();
	private Map<String, String> tables = new HashMap<String, String>();
	private Map<String, String> vars = new HashMap<String, String>();
	private Map<String, TableModel> tableModels = new HashMap<String, TableModel>();
	private Map<String, TableModelRow> tableModelRows = new HashMap<String, TableModelRow>();
	private Map<String, List<CommandChainArgumentsImpl>> forModels = Maps.newHashMap();
	private TemplateParameterTranslation translation = new TemplateParameterTranslation();

	public TemplateParameterTranslation getTranslation() {
		return translation;
	}

	@Override
	public boolean hasTranslation() {
		return !translation.isEmpty();
	}

	public void setTranslation(TemplateParameterTranslation translation) {
		this.translation = translation;
	}

	public Map<String, TableModelRow> getTableModelRows() {
		return tableModelRows;
	}

	@Override
	public TableModelRow getTableModelRow(String src) {
		return tableModelRows.get(src);
	}

	CommandChainArgumentsImpl() {
	}

	@Override
	public TableModelRow putTableModelRow(String key, TableModelRow value) {
		if (tableModelRows.containsKey(key)) {
			throw new IllegalArgumentException(String.format("Key %s already exists!", key));
		}
		return tableModelRows.put(key, value);
	}

	@Override
	public Map<String, List<CommandChainArgumentsImpl>> getForModels() {
		return forModels;
	}

	@Override
	public void setForModels(Map<String, List<CommandChainArgumentsImpl>> forModels) {
		this.forModels = forModels;
	}

	@Override
	public Map<String, String> getSvgs() {
		return svgs;
	}

	@Override
	public void setSvgs(Map<String, String> svgs) {
		this.svgs = svgs;
	}

	@Override
	public Map<String, String> getVars() {
		return vars;
	}

	@Override
	public void setVars(Map<String, String> vars) {
		this.vars = vars;
	}

	@Override
	public byte[] putImage(String key, byte[] value) {
		if (images.containsKey(key)) {
			throw new IllegalArgumentException(String.format("Key %s already exists!", key));
		}
		return images.put(key, value);
	}

	@Override
	public String putVars(String key, String value) {
		if (vars.containsKey(key)) {
			throw new IllegalArgumentException(String.format("Key %s already exists!", key));
		}
		return vars.put(key, value);
	}

	@Override
	public String putVarsUnsafe(String key, String value) {
		return vars.put(key, value);
	}

	@Override
	public String putVarIfNotExists(String key, String val) {
		if (this.getVars().containsKey(key)) {
			return getVars(key);
		} else {
			return putVars(key, val);
		}
	}

	@Override
	public List<CommandChainArgumentsImpl> putFor(String key, List<CommandChainArgumentsImpl> value) {
		if (forModels.containsKey(key)) {
			throw new IllegalArgumentException(String.format("Key %s already exists!", key));
		}
		return forModels.put(key, value);
	}

	@Override
	public String putSvg(String key, String value) {
		return svgs.put(key, value);
	}

	@Override
	public String putTable(String key, String value) {
		return tables.put(key, value);
	}

	@Override
	public TableModel putTable(String key, TableModel value) {
		return tableModels.put(key, value);
	}

	@Override
	public Map<String, TableModel> getTableModels() {
		return tableModels;
	}

	@Override
	public void setTableModels(Map<String, TableModel> tableModels) {
		this.tableModels = tableModels;
	}

	@Override
	public byte[] getImage(String key) {
		return this.images.get(key);
	}

	@Override
	public String getTable(String key) {
		return this.tables.get(key);
	}

	@Override
	public List<CommandChainArgumentsImpl> getFor(String key) {
		return this.forModels.get(key);
	}

	@Override
	public TableModel getTableModel(String key) {
		return this.tableModels.get(key);
	}

	@Override
	public String getVars(String key) {
		if (key.contains("-")) {
			System.out.println();
		}
		return this.vars.get(key);
	}

	@Override
	public Map<String, byte[]> getImages() {
		return images;
	}

	@Override
	public void setImages(Map<String, byte[]> images) {
		this.images = images;
	}

	@Override
	public Map<String, String> getTables() {
		return tables;
	}

	@Override
	public void setTables(Map<String, String> tables) {
		this.tables = tables;
	}

	@Override
	public String getSvg(String src) {
		return svgs.get(src);
	}

	@Override
	public void merge(CommandChainArguments current) {
		this.getVars().putAll(current.getVars());
		this.getTableModels().putAll(current.getTableModels());
		this.getImages().putAll(current.getImages());
	}

}
