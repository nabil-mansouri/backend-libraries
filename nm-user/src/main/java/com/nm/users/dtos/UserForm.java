package com.nm.users.dtos;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import com.nm.users.constants.CivilityType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class UserForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private CivilityType type;
	private String name;
	private String firstname;
	private String email;
	private String personnalEmail;
	private String phone;
	private String token;
	private Long idAvatar;
	private boolean enable;
	//
	private boolean hasAuthentication;
	private AuthenticationForm authentication = new AuthenticationForm();
	//
	private Collection<UserServiceForm> services = new HashSet<UserServiceForm>();

	public UserForm() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserForm other = (UserForm) obj;
		if (authentication == null) {
			if (other.authentication != null)
				return false;
		} else if (!authentication.equals(other.authentication))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (hasAuthentication != other.hasAuthentication)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idAvatar == null) {
			if (other.idAvatar != null)
				return false;
		} else if (!idAvatar.equals(other.idAvatar))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (personnalEmail == null) {
			if (other.personnalEmail != null)
				return false;
		} else if (!personnalEmail.equals(other.personnalEmail))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (services == null) {
			if (other.services != null)
				return false;
		} else if (!services.equals(other.services))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public AuthenticationForm getAuthentication() {
		return authentication;
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

	public Long getIdAvatar() {
		return idAvatar;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getName() {
		return name;
	}

	public String getPersonnalEmail() {
		return personnalEmail;
	}

	public String getPhone() {
		return phone;
	}

	public Collection<UserServiceForm> getServices() {
		return services;
	}

	public String getToken() {
		return token;
	}

	public CivilityType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authentication == null) ? 0 : authentication.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + (hasAuthentication ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idAvatar == null) ? 0 : idAvatar.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((personnalEmail == null) ? 0 : personnalEmail.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((services == null) ? 0 : services.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	public boolean isHasAuthentication() {
		return hasAuthentication;
	}

	public void setAuthentication(AuthenticationForm authentication) {
		this.authentication = authentication;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setHasAuthentication(boolean hasAuthentication) {
		this.hasAuthentication = hasAuthentication;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIdAvatar(Long idAvatar) {
		this.idAvatar = idAvatar;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPersonnalEmail(String personnalEmail) {
		this.personnalEmail = personnalEmail;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setServices(Collection<UserServiceForm> services) {
		this.services = services;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setType(CivilityType type) {
		this.type = type;
	}

}
