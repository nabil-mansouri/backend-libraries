package com.nm.permissions.constants;

import java.util.Collection;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nm.utils.json.EnumJsonConverterIn;
import com.nm.utils.json.EnumJsonConverterOut;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@JsonSerialize(using = EnumJsonConverterIn.class)
@JsonDeserialize(using = EnumJsonConverterOut.class)
public interface ResourceType {
	ResourceType parent();

	Collection<Action> selectable();

	Collection<Action> lower();

	Collection<Action> master();

	String name();
}
