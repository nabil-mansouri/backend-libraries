package com.nm.utils.node;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class DtoNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ModelNodeType tye;
	private BigInteger uuid;
	private String label;

	public DtoNode() {
	}

	public DtoNode(BigInteger uuid) {
		super();
		this.uuid = uuid;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ModelNodeType getTye() {
		return tye;
	}

	public void setTye(ModelNodeType tye) {
		this.tye = tye;
	}

	public BigInteger getUuid() {
		return uuid;
	}

	public void setUuid(BigInteger uuid) {
		this.uuid = uuid;
	}

}
