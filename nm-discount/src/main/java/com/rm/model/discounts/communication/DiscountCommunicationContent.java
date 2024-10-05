package com.rm.model.discounts.communication;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;

import org.hibernate.annotations.UpdateTimestamp;

import com.rm.contract.discounts.constants.CommunicationType;
import com.rm.utils.I18nUtils;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_communication_content")
public class DiscountCommunicationContent implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_discount_communication_content", sequenceName = "seq_discount_communication_content", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_discount_communication_content")
	protected Long id;

	@Column(nullable = false, updatable = false)
	protected Date created = new Date();
	@Column(nullable = false)
	@UpdateTimestamp
	protected Date updated = new Date();
	@Column(columnDefinition = "TEXT", nullable = false)
	protected String content;
	@Column(columnDefinition = "TEXT", nullable = false)
	protected String contentText;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	protected CommunicationType type;

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getContent() {
		return content;
	}

	public String getContent(String lang) {
		return I18nUtils.getByLangage(lang, getContent());
	}

	public String getContentText(String lang) {
		return I18nUtils.getByLangage(lang, getContentText());
	}

	public String getContentText() {
		return contentText;
	}

	public Date getCreated() {
		return created;
	}

	public Long getId() {
		return id;
	}

	public CommunicationType getType() {
		return type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setType(CommunicationType type) {
		this.type = type;
	}

	public void addContent(String locale, String desc) {
		setContent(I18nUtils.addLangage(locale, getContent(), desc));
	}

	public void addContentText(String locale, String desc) {
		setContentText(I18nUtils.addLangage(locale, getContentText(), desc));
	}

	@AssertTrue(message = "discount.comm.errors.hascontent")
	protected boolean hasContent() {
		return I18nUtils.getLangages(content).size() > 0;
	}

	public List<String> getLangs() {
		return I18nUtils.getLangages(content);
	}
}
