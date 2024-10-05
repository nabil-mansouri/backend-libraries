package com.nm.utils.json;

import java.io.IOException;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
/**
 * 
 * @author Nabil
 *
 */
public class RawJsonConverterIn extends JsonSerializer< JSONObject> {

	@Override
	public void serialize(JSONObject value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeRawValue(value.toString());
	}


}