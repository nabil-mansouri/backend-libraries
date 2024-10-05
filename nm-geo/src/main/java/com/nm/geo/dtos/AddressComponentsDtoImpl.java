package com.nm.geo.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressComponentsDtoImpl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String country;
	protected String locality;
	protected String street;
	protected String postal;
	protected Double latitude;
	protected Double longitude;

	public String getCountry() {
		return country;
	}

	public AddressComponentsDtoImpl setCountry(String country) {
		this.country = country;
		return this;
	}

	public String getLocality() {
		return locality;
	}

	public AddressComponentsDtoImpl setLocality(String locality) {
		this.locality = locality;
		return this;
	}

	public String getStreet() {
		return street;
	}

	public AddressComponentsDtoImpl setStreet(String street) {
		this.street = street;
		return this;
	}

	public String getPostal() {
		return postal;
	}

	public AddressComponentsDtoImpl setPostal(String postal) {
		this.postal = postal;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getLatitude() {
		return latitude;
	}

	public AddressComponentsDtoImpl setLatitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}

	public Double getLongitude() {
		return longitude;
	}

	public AddressComponentsDtoImpl setLongitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}

	@Override
	public String toString() {
		return "AddressComponentsDtoImpl [country=" + country + ", locality=" + locality + ", street=" + street
				+ ", postal=" + postal + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
