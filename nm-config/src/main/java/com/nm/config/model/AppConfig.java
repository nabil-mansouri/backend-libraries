package com.nm.config.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import com.nm.config.constants.AppConfigKey;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_config_appconfig", schema = "mod_config", uniqueConstraints = @UniqueConstraint(columnNames = "key") )
public class AppConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_nm_config_appconfig", schema = "mod_config", sequenceName = "seq_nm_config_appconfig", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_nm_config_appconfig")
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String payload;

	@Column(nullable = false, updatable = false)
	private Date createdAt = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	private Date updatedAt = new Date();
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private AppConfigKey key;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppConfig other = (AppConfig) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (key != other.key)
			return false;
		if (payload == null) {
			if (other.payload != null)
				return false;
		} else if (!payload.equals(other.payload))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Long getId() {
		return id;
	}

	public String getPayload() {
		return payload;
	}

	public AppConfigKey getKey() {
		return key;
	}

	public void setKey(AppConfigKey key) {
		this.key = key;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((payload == null) ? 0 : payload.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Config [id=" + id + ", payload=" + payload + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", key=" + key + "]";
	}

	@PreUpdate
	public void onUpdate() {
		this.setUpdatedAt(new Date());
	}

}
