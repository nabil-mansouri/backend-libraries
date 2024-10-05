package com.nm.products.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_product_instance_part")
public class ProductInstancePart implements Serializable {

	private static final long serialVersionUID = 1978550118107172866L;

	@Id
	@SequenceGenerator(name = "seq_product_instance_part", sequenceName = "seq_product_instance_part", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_product_instance_part")
	protected Long id;
	@NotNull(message = "{part.notNull}")
	protected Long idPart;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@NotNull(message = "{child.notNull}")
	protected ProductInstance child;
	@Column(nullable = false)
	private Boolean mandatory = true;

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public ProductInstance getChild() {
		return child;
	}

	public void setChild(ProductInstance child) {
		this.child = child;
	}

	public Long getIdPart() {
		return idPart;
	}

	public void setIdPart(Long idPart) {
		this.idPart = idPart;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@AssertTrue(message = "product.instance.hasselected")
	protected boolean hasChild() {
		if (this.mandatory) {
			return this.child != null;
		} else {
			return true;
		}
	}

}
