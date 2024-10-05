package com.rm.contract.clients.builders;

import java.util.Collection;
import java.util.Date;

import com.rm.app.geo.AddressFormDto;
import com.rm.contract.clients.beans.ClientForm;

/**
 * 
 * @author Nabil
 * 
 */
public class ClientFormBuilder {

	public static ClientFormBuilder get() {
		return new ClientFormBuilder();
	}

	private ClientForm bean = new ClientForm();

	private ClientFormBuilder() {

	}

	public ClientForm build() {
		return bean;
	}

	public ClientFormBuilder withIgnore(boolean ignore) {
		bean.setIgnore(ignore);
		return this;
	}

	public ClientFormBuilder withAddresses(Collection<AddressFormDto> addresses) {
		bean.setAddresses(addresses);
		return this;
	}

	public ClientFormBuilder withReference(String reference) {
		bean.setReference(reference);
		return this;
	}

	public ClientFormBuilder withId(Long id) {
		bean.setId(id);
		return this;
	}

	public ClientFormBuilder withCreated(Date created) {
		bean.setCreated(created);
		return this;
	}

	public ClientFormBuilder withName(String name) {
		bean.setName(name);
		return this;
	}

	public ClientFormBuilder withFirstname(String firstname) {
		bean.setFirstname(firstname);
		return this;
	}

	public ClientFormBuilder withPhone(String phone) {
		bean.setPhone(phone);
		return this;
	}

	public ClientFormBuilder withEmail(String email) {
		bean.setEmail(email);
		return this;
	}

	public ClientFormBuilder withBirthDate(Date birthDate) {
		bean.setBirthDate(birthDate);
		return this;
	}

	public ClientFormBuilder withAddress(AddressFormDto address) {
		bean.setAddress(address);
		return this;
	}

	public ClientFormBuilder withAddress(AddressFormBuilder builder) {
		bean.setAddress(builder.build());
		return this;
	}

}
