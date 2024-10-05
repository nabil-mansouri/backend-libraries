package com.nm.permissions.grids;

import com.nm.permissions.models.Permission;
import com.nm.permissions.models.PermissionGrid;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class GridMergerEntry {

	public GridMergerEntry(PermissionGrid grid, Permission permission) {
		super();
		this.grid = grid;
		this.permission = permission;
	}

	private int priority;
	private PermissionGrid grid;
	private Permission permission;

	public int getPriority() {
		return priority;
	}

	public GridMergerEntry setPriority(int priority) {
		this.priority = priority;
		return this;
	}

	public Permission getPermission() {
		return permission;
	}

	public PermissionGrid getGrid() {
		return grid;
	}

	public void setGrid(PermissionGrid grid) {
		this.grid = grid;
	}

}
