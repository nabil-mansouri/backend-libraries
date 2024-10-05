package com.nm.plannings.splitters.impl;

import java.util.ArrayList;
import java.util.List;

import com.nm.plannings.dtos.DtoSlotOccurrence;

/**
 * 
 * @author Nabil
 * 
 */
public class SlotContextBean {
	public List<DtoSlotOccurrence> originals = new ArrayList<DtoSlotOccurrence>();
	public List<DtoSlotOccurrence> result = new ArrayList<DtoSlotOccurrence>();

	public void swapToOriginals() {
		this.originals = new ArrayList<DtoSlotOccurrence>(result);
		this.result.clear();
	}

	public List<DtoSlotOccurrence> getOriginals() {
		return originals;
	}

	public void setOriginals(List<DtoSlotOccurrence> originals) {
		this.originals = originals;
	}

	public List<DtoSlotOccurrence> getResult() {
		return result;
	}

	public void setResult(List<DtoSlotOccurrence> result) {
		this.result = result;
	}

}
