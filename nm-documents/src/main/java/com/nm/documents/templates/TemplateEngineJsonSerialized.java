package com.nm.documents.templates;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nm.documents.TableModel;
import com.nm.documents.TableModelCell;
import com.nm.documents.TableModelRow;
import com.nm.templates.constants.TemplateType;
import com.nm.templates.constants.TemplateType.TemplateTypeDefault;
import com.nm.templates.contexts.TemplateContext;
import com.nm.templates.contexts.TemplateContextDefault;
import com.nm.templates.engines.TemplateEngine;
import com.nm.templates.engines.TemplateModel;
import com.nm.utils.ByteUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class TemplateEngineJsonSerialized implements TemplateEngine {

	public boolean accept(TemplateType type) {
		if (type instanceof TemplateTypeDefault) {
			TemplateTypeDefault d = (TemplateTypeDefault) type;
			switch (d) {
			case JsonSerialized:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	public TemplateContext buildContext() {
		return new TemplateContextDefault();
	}

	public OutputStream generate(TemplateModel template, TemplateContext context) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		generate(template, context, out);
		return out;
	}

	public void generate(TemplateModel template, TemplateContext context, OutputStream output) throws Exception {
		TemplateContextDefault v = (TemplateContextDefault) context;
		JSONObject object = new JSONObject();
		for (String key : v.getResults().keysetResult()) {
			Object result = v.getResult(key);
			if (result instanceof TableModel) {
				TableModel table = (TableModel) result;
				JSONArray rows = new JSONArray();
				JSONArray headers = new JSONArray();
				rows.put(headers);
				for (TableModelCell col : table.getColumnNames()) {
					headers.put(col.getValue());
				}
				for (List<TableModelCell> row : table.getRows()) {
					JSONArray cols = new JSONArray();
					rows.put(cols);
					for (TableModelCell c : row) {
						cols.put(c.getValue());
					}
				}
				object.put(key, rows);
			} else if (result instanceof TableModelRow) {
				TableModelRow table = (TableModelRow) result;
				object.put(key, table.toJSONObject());
			} else if (result instanceof TableModelRow) {
				TableModelRow table = (TableModelRow) result;
				JSONArray row = new JSONArray();
				for (TableModelCell col : table) {
					row.put(col.getValue());
				}
				object.put(key, row);
			} else if (result instanceof String) {
				object.put(key, result);
			} else if (result instanceof byte[]) {
				object.put(key, ByteUtils.toBase64Encode((byte[]) result));
				try {
					String text = ByteUtils.toStrings((byte[]) result);
					object.put(key + "AsText", text);
				} catch (Exception e) {

				}
			} else {
				object.put(key, "Unknown type: " + result);
			}
		}
		output.write(ByteUtils.toBytes(object.toString(2)));
		output.flush();
	}

}
