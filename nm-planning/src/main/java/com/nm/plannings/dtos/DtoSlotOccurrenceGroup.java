package com.nm.plannings.dtos;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoSlotOccurrenceGroup extends ArrayList<DtoSlotOccurrence> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
