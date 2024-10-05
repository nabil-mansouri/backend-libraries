package com.nm.datas.models;

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

import org.hibernate.annotations.Type;

import com.nm.datas.constants.AppDataContentKind;
import com.nm.datas.constants.AppDataDestination;
import com.nm.utils.hibernate.impl.ModelTimeable;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_app_data", schema = "mod_data")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AppData extends ModelTimeable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "seq_nm_app_data", schema = "mod_data", sequenceName = "seq_nm_app_data", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nm_app_data")
	private Long id;
	@Column(columnDefinition = "TEXT", nullable = false)
	private String fullName;
	@Column(columnDefinition = "TEXT", nullable = false)
	private String type;
	@Column(nullable = true)
	private int size;
	@Column(columnDefinition = "TEXT", nullable = false)
	private String hash;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AppDataContentKind kind;
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private AppDataDestination destination;

	public AppDataContentKind getKind() {
		return kind;
	}

	public void setKind(AppDataContentKind kind) {
		this.kind = kind;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public AppDataDestination getDestination() {
		return destination;
	}

	public void setDestination(AppDataDestination destination) {
		this.destination = destination;
	}

	public int getSize() {
		return size;
	}

	public String getType() {
		return type;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
