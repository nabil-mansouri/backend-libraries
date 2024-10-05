package com.rm.model.clients.criterias;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_client_criterias_rule_position")
public class CriteriaRulePosition extends CriteriaRule {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CriteriaRulePosition() {
	}

	@Column(nullable = false)
	private Double latitude;
	@Column(nullable = false)
	private Double longitude;
	@Column(nullable = false)
	private Double radius;

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
