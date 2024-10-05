package com.rm.contract.clients.builders;

import com.rm.app.geo.AddressFormComponentsBean;

/**
 * 
 * @author Nabil
 * 
 */
public class AddressComponentFormBuilder {

	public static AddressComponentFormBuilder get() {
		return new AddressComponentFormBuilder();
	}

	private AddressFormComponentsBean bean = new AddressFormComponentsBean();

	private AddressComponentFormBuilder() {

	}

	public AddressFormComponentsBean build() {
		return bean;
	}

	public AddressComponentFormBuilder withCountry(String country) {
		bean.setCountry(country);
		return this;
	}

	public AddressComponentFormBuilder withLocality(String locality) {
		bean.setLocality(locality);
		return this;
	}

	public AddressComponentFormBuilder withStreet(String street) {
		bean.setStreet(street);
		return this;
	}

	public AddressComponentFormBuilder withPostal(String postal) {
		bean.setPostal(postal);
		return this;
	}

	public AddressComponentFormBuilder withLatitude(Double latitude) {
		bean.setLatitude(latitude);
		return this;
	}

	public AddressComponentFormBuilder withLongitude(Double longitude) {
		bean.setLongitude(longitude);
		return this;
	}

}
