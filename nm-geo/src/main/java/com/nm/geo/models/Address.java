package com.nm.geo.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.nm.geo.constants.AddressType;
import com.nm.geo.constants.AddressType.AddressTypeDefault;
import com.nm.utils.json.EnumHibernateType;
import com.nm.utils.node.ModelNode;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(schema = "mod_geo", name = "nm_geo_address")
public class Address implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_geo_address", sequenceName = "seq_geo_address", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_geo_address")
	protected Long id;
	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	/**
	 * 
	 */
	@Column()
	protected String name;
	@Column(nullable = false, columnDefinition = "TEXT")
	protected String street;
	@Column(nullable = false)
	protected String postal;
	@Column(nullable = false)
	protected String locality;
	@Column(nullable = false)
	protected String country;
	@Column(nullable = false, columnDefinition = "TEXT")
	protected String geocode;
	/**
	 * Coords
	 */
	@Column(nullable = false)
	protected Double latitude;
	@Column(nullable = false)
	protected Double longitude;
	/**
	 * Google Object
	 */
	@Column(nullable = false, columnDefinition = "TEXT")
	protected String details;
	/**
	 * Optionnal
	 */
	@Column(columnDefinition = "TEXT")
	protected String complement;
	@ManyToOne(optional = true)
	private ModelNode about;
	/**
	 * 
	 * @return
	 */
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private AddressType type = AddressTypeDefault.Secondary;

	public AddressType getType() {
		return type;
	}

	public void setType(AddressType type) {
		this.type = type;
	}

	public ModelNode getAbout() {
		return about;
	}

	public void setAbout(ModelNode about) {
		this.about = about;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getComplement() {
		return complement;
	}

	@Access(AccessType.PROPERTY)
	public Double getCosLat() {
		return Math.cos(getLatRadian());
	}

	public String getCountry() {
		return country;
	}

	public Date getCreated() {
		return created;
	}

	public String getDetails() {
		return details;
	}

	public String getGeocode() {
		return geocode;
	}

	public Long getId() {
		return id;
	}

	public Double getLatitude() {
		return latitude;
	}

	@Access(AccessType.PROPERTY)
	public Double getLatRadian() {
		return Math.toRadians(getLatitude());
	}

	public String getLocality() {
		return locality;
	}

	public Double getLongitude() {
		return longitude;
	}

	@Access(AccessType.PROPERTY)
	public Double getLonRadian() {
		return Math.toRadians(getLongitude());
	}

	public String getName() {
		return name;
	}

	public String getPostal() {
		return postal;
	}

	@Access(AccessType.PROPERTY)
	public Double getSinLat() {
		return Math.sin(getLatRadian());
	}

	public String getStreet() {
		return street;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public void setCosLat(Double d) {
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setGeocode(String geocode) {
		this.geocode = geocode;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLatRadian(Double rad) {
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setLonRadian(Double rad) {
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public void setSinLat(Double d) {
	}

	public void setStreet(String street) {
		this.street = street;
	}

}
