package com.nm.app.triggers;

import java.util.Date;

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
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_app_trigger", schema = "mod_app")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Trigger extends ModelTimeable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_nm_app_trigger", schema = "mod_app", sequenceName = "seq_nm_app_trigger", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_nm_app_trigger")
	private Long id;
	@Column(nullable = true)
	private Date lastExecution;
	@Column(nullable = true)
	private Date scheduledAt;
	@Column(nullable = false)
	private int countAttempt;
	@Column(nullable = false)
	private int countExecution;
	@OneToOne(optional = false)
	private TriggerSubject subject;

	public int getCountExecution() {
		return countExecution;
	}

	public void setCountExecution(int countExecution) {
		this.countExecution = countExecution;
	}

	public TriggerSubject getSubject() {
		return subject;
	}

	public void setSubject(TriggerSubject subject) {
		this.subject = subject;
		this.subject.setTrigger(this);
	}

	public Long getId() {
		return id;
	}

	public Date getLastExecution() {
		return lastExecution;
	}

	public void setLastExecution(Date lastExecution) {
		this.lastExecution = lastExecution;
	}

	public Date getScheduledAt() {
		return scheduledAt;
	}

	public void setScheduledAt(Date scheduledAt) {
		this.scheduledAt = scheduledAt;
	}

	public int getCountAttempt() {
		return countAttempt;
	}

	public void setCountAttempt(int countAttempt) {
		this.countAttempt = countAttempt;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
