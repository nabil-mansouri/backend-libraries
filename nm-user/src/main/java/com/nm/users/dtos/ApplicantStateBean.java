package com.nm.users.dtos;

import java.util.Date;

import com.nm.users.constants.ApplicantStateType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class ApplicantStateBean {
	private Long id;
	private ApplicantStateType type;
	private Date createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ApplicantStateType getType() {
		return type;
	}

	public void setType(ApplicantStateType type) {
		this.type = type;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
