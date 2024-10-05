package com.nm.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FKBeanTest {
	
	private double				ident;
	
	private Collection<Object>	collection	= new ArrayList<Object>();
	
	private Set<?>					sets		= new HashSet<Object>(0);
	
	public double getIdent() {
		return ident;
	}
	
	public void setIdent(double ident) {
		this.ident = ident;
	}
	
	public Collection<Object> getCollection() {
		return collection;
	}
	
	public void setCollection(Collection<Object> collection) {
		this.collection = collection;
	}
	
	public Set<?> getSets() {
		return sets;
	}
	
	public void setSets(Set<?> sets) {
		this.sets = sets;
	}
}
