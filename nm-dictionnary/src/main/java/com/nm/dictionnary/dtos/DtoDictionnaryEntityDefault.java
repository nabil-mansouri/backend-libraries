package com.nm.dictionnary.dtos;

import java.util.Collection;

import com.google.common.collect.Sets;
import com.nm.utils.node.DtoNode;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DtoDictionnaryEntityDefault implements DtoDictionnaryEntity {
	private static final long serialVersionUID = 1L;
	private Long dictionnaryEntityId;
	private String description;
	private DtoNode about = new DtoNode();
	private Collection<DtoDictionnaryValue> values = Sets.newHashSet();

	public DtoNode getAbout() {
		return about;
	}

	public void setAbout(DtoNode about) {
		this.about = about;
	}

	public Collection<DtoDictionnaryValue> getValues() {
		return values;
	}

	public int size() {
		return this.values.size();
	}

	public void add(DtoDictionnaryValue dto) {
		this.values.add(dto);
	}

	public void setValues(Collection<DtoDictionnaryValue> values) {
		this.values = values;
	}

	public DtoDictionnaryEntityDefault() {
	}

	public DtoDictionnaryEntityDefault(Long id) {
		setDictionnaryEntityId(id);
	}

	public Long getDictionnaryEntityId() {
		return dictionnaryEntityId;
	}

	public void setDictionnaryEntityId(Long id) {
		this.dictionnaryEntityId = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}