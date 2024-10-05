package com.rm.contract.discounts.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rm.contract.discounts.constants.DiscountOperationType;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountRuleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean operation;
	private boolean reduction;
	private boolean replacePrice;
	private boolean free;
	private boolean isSpecial;
	private boolean isGift;
	//
	private Double operationValue;
	private DiscountOperationType operationType;
	//
	private DiscountRuleSubjectBean subject = new DiscountRuleSubjectBean();
	private Collection<DiscountRuleConditionBean> conditions = new ArrayList<DiscountRuleConditionBean>();

	public Collection<DiscountRuleConditionBean> getConditions() {
		return conditions;
	}

	public DiscountRuleBean setConditions(Collection<DiscountRuleConditionBean> conditions) {
		this.conditions = conditions;
		return this;
	}

	public DiscountRuleSubjectBean getSubject() {
		return subject;
	}

	public DiscountRuleBean setSubject(DiscountRuleSubjectBean subject) {
		this.subject = subject;
		return this;
	}

	public boolean isOperation() {
		return operation;
	}

	public DiscountRuleBean setOperation(boolean operation) {
		this.operation = operation;
		return this;
	}

	public boolean isReduction() {
		return reduction;
	}

	public DiscountRuleBean setReduction(boolean reduction) {
		this.reduction = reduction;
		return this;
	}

	public boolean isReplacePrice() {
		return replacePrice;
	}

	public DiscountRuleBean setReplacePrice(boolean replacePrice) {
		this.replacePrice = replacePrice;
		return this;
	}

	public boolean isFree() {
		return free;
	}

	public DiscountRuleBean setFree(boolean free) {
		this.free = free;
		return this;
	}

	public boolean isSpecial() {
		return isSpecial;
	}

	public DiscountRuleBean setSpecial(boolean isSpecial) {
		this.isSpecial = isSpecial;
		return this;
	}

	public boolean isGift() {
		return isGift;
	}

	public DiscountRuleBean setGift(boolean isGift) {
		this.isGift = isGift;
		return this;
	}

	public Double getOperationValue() {
		return operationValue;
	}

	public DiscountRuleBean setOperationValue(Double operationValue) {
		this.operationValue = operationValue;
		return this;
	}

	public DiscountOperationType getOperationType() {
		return operationType;
	}

	public DiscountRuleBean setOperationType(DiscountOperationType operationType) {
		this.operationType = operationType;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
