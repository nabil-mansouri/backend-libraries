package com.rm.model.account.permissions;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.rm.account.constants.PermissionAction;
import com.rm.model.account.modules.Module;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_account_perm_permission")
public class Permission implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_rm_account_perm_permission", sequenceName = "seq_rm_account_perm_permission", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_rm_account_perm_permission")
	private Long id;
	@Column(nullable = false, updatable = false)
	private Date createdAt = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	private Date updatedAt = new Date();
	//
	@ManyToOne(optional = false)
	private PermissionSubject subject;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PermissionAction action;
	@ManyToOne(optional=false)
	private Module resource;

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

	public PermissionSubject getSubject() {
		return subject;
	}

	public void setSubject(PermissionSubject subject) {
		this.subject = subject;
	}

	public PermissionAction getAction() {
		return action;
	}

	public void setAction(PermissionAction action) {
		this.action = action;
	}

	public Module getResource() {
		return resource;
	}

	public void setResource(Module resource) {
		this.resource = resource;
	}

}
