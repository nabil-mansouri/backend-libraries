package com.nm.auths.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.nm.auths.constants.AuthenticationState;
import com.nm.auths.constants.AuthenticationType;
import com.nm.utils.hibernate.impl.ModelTimeable;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(schema="mod_auth",name = "nm_auth_authentication")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Authentication extends ModelTimeable {

	private static final long serialVersionUID = 1978550118107172866L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_nm_auth_authentication", sequenceName = "seq_nm_auth_authentication", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_nm_auth_authentication")
	protected Long id;
	//
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private AuthenticationType type;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AuthenticationState state;
	@ManyToOne(optional = false)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		user.getAuthentications().add(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AuthenticationType getType() {
		return type;
	}

	public void setType(AuthenticationType type) {
		this.type = type;
	}

	public AuthenticationState getState() {
		return state;
	}

	public void setState(AuthenticationState state) {
		this.state = state;
	}

}
