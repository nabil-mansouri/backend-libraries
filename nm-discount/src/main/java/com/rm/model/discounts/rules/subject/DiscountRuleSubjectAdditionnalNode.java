package com.rm.model.discounts.rules.subject;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "nm_discount_rule_subject_additionnal_node")
public class DiscountRuleSubjectAdditionnalNode {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_discount_rule_subject_additionnal_node", sequenceName = "seq_discount_rule_subject_additionnal_node", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_discount_rule_subject_additionnal_node")
	protected Long id;
	@Column(nullable = false)
	private Long productRoot;
	@Column(nullable = false)
	private Long additionnal;
	@Column(nullable = false)
	private String path;
	@ManyToOne(optional = false)
	private DiscountRuleSubjectAdditionnal add;

	public DiscountRuleSubjectAdditionnal getAdd() {
		return add;
	}

	public void setAdd(DiscountRuleSubjectAdditionnal add) {
		this.add = add;
	}

	public Long getId() {
		return id;
	}

	public Long getProductRoot() {
		return productRoot;
	}

	public void setProductRoot(Long root) {
		this.productRoot = root;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdditionnal() {
		return additionnal;
	}

	public void setAdditionnal(Long product) {
		this.additionnal = product;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
