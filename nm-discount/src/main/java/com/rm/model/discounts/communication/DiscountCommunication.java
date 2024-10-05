package com.rm.model.discounts.communication;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;

import org.hibernate.annotations.UpdateTimestamp;

import com.google.common.base.Strings;
import com.rm.contract.discounts.constants.CommunicationType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_communication")
public class DiscountCommunication implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_discount_communication", sequenceName = "seq_discount_communication", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_discount_communication")
	protected Long id;

	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	protected Date updated = new Date();
	//
	protected String code;
	protected boolean hasCode;
	protected boolean autoCommunicate;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@MapKey(name = "type")
	private Map<CommunicationType, DiscountCommunicationContent> contents = new HashMap<CommunicationType, DiscountCommunicationContent>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isHasCode() {
		return hasCode;
	}

	public void setHasCode(boolean hasCode) {
		this.hasCode = hasCode;
	}

	public Map<CommunicationType, DiscountCommunicationContent> getContents() {
		return contents;
	}

	public void setContents(Map<CommunicationType, DiscountCommunicationContent> contents) {
		this.contents = contents;
	}

	public void putContents(CommunicationType type, DiscountCommunicationContent contents) {
		this.contents.put(type, contents);
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

	public boolean isAutoCommunicate() {
		return autoCommunicate;
	}

	public void setAutoCommunicate(boolean autoCommunicate) {
		this.autoCommunicate = autoCommunicate;
	}

	@AssertTrue(message = "discount.communication.error.hascode")
	protected boolean hasGotCode() {
		if (hasCode) {
			return !Strings.isNullOrEmpty(code);
		} else {
			return true;
		}
	}
}
