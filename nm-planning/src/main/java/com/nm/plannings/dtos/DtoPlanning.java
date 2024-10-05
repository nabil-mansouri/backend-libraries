package com.nm.plannings.dtos;

import java.util.HashMap;
import java.util.Map;

import com.nm.plannings.constants.SlotType;
import com.nm.utils.dtos.Dto;
import com.nm.utils.node.DtoNode;

/**
 * 
 * @author nabilmansouri
 * 
 */
public class DtoPlanning implements Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private DtoNode about = new DtoNode();
	private Map<SlotType, DtoSlotOccurrenceGroup> groups = new HashMap<SlotType, DtoSlotOccurrenceGroup>();

	public DtoPlanning() {
	}
	

	public DtoPlanning(Long id) {
		super();
		this.id = id;
	}


	public DtoNode getAbout() {
		return about;
	}

	public DtoPlanning setAbout(DtoNode about) {
		this.about = about;
		return this;
	}

	public Long getId() {
		return id;
	}

	public DtoPlanning setId(Long id) {
		this.id = id;
		return this;
	}

	public Map<SlotType, DtoSlotOccurrenceGroup> getGroups() {
		return groups;
	}

	public void setGroups(Map<SlotType, DtoSlotOccurrenceGroup> groups) {
		this.groups = groups;
	}
}
