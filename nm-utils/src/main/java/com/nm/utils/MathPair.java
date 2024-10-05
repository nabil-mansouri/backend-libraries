package com.nm.utils;

import java.io.Serializable;

/**
 * 
 * @author MANSOURI Nabil
 *
 * @param <L>
 * @param <R>
 */
public class MathPair<L, R> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private L l;
	private R r;

	public MathPair() {
	}

	public MathPair(L l, R r) {
		this.l = l;
		this.r = r;
	}

	public L getL() {
		return l;
	}

	public R getR() {
		return r;
	}

	public void setL(L l) {
		this.l = l;
	}

	public void setR(R r) {
		this.r = r;
	}
}