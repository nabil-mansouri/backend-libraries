package com.rm.model.account.modules;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import com.rm.account.constants.ModuleType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_account_module")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Module implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_account_module", sequenceName = "seq_account_module", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_account_module")
	private Long id;
	@Column(nullable = false, updatable = false)
	private Date createdAt = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	private Date updatedAt = new Date();
	//
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ModuleType type;

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

	public ModuleType getType() {
		return type;
	}

	public void setType(ModuleType type) {
		this.type = type;
	}

}
