package com.nm.users.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Email;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "user_invitation")
public class Invitation {

	@Id
	@SequenceGenerator(name = "invitation_seq", sequenceName = "invitation_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invitation_seq")
	private Long id;
	@Column(updatable = false)
	private Date created = new Date();
	@UpdateTimestamp
	private Date updated = new Date();
	@ManyToOne(optional = false)
	private User sponsor;
	@Column(nullable = false)
	@Email
	private String invited;
	@ManyToOne(optional = true)
	private UserGroup group;

	public UserGroup getGroup() {
		return group;
	}

	public void setGroup(UserGroup group) {
		this.group = group;
	}

	public Date getCreated() {
		return created;
	}

	public Long getId() {
		return id;
	}

	public String getInvited() {
		return invited;
	}

	public User getSponsor() {
		return sponsor;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInvited(String invited) {
		this.invited = invited;
	}

	public void setSponsor(User sponsor) {
		this.sponsor = sponsor;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
