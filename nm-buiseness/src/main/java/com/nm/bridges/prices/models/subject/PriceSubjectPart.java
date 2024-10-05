package com.nm.bridges.prices.models.subject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.nm.prices.model.subject.PriceSubject;
import com.nm.products.model.ProductDefinitionPart;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_price_subject_part")
public class PriceSubjectPart extends PriceSubject {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	protected ProductDefinitionPart part;
	@Column(nullable = false, insertable = false, updatable = false, name = "part_id")
	private Long partId;

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public ProductDefinitionPart getPart() {
		return part;
	}

	public void setPart(ProductDefinitionPart part) {
		this.part = part;
	}

}
