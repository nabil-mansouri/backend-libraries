package com.nm.app.job;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "app_appjob", schema = "mod_app")
public class AppJob {
	@Id
	@SequenceGenerator(name = "app_appjob_seq", schema = "mod_app", sequenceName = "app_appjob_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_appjob_seq")
	private Long id;
	@Column(updatable = false)
	private Date created = new Date();
	@UpdateTimestamp
	private Date updated = new Date();
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private AppJobType type;
	@Column(nullable = false, columnDefinition = "TEXT")
	private String cron;
	@Column(nullable = false)
	private boolean enable;

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public AppJobType getType() {
		return type;
	}

	public void setType(AppJobType type) {
		this.type = type;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

}
