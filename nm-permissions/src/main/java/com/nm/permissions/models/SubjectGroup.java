package com.nm.permissions.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.nm.auths.models.UserGroup;
import com.nm.permissions.constants.SubjectPriority;

/**
 * 
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@DiscriminatorValue("GROUP")
@Table(schema="mod_perm",name = "perm_subject_group")
public class SubjectGroup extends Subject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	private UserGroup group;

	public SubjectGroup() {
		setPriotity(SubjectPriority.Group.getPriority());
	}

	public SubjectGroup(UserGroup userGroup) {
		setGroup(userGroup);
	}

	public UserGroup getGroup() {
		return group;
	}

	public void setGroup(UserGroup group) {
		this.group = group;
	}
}
