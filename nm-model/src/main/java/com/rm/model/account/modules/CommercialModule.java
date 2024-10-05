package com.rm.model.account.modules;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_account_module_commercial")
@Inheritance(strategy = InheritanceType.JOINED)
public class CommercialModule extends Module {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private Date beginDate;
	@Column(nullable = false)
	private Date endDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
