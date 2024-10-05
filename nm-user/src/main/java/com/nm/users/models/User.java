package com.nm.users.models;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Email;

import com.nm.datas.models.AppData;
import com.nm.users.constants.CivilityType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "user_user")
public class User {

	@Id
	@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	private Long id;

	@Column(updatable = false)
	private Date created = new Date();

	@UpdateTimestamp
	private Date updated = new Date();
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String firstname;
	@Column(nullable = false)
	@Email(message = "user.email.bad")
	private String email;
	@Email(message = "user.email.bad")
	private String personnalEmail;
	private String phone;
	@Column(nullable = false)
	private boolean master;
	@Column(nullable = false)
	private boolean enable;
	@OneToOne(optional = true)
	private AppData avatar;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private CivilityType civility;
	@ManyToMany(mappedBy = "users")
	private Collection<UserGroup> groups = new HashSet<UserGroup>();

	public Collection<UserGroup> getGroups() {
		return groups;
	}

	public void setGroups(Collection<UserGroup> groups) {
		this.groups = groups;
	}

	public CivilityType getCivility() {
		return civility;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void setCivility(CivilityType civility) {
		this.civility = civility;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (avatar == null) {
			if (other.avatar != null)
				return false;
		} else if (!avatar.equals(other.avatar))
			return false;
		if (civility != other.civility)
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (master != other.master)
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
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		return true;
	}

	public AppData getAvatar() {
		return avatar;
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

	public String getName() {
		return name;
	}

	public Date getUpdated() {
		return updated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((avatar == null) ? 0 : avatar.hashCode());
		result = prime * result + ((civility == null) ? 0 : civility.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (master ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((personnalEmail == null) ? 0 : personnalEmail.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
		return result;
	}

	public boolean isMaster() {
		return master;
	}

	public void setAvatar(AppData avatar) {
		this.avatar = avatar;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getPersonnalEmail() {
		return personnalEmail;
	}

	public void setPersonnalEmail(String personnalEmail) {
		this.personnalEmail = personnalEmail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
