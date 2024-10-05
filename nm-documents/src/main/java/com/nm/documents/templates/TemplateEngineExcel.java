package com.nm.documents.templates;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.nm.datas.AppDataException;
import com.nm.datas.SoaAppData;
import com.nm.datas.constants.AppDataOptions;
import com.nm.datas.models.AppData;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.sheets.poi.ExcelProcessor;
import com.nm.templates.constants.TemplateType;
import com.nm.templates.constants.TemplateType.TemplateTypeDefault;
import com.nm.templates.contexts.TemplateContext;
import com.nm.templates.engines.TemplateEngine;
import com.nm.templates.engines.TemplateModel;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class TemplateEngineExcel implements TemplateEngine {

	private SoaAppData soaAppData;

	public void setSoaAppData(SoaAppData soaAppData) {
		this.soaAppData = soaAppData;
	}

	public boolean accept(TemplateType type) {
		if (type instanceof TemplateTypeDefault) {
			TemplateTypeDefault d = (TemplateTypeDefault) type;
			switch (d) {
			case CustomExcel:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	private byte[] getData(AppData data) throws AppDataException {
		OptionsList ops = new OptionsList().withOption(AppDataOptions.Content);
		return soaAppData.fetch(data.getId(), ops).getFile();
	}

	public TemplateContext buildContext() {
		return new TemplateContextDocument(CommandChainArguments.build());
	}

	public OutputStream generate(TemplateModel template, TemplateContext context) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		generate(template, context, out);
		return out;
	}

	public void generate(TemplateModel template, TemplateContext context, OutputStream output) throws Exception {
		TemplateContextDocument v = (TemplateContextDocument) context;
		InputStream input = new ByteArrayInputStream(getData(template.getData()));
		new ExcelProcessor().process(v.getArguments(), input, output);
	}

}
