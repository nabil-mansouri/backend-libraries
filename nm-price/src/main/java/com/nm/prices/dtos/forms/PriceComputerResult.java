package com.nm.prices.dtos.forms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.SerializationUtils;

import com.nm.prices.dtos.constants.PriceComputeError;

/***
 * 
 * @author nabilmansouri
 *
 */
public class PriceComputerResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Set<PriceComputeError> errors = new HashSet<PriceComputeError>();
	private Collection<PriceComputerResultNode> nodes = new ArrayList<PriceComputerResultNode>();

	public Collection<PriceComputerResultNode> getNodes() {
		return nodes;
	}

	public void setNodes(Collection<PriceComputerResultNode> nodes) {
		this.nodes = nodes;
	}

	public Set<PriceComputeError> getErrors() {
		return errors;
	}

	public void setErrors(Set<PriceComputeError> errors) {
		this.errors = errors;
	}

	public boolean hasErrors() {
		return !getErrors().isEmpty();
	}

	public PriceComputerResult clone() {
		return SerializationUtils.clone(this);
	}

	public Double total() {
		Double t = 0d;
		for (PriceComputerResultNode n : this.nodes) {
			switch (n.getOperation()) {
			case Add:
				t += n.getValue();
				break;
			case Substract:
				t -= n.getValue();
				break;
			}
		}
		return t;
	}
}
