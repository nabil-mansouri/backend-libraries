package com.nm.prices.model.subject;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_price_subject_default")
public class PriceSubjectDefault extends PriceSubject {

	protected static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
