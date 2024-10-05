package com.nm.dictionnary.dtos;

import java.util.Collection;

import com.google.common.collect.Sets;
import com.nm.dictionnary.constants.EnumDictionnaryDomain;
import com.nm.dictionnary.constants.EnumDictionnaryState;
import com.nm.dictionnary.constants.EnumDictionnaryType;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DtoDictionnaryEntryDefault implements DtoDictionnaryEntry {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String key;
	private EnumDictionnaryDomain domain;
	private EnumDictionnaryState state;
	private EnumDictionnaryType type;
	private Collection<DtoDictionnaryValueDefault> values = Sets.newHashSet();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public EnumDictionnaryDomain getDomain() {
		return domain;
	}

	public void setDomain(EnumDictionnaryDomain domain) {
		this.domain = domain;
	}

	public EnumDictionnaryState getState() {
		return state;
	}

	public void setState(EnumDictionnaryState state) {
		this.state = state;
	}

	public EnumDictionnaryType getType() {
		return type;
	}

	public void setType(EnumDictionnaryType type) {
		this.type = type;
	}

	public Collection<DtoDictionnaryValueDefault> getValues() {
		return values;
	}

	public void setValues(Collection<DtoDictionnaryValueDefault> values) {
		this.values = values;
	}

}
