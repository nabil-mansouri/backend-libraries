package com.nm.prices.dtos.forms.old;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nm.prices.dtos.constants.PriceComputeError;
import com.nm.utils.graphs.AbstractGraph;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceComputeBean extends AbstractGraph implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Long id;
//	private OrderType priceType;
	private List<String> strategies = new ArrayList<String>();
	private Set<PriceComputeError> errors = new HashSet<PriceComputeError>();
	private Set<Double> history = new HashSet<Double>();
	private Double value = 0d;
	private Map<String, PriceComputeBean> additionnals = new HashMap<String, PriceComputeBean>();
	private String path = "";
	//
//	private OrderType priceTypeUsed;

	public PriceComputeBean() {
	}

	public void addAdditionnals(String path, PriceComputeBean child) {
		this.additionnals.put(path, child);
		child.setPath(path);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PriceComputeBean other = (PriceComputeBean) obj;
		if (additionnals == null) {
			if (other.additionnals != null)
				return false;
		} else if (!additionnals.equals(other.additionnals))
			return false;
		if (errors == null) {
			if (other.errors != null)
				return false;
		} else if (!errors.equals(other.errors))
			return false;
		if (history == null) {
			if (other.history != null)
				return false;
		} else if (!history.equals(other.history))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
//		if (priceType != other.priceType)
//			return false;
//		if (priceTypeUsed != other.priceTypeUsed)
//			return false;
		if (strategies == null) {
			if (other.strategies != null)
				return false;
		} else if (!strategies.equals(other.strategies))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public Map<String, PriceComputeBean> getAdditionnals() {
		return additionnals;
	}

	public List<? extends AbstractGraph> children() {
		Collection<PriceComputeBean> children = this.additionnals.values();
		return new ArrayList<AbstractGraph>(children);
	}

	@Override
	public void children(List<? extends AbstractGraph> g) {

	}

	public Set<PriceComputeError> getErrors() {
		return errors;
	}

	public Set<Double> getHistory() {
		return history;
	}

	public Long getId() {
		return id;
	}

	public String nodeId() {
		if (id != null) {
			return this.id.toString();
		} else {// MUST BE LIKE THAT
			return "";
		}
	}

	public String nodetype() {
		return "Price";
	}

	public String getPath() {
		return path;
	}

//	public OrderType getPriceType() {
//		return priceType;
//	}
//
//	public OrderType getPriceTypeUsed() {
//		return priceTypeUsed;
//	}

	public List<String> getStrategies() {
		return strategies;
	}

	public Double getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((additionnals == null) ? 0 : additionnals.hashCode());
		result = prime * result + ((errors == null) ? 0 : errors.hashCode());
		result = prime * result + ((history == null) ? 0 : history.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
//		result = prime * result + ((priceType == null) ? 0 : priceType.hashCode());
//		result = prime * result + ((priceTypeUsed == null) ? 0 : priceTypeUsed.hashCode());
		result = prime * result + ((strategies == null) ? 0 : strategies.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public void removeChild(AbstractGraph graph) {
		this.additionnals.remove(graph);
	}

	public void setAdditionnals(Map<String, PriceComputeBean> additionnals) {
		this.additionnals = additionnals;
	}

	public void setErrors(Set<PriceComputeError> errors) {
		this.errors = errors;
	}

	public void setHistory(Set<Double> history) {
		this.history = history;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPath(String path) {
		this.path = path;
	}

	// public void setPriceType(OrderType priceType) {
	// this.priceType = priceType;
	// }
	//
	// public void setPriceTypeUsed(OrderType priceTypeUsed) {
	// this.priceTypeUsed = priceTypeUsed;
	// }

	public void setStrategies(List<String> strategies) {
		this.strategies = strategies;
	}

	public void setValue(Double value) {
		this.history.add(new Double(value));
		this.value = value;
	}

}
