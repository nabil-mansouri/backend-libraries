package com.rm.buiseness.clients;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rm.app.geo.AddressFormDto;

/**
 * 
 * @author Nabil
 * 
 */
public class TestSerialise {

	//

	@Test
	public void testSerialize() throws Exception {
		// TEST
		AddressFormDto address = new AddressFormDto();
		address.setComplement("comple");
		{
			JSONObject json = new JSONObject();
			json.put("key", "key");
			address.setDetails(json);
		}
		//
		ObjectMapper map = new ObjectMapper();
		String s = map.writeValueAsString(address);
		System.out.println(s);
		//
		address = map.readValue(s, AddressFormDto.class);
		assertEquals("key", address.getDetails().get("key"));
	}

	@Test
	public void testSerializeDetails() throws Exception {
		// TEST
		AddressFormDto address = new AddressFormDto();
		address.setComplement("comple");
		//
		ObjectMapper map = new ObjectMapper();
		String s = map.writeValueAsString(address);
		System.out.println(s);
		//
		address = map.readValue(s, AddressFormDto.class);
	}

}
