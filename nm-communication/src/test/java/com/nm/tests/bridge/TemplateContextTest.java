package com.nm.tests.bridge;

import java.util.Date;
import java.util.Set;

import com.nm.templates.constants.TemplateArgsEnum;
import com.nm.templates.contexts.TemplateContextParameters;
import com.nm.templates.contexts.TemplateContextResults;
import com.nm.templates.processors.TemplateProcessorContextAbstract;
import com.nm.utils.dates.DateUtilsExt;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class TemplateContextTest extends TemplateProcessorContextAbstract {

	@Override
	protected Set<TemplateArgsEnum> all(Set<TemplateArgsEnum> empty) {
		empty.add(TemplateArgsEnumTest.TestDate);
		empty.add(TemplateArgsEnumTest.ReceptorName);
		return empty;
	}

	public void prepare(TemplateContextParameters context) {
		context.putParameter("TestDate", DateUtilsExt.from(01, 01, 2000));
		context.putParameter("ReceptorName", "Name");
	}

	public void hydrate(TemplateContextResults context) {
		String f = DateUtilsExt.format(context.getParameter("TestDate", Date.class), "dd-MM-yyyy");
		context.putResult("testdate", f);
		context.putResult("testname", context.getParameter("ReceptorName", String.class));

	}

}
