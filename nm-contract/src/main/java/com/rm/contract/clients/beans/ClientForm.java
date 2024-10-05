package com.rm.contract.clients.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rm.app.geo.AddressFormDto;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected Date created = new Date();
	protected Date birthDate = new Date();
	protected String email;
	protected String firstname;
	protected String name;
	protected String phone;
	protected String reference;
	protected Collection<AddressFormDto> addresses = new HashSet<AddressFormDto>();
	protected AddressFormDto address = new AddressFormDto();
	protected boolean ignore;

	public boolean isIgnore() {
		return ignore;
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	public String getReference() {
		return reference;
	}

	public Collection<AddressFormDto> getAddresses() {
		return addresses;
	}

	public void setAddresses(Collection<AddressFormDto> addresses) {
		this.addresses = addresses;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public AddressFormDto getAddress() {
		return address;
	}

	public void setAddress(AddressFormDto address) {
		this.address = address;
	}

}
