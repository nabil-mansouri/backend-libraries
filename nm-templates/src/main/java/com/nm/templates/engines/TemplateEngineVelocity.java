package com.nm.templates.engines;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.nm.datas.AppDataException;
import com.nm.datas.SoaAppData;
import com.nm.datas.constants.AppDataOptions;
import com.nm.datas.models.AppData;
import com.nm.templates.constants.TemplateType;
import com.nm.templates.constants.TemplateType.TemplateTypeDefault;
import com.nm.templates.contexts.TemplateContext;
import com.nm.templates.contexts.TemplateContextVelocity;
import com.nm.utils.ByteUtils;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class TemplateEngineVelocity implements TemplateEngine {

	private SoaAppData soaAppData;

	public void setSoaAppData(SoaAppData soaAppData) {
		this.soaAppData = soaAppData;
	}

	private byte[] getData(AppData data) throws AppDataException {
		OptionsList ops = new OptionsList().withOption(AppDataOptions.Content);
		return soaAppData.fetch(data.getId(), ops).getFile();
	}

	public boolean accept(TemplateType type) {
		if (type instanceof TemplateTypeDefault) {
			TemplateTypeDefault d = (TemplateTypeDefault) type;
			switch (d) {
			case Velocity:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	public TemplateContext buildContext() {
		return new TemplateContextVelocity();
	}

	public OutputStream generate(TemplateModel template, TemplateContext context) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		generate(template, context, out);
		return out;
	}

	public void generate(TemplateModel template, TemplateContext context, OutputStream output) throws Exception {
		TemplateContextVelocity v = new TemplateContextVelocity();
		v.putAll(context.getResults());
		String html = ByteUtils.toStrings(getData(template.getData()));
		VelocityUtils.run(v.getArguments(), output, "TemplateEngineVelocity", html);
	}

}
