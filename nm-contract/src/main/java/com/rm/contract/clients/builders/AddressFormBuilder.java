package com.rm.contract.clients.builders;

import org.json.JSONObject;

import com.rm.app.geo.AddressFormDto;
import com.rm.app.geo.AddressFormComponentsBean;

/**
 * 
 * @author Nabil
 * 
 */
public class AddressFormBuilder {

	public static AddressFormBuilder get() {
		return new AddressFormBuilder();
	}

	private AddressFormDto bean = new AddressFormDto();

	private AddressFormBuilder() {

	}

	public AddressFormDto build() {
		return bean;
	}

	public AddressFormBuilder withId(Long id) {
		bean.setId(id);
		return this;
	}

	public AddressFormBuilder withName(String name) {
		bean.setName(name);
		return this;
	}

	public AddressFormBuilder withComplement(String complement) {
		bean.setComplement(complement);
		return this;
	}

	public AddressFormBuilder withDetails(JSONObject details) {
		bean.setDetails(details);
		return this;
	}

	public AddressFormBuilder withGeocode(String geocode) {
		bean.setGeocode(geocode);
		return this;
	}

	public AddressFormBuilder withComponents(AddressFormComponentsBean component) {
		bean.setComponents(component);
		return this;
	}

}
