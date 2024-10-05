package com.rm.model.clients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.AssertTrue;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Email;
import org.joda.time.MutableDateTime;

import com.google.common.base.Strings;
import com.rm.model.commons.Address;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_client_client", uniqueConstraints = @UniqueConstraint(columnNames = "reference"))
public class Client implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_client_client", sequenceName = "seq_client_client", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_client_client")
	protected Long id;
	/**
	 * Dates
	 */
	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Column(nullable = false, updatable = false)
	protected Date subscribed = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	protected Date updated = new Date();
	/**
	 * 
	 */
	@Column(nullable = false)
	protected Date birthDate = new Date();
	@Email
	protected String email;
	@Column(nullable = false)
	protected String firstname;
	@Column(nullable = false)
	protected String name;
	protected String phone;
	@Column(nullable = false, updatable = false)
	protected String reference;
	private String image;
	/**
	 * Address
	 */
	@OneToMany(cascade = CascadeType.ALL)
	protected Collection<Address> adresses = new HashSet<Address>();
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	protected Address last;

	/**
	 * FOR SEARH
	 */
	@Column(nullable = false, columnDefinition = "TEXT")
	protected String search;
	/**
	 * TRACK
	 */
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	private ClientActions lastAction;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
	private List<ClientActions> actions = new ArrayList<ClientActions>();

	public List<ClientActions> getActions() {
		return actions;
	}

	public void setActions(List<ClientActions> actions) {
		this.actions = actions;
	}

	public ClientActions getLastAction() {
		return lastAction;
	}

	public void setLastAction(ClientActions lastAction) {
		this.lastAction = lastAction;
	}

	public void add(Address address) {
		this.last = address;
		if (!adresses.contains(address)) {
			this.adresses.add(address);
		}
	}

	public void addSearch(String t) {
		this.setSearch(StringUtils.appendIfMissing(this.getSearch(), StringUtils.lowerCase(t)));
	}

	@AssertTrue
	protected boolean assertEmailOrPhone() {
		return !Strings.isNullOrEmpty(email) || !Strings.isNullOrEmpty(phone);
	}

	public Collection<Address> getAdresses() {
		return adresses;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	@Column(nullable = false)
	@Access(AccessType.PROPERTY)
	public int getBirthdayDay() {
		return new MutableDateTime(birthDate).getDayOfMonth();
	}

	@Column(nullable = false)
	@Access(AccessType.PROPERTY)
	public int getBirthdayMonth() {
		return new MutableDateTime(birthDate).getMonthOfYear();
	}

	public Date getCreated() {
		return created;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstname() {
		return firstname;
	}

	public Long getId() {
		return id;
	}

	public String getImage() {
		return image;
	}

	public Address getLast() {
		return last;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getReference() {
		return reference;
	}

	public String getSearch() {
		return search;
	}

	public Date getSubscribed() {
		return subscribed;
	}

	//
	@Column(nullable = false)
	@Access(AccessType.PROPERTY)
	public int getSubscribedDay() {
		return new MutableDateTime(subscribed).getDayOfMonth();
	}

	@Column(nullable = false)
	@Access(AccessType.PROPERTY)
	public int getSubscribedMonth() {
		return new MutableDateTime(subscribed).getMonthOfYear();
	}

	public Date getUpdated() {
		return updated;
	}

	public void resetSearch() {
		this.setSearch("");
		this.addSearch(this.getReference());
	}

	public void setAdresses(Collection<Address> adresses) {
		this.adresses = adresses;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setBirthdayDay(int birthdayDay) {
		// empty
	}

	public void setBirthdayMonth(int birthdayMonth) {
		// Empty
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setEmail(String email) {
		this.email = email;
		this.addSearch(email);
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
		this.addSearch(firstname);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setLast(Address last) {
		this.last = last;
	}

	public void setName(String name) {
		this.name = name;
		this.addSearch(name);
	}

	public void setPhone(String phone) {
		this.phone = phone;
		this.addSearch(phone);
	}

	public void setReference(String reference) {
		this.reference = reference;
		this.addSearch(reference);
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public void setSubscribed(Date subscribed) {
		this.subscribed = subscribed;
	}

	public void setSubscribedDay(int birthdayDay) {
		// empty
	}

	public void setSubscribedMonth(int birthdayMonth) {
		// Empty
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public void add(ClientActions clientActions) {
		clientActions.setClient(this);
		this.setLastAction(clientActions);
		this.actions.add(clientActions);
	}

}
