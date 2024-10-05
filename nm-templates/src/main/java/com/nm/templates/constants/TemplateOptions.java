package com.nm.templates.constants;

import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.dtos.ModelOptionsType;

/**
 * 
 * @author Nabil
 * 
 */
public enum TemplateOptions implements ModelOptions {
	SaveTree;

	public ModelOptionsType getType() {
		return TemplateOptionsType.Template;
	}

	public enum TemplateOptionsType implements ModelOptionsType {
		Template
	}
}