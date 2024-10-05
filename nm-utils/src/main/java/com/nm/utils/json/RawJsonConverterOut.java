package com.nm.utils.json;

import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
/**
 * 
 * @author Nabil
 *
 */
public class RawJsonConverterOut extends JsonDeserializer<JSONObject> {
	@Override
	public JSONObject deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		Map<String, Object> bean = jp.readValueAs(new TypeReference<Map<String, Object>>() {
		});
		return new JSONObject(bean);
	}
}