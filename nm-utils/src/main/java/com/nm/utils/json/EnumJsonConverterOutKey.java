package com.nm.utils.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

/**
 * 
 * @author Nabil
 *
 */
public class EnumJsonConverterOutKey extends KeyDeserializer {

	@Override
	public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return EnumJsonConverterRegistry.getInstance().find(key);
	}

}