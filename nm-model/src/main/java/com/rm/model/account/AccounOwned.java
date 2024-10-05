package com.rm.model.account;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_account_owned")
public abstract class AccounOwned implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_account_owned", sequenceName = "seq_account_owned", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_account_owned")
	private Long id;
	
}
