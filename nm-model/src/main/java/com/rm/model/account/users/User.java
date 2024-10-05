package com.rm.model.account.users;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Email;

import com.rm.model.commons.Address;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_account_user_user")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_account_user_user", sequenceName = "seq_account_user_user", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_account_user_user")
	private Long id;
	@Column(nullable = false, updatable = false)
	private Date createdAt = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	private Date updatedAt = new Date();
	//
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String firstname;
	@Email
	@Column(nullable = false)
	protected String email;
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	private Address address;
	private String image;

	public String getEmail() {
		return email;
	}

	public String getImage() {
		return image;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
