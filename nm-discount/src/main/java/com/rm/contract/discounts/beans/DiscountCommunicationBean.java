package com.rm.contract.discounts.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect()
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountCommunicationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean show = false;
	private List<DiscountCommunicationContentBean> contents = new ArrayList<DiscountCommunicationContentBean>();

	public DiscountCommunicationBean() {
	}

	public List<DiscountCommunicationContentBean> getContents() {
		return contents;
	}

	public void setContents(List<DiscountCommunicationContentBean> contents) {
		this.contents = contents;
	}

	public DiscountCommunicationBean addContents(DiscountCommunicationContentBean contents) {
		this.contents.add(contents);
		return this;
	}

	public boolean isShow() {
		return show;
	}

	public DiscountCommunicationBean setShow(boolean show) {
		this.show = show;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
