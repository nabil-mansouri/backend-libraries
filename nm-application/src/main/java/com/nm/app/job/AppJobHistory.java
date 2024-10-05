package com.nm.app.job;

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

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "app_appjob_history", schema = "mod_app")
public class AppJobHistory {
	@Id
	@SequenceGenerator(name = "app_appjob_history_seq", schema = "mod_app", sequenceName = "app_appjob_history_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_appjob_history_seq")
	private Long id;
	@Column(updatable = false)
	private Date started = new Date();
	@Column(nullable = true)
	private Date ended;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AppJobStatus status;
	@ManyToOne(optional = false)
	private AppJob job;
	@Column(nullable = true, columnDefinition = "TEXT")
	private String cause;

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppJob getJob() {
		return job;
	}

	public void setJob(AppJob job) {
		this.job = job;
	}

	public Date getStarted() {
		return started;
	}

	public void setStarted(Date started) {
		this.started = started;
	}

	public Date getEnded() {
		return ended;
	}

	public void setEnded(Date ended) {
		this.ended = ended;
	}

	public AppJobStatus getStatus() {
		return status;
	}

	public void setStatus(AppJobStatus status) {
		this.status = status;
	}

}
