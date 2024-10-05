package com.nm.app.history;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.nm.utils.hibernate.impl.ModelTimeable;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_app_history", schema = "mod_app")
@Inheritance(strategy = InheritanceType.JOINED)
public class History extends ModelTimeable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_nm_app_history", schema = "mod_app", sequenceName = "seq_nm_app_history", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_nm_app_history")
	private Long id;
	@Column(nullable = true)
	private Date happendAt;
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	private HistorySubject subject;
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	private HistoryActor actor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getHappendAt() {
		return happendAt;
	}

	public void setHappendAt(Date when) {
		this.happendAt = when;
	}

	public HistorySubject getSubject() {
		return subject;
	}

	public void setSubject(HistorySubject subject) {
		this.subject = subject;
	}

	public HistoryActor getActor() {
		return actor;
	}

	public void setActor(HistoryActor actor) {
		this.actor = actor;
	}

}
