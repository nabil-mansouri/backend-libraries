package com.nm.utils.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author Nabil
 *
 */
public class EnumJsonConverterInKey extends JsonSerializer<Enum<?>> {

	@Override
	public void serialize(Enum<?> arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
		arg1.writeFieldName(EnumJsonConverterRegistry.key(arg0));
	}

}