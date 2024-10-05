package com.nm.products.dtos.navigation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class NavigationPathList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Collection<NavigationPath> paths = new ArrayList<NavigationPath>();

	public NavigationPath create() {
		NavigationPath n = new NavigationPath();
		paths.add(n);
		return n;
	}

	public Collection<NavigationPath> getPaths() {
		return paths;
	}

	public void setPaths(Collection<NavigationPath> paths) {
		this.paths = paths;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
