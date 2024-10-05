package com.nm.app.log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "app_general_log", schema = "mod_app")
public class GeneralLog {
	@Id
	@SequenceGenerator(name = "app_general_log_seq", schema = "mod_app", sequenceName = "app_general_log_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_general_log_seq")
	private Long id;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private GeneralLogLevel level;
	@Column(columnDefinition = "TEXT")
	private String commentaires;
	@Column(updatable = false, nullable = false)
	private Date createdAt = new Date();

	protected GeneralLog() {
	}

	public GeneralLog(GeneralLogLevel level, String commentaires) {
		super();
		this.level = level;
		this.commentaires = commentaires;
	}

	public String getCommentaires() {
		return commentaires;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Long getId() {
		return id;
	}

	public GeneralLogLevel getLevel() {
		return level;
	}

	public void setCommentaires(String commentaires) {
		this.commentaires = commentaires;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLevel(GeneralLogLevel level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "GeneralLog [id=" + id + ", level=" + level + ", commentaires=" + commentaires + ", createdAt=" + createdAt + "]";
	}

}
