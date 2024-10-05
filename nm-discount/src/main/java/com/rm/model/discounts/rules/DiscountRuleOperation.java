package com.rm.model.discounts.rules;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.rm.contract.discounts.constants.DiscountOperationType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_discount_rule_operation")
public class DiscountRuleOperation extends DiscountRule {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DiscountOperationType operation;
	@Column(nullable = false)
	private Double value;

	public DiscountOperationType getOperation() {
		return operation;
	}

	public void setOperation(DiscountOperationType operation) {
		this.operation = operation;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	// TODO remplacer les builder par les setter?
	// TODO appliquer le pricefilter? operation selon le ordertype?
	// TODO faire un pricefilter (ordertype ou all ordertype)
	// TODO faut il d'autre r�gles?
	// TODO isoler l'operation replace? (pour utiliser le filter)

}
