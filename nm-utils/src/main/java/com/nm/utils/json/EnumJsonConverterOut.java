package com.nm.utils.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 
 * @author Nabil
 *
 */
public class EnumJsonConverterOut extends JsonDeserializer<Enum<?>> {
	@Override
	public Enum<?> deserialize(JsonParser arg0, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		String oc = arg0.getText();
		return EnumJsonConverterRegistry.getInstance().find(oc);
	}

}