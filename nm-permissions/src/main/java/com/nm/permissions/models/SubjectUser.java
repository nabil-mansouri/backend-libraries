package com.nm.permissions.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.nm.auths.models.User;
import com.nm.permissions.constants.SubjectPriority;

/**
 * 
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@DiscriminatorValue("USER")
@Table(schema="mod_perm",name = "perm_subject_user")
public class SubjectUser extends Subject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	private User user;

	public SubjectUser() {
		setPriotity(SubjectPriority.User.getPriority());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
