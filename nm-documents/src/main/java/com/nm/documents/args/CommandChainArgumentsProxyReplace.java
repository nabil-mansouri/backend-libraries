package com.nm.documents.args;

import java.util.List;
import java.util.Map;

import com.nm.documents.TableModel;
import com.nm.documents.TableModelRow;
import com.nm.templates.parameters.TemplateParameterTranslation;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandChainArgumentsProxyReplace extends CommandChainArguments {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2780561461546417549L;
	private final CommandChainArguments args;
	private final Map<String, String> replace;

	@Override
	public TableModelRow putTableModelRow(String src, TableModelRow row) {
		return args.putTableModelRow(src, row);
	}

	@Override
	public Map<String, TableModelRow> getTableModelRows() {
		return args.getTableModelRows();
	}

	public CommandChainArgumentsProxyReplace(CommandChainArguments args, Map<String, String> replace) {
		super();
		this.args = args;
		this.replace = replace;
	}

	@Override
	public TemplateParameterTranslation getTranslation() {
		return args.getTranslation();
	}

	@Override
	public boolean hasTranslation() {
		return args.hasTranslation();
	}

	@Override
	public void setTranslation(TemplateParameterTranslation translation) {
		args.setTranslation(translation);
	}

	@Override
	public TableModelRow getTableModelRow(String src) {
		return args.getTableModelRow(src);
	}

	public Map<String, List<CommandChainArgumentsImpl>> getForModels() {
		return args.getForModels();
	}

	public void setForModels(Map<String, List<CommandChainArgumentsImpl>> forModels) {
		args.setForModels(forModels);
	}

	public String putVarIfNotExists(String key, String val) {
		return args.putVarIfNotExists(key, val);
	}

	public Map<String, String> getSvgs() {
		return args.getSvgs();
	}

	public void setSvgs(Map<String, String> svgs) {
		args.setSvgs(svgs);
	}

	public Map<String, String> getVars() {
		return args.getVars();
	}

	public void setVars(Map<String, String> vars) {
		args.setVars(vars);
	}

	public byte[] putImage(String key, byte[] value) {
		return args.putImage(key, value);
	}

	public String putVars(String key, String value) {
		return args.putVars(key, value);
	}

	public String putVarsUnsafe(String key, String value) {
		return args.putVarsUnsafe(key, value);
	}

	public List<CommandChainArgumentsImpl> putFor(String key, List<CommandChainArgumentsImpl> value) {
		return args.putFor(key, value);
	}

	public String putSvg(String key, String value) {
		return args.putSvg(key, value);
	}

	public String putTable(String key, String value) {
		return args.putTable(key, value);
	}

	public TableModel putTable(String key, TableModel value) {
		return args.putTable(key, value);
	}

	public Map<String, TableModel> getTableModels() {
		return args.getTableModels();
	}

	public void setTableModels(Map<String, TableModel> tableModels) {
		args.setTableModels(tableModels);
	}

	public byte[] getImage(String key) {
		return args.getImage(key);
	}

	public String getTable(String key) {
		return args.getTable(key);
	}

	public List<CommandChainArgumentsImpl> getFor(String key) {
		return args.getFor(key);
	}

	public TableModel getTableModel(String key) {
		return args.getTableModel(key);
	}

	public String getVars(String key) {
		String val = args.getVars(key);
		if (this.replace.containsKey(val)) {
			return this.replace.get(val);
		} else {
			return val;
		}
	}

	public Map<String, byte[]> getImages() {
		return args.getImages();
	}

	public void setImages(Map<String, byte[]> images) {
		args.setImages(images);
	}

	public Map<String, String> getTables() {
		return args.getTables();
	}

	public void setTables(Map<String, String> tables) {
		args.setTables(tables);
	}

	public String getSvg(String src) {
		return args.getSvg(src);
	}

	public void merge(CommandChainArguments current) {
		args.merge(current);
	}

}
