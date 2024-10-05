package com.nm.permissions.grids;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.nm.permissions.constants.Action;
import com.nm.permissions.models.Resource;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class GridEntriesSorted extends LinkedHashMap<String, List<GridMergerEntry>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GridEntriesSorted() {
	}

	public boolean containsKeyForEntry(Resource r, Action a) {
		return this.containsKey(key(r, a));
	}

	public boolean containsKeyForEntry(GridMergerEntry entry) {
		return this.containsKey(key(entry));
	}

	private String key(Resource r, Action a) {
		return String.format("%s;%s", //
				EnumJsonConverterRegistry.key((Enum<?>) r.getType()), //
				EnumJsonConverterRegistry.key((Enum<?>) a));
	}

	private String key(GridMergerEntry entry) {
		return String.format("%s;%s", //
				EnumJsonConverterRegistry.key((Enum<?>) entry.getPermission().getResource().getType()), //
				EnumJsonConverterRegistry.key((Enum<?>) entry.getPermission().getAction()));
	}

	public void add(GridMergerEntry entry) {
		String key = key(entry);
		//
		this.putIfAbsent(key, new ArrayList<GridMergerEntry>());
		this.get(key).add(entry);

	}
}
