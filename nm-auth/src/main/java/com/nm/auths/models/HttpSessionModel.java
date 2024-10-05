package com.nm.auths.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(schema="mod_auth",name = "nm_app_session")
public class HttpSessionModel {
	@Id
	@Column(name = "SESSION_ID", columnDefinition = "VARCHAR(36)")
	private String sessionId;
	@Column(name = "LAST_ACCESS_TIME", columnDefinition = "BIGINT", nullable = false)
	private Date lastAccessTime;
	@Column(name = "PRINCIPAL_NAME", columnDefinition = "VARCHAR(100)")
	private String principalName;
	@Lob
	@Column(name = "SESSION_BYTES", columnDefinition = "BYTEA")
	private byte[] sessionBytes;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public byte[] getSessionBytes() {
		return sessionBytes;
	}

	public void setSessionBytes(byte[] sessionBytes) {
		this.sessionBytes = sessionBytes;
	}

}
