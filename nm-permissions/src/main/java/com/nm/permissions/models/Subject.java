package com.nm.permissions.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.nm.utils.hibernate.impl.ModelTimeable;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(schema="mod_perm",name = "perm_subject")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Subject extends ModelTimeable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "subject_seq", sequenceName = "subject_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_seq")
	private Long id;
	@Column(nullable = false)
	private int priotity;
	@OneToOne(optional = false, mappedBy = "subject")
	private PermissionGrid grid;

	public PermissionGrid getGrid() {
		return grid;
	}

	public void setGrid(PermissionGrid grid) {
		this.grid = grid;
	}

	public int getPriotity() {
		return priotity;
	}

	public void setPriotity(int priotity) {
		this.priotity = priotity;
	}

	public Long getId() {
		return id;
	}

	public Subject setId(Long id) {
		this.id = id;
		return this;
	}

}
