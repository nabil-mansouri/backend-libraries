package com.nm.geo.dtos;

import java.util.Date;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nm.utils.json.RawJsonConverterIn;
import com.nm.utils.json.RawJsonConverterOut;
import com.nm.utils.node.DtoNode;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect()
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDtoImpl implements AddressDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected Date created = new Date();
	protected String name;
	protected String complement;
	@JsonSerialize(using = RawJsonConverterIn.class)
	@JsonDeserialize(using = RawJsonConverterOut.class)
	protected JSONObject details = new JSONObject();
	protected String geocode;
	protected AddressComponentsDtoImpl components = new AddressComponentsDtoImpl();
	private boolean failed;
	private boolean ignored;
	private boolean forceField;
	private DtoNode about = new DtoNode();

	public DtoNode getAbout() {
		return about;
	}

	public void setAbout(DtoNode about) {
		this.about = about;
	}

	public boolean isForceField() {
		return forceField;
	}

	public void setForceField(boolean forceField) {
		this.forceField = forceField;
	}

	public boolean isFailed() {
		return failed;
	}

	public boolean isIgnored() {
		return ignored;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}

	public void setIgnored(boolean ignored) {
		this.ignored = ignored;
	}

	public AddressDtoImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nm.geo.dtos.AdressDto#getId()
	 */
	public Long getId() {
		return id;
	}

	public AddressDto setId(Long id) {
		this.id = id;
		return this;
	}

	public Date getCreated() {
		return created;
	}

	public AddressDto setCreated(Date created) {
		this.created = created;
		return this;
	}

	public String getName() {
		return name;
	}

	public AddressDto setName(String name) {
		this.name = name;
		return this;
	}

	public String getComplement() {
		return complement;
	}

	public AddressDtoImpl setComplement(String complement) {
		this.complement = complement;
		return this;
	}

	public JSONObject getDetails() {
		return details;
	}

	public AddressDtoImpl setDetails(JSONObject details) {
		this.details = details;
		return this;
	}

	public String getGeocode() {
		return geocode;
	}

	public AddressDtoImpl setGeocode(String geocode) {
		this.geocode = geocode;
		return this;
	}

	public AddressComponentsDtoImpl getComponents() {
		return components;
	}

	public AddressDto setComponents(AddressComponentsDtoImpl component) {
		this.components = component;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AddressDtoImpl [id=" + id + ", created=" + created + ", name=" + name + ", complement=" + complement
				+ ", details=" + details + ", geocode=" + geocode + ", components=" + components + ", failed=" + failed
				+ ", ignored=" + ignored + ", forceField=" + forceField + "]";
	}

}
