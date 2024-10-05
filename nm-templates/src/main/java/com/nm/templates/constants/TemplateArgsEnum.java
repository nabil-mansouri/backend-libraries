package com.nm.templates.constants;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nm.utils.json.EnumJsonConverterIn;
import com.nm.utils.json.EnumJsonConverterOut;

/**
 * 
 * @author Nabil
 * 
 */
@JsonSerialize(using = EnumJsonConverterIn.class)
@JsonDeserialize(using = EnumJsonConverterOut.class)
public interface TemplateArgsEnum {

	public enum TemplateArgsEnumDefault implements TemplateArgsEnum {
		CurrentYear, CurrentMonth, CurrentDay, CurrentFullDay, //
		CurrentFullMonth, CurrentHour, CurrentMinutes, CurrrentSeconds, //
		CurrentShortDate, CurrentShortHour, CurrentFullDate;

	}
}