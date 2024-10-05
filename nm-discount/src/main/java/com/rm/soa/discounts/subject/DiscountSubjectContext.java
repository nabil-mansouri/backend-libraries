package com.rm.soa.discounts.subject;

import java.util.ArrayList;
import java.util.List;

import com.rm.utils.graphs.AbstractGraph;

/**
 * 
 * @author Nabil
 * 
 */
public class DiscountSubjectContext {
	private List<CartProductBean> foundedRows = new ArrayList<CartProductBean>();
	private List<AbstractGraph> foundedNodes = new ArrayList<AbstractGraph>();
	private List<TaxDefFormBean> foundedTax = new ArrayList<TaxDefFormBean>();

	public List<TaxDefFormBean> getFoundedTax() {
		return foundedTax;
	}

	public void setFoundedTax(List<TaxDefFormBean> foundedTax) {
		this.foundedTax = foundedTax;
	}

	public List<AbstractGraph> getFoundedNodes() {
		return foundedNodes;
	}

	public void setFoundedNodes(List<AbstractGraph> foundedNodes) {
		this.foundedNodes = foundedNodes;
	}

	public List<CartProductBean> getFoundedRows() {
		return foundedRows;
	}

	public void setFoundedRows(List<CartProductBean> foundedRows) {
		this.foundedRows = foundedRows;
	}
}
