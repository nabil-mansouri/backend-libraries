package com.nm.permissions.dtos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.nm.permissions.constants.Action;
import com.nm.permissions.constants.ResourceType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class DtoPermissionsView implements DtoPermissionGrid {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Set<DtoPermissionEntry> values = new HashSet<DtoPermissionEntry>();
	private DtoSubject subject;
	private Long id;

	public Long getIdGrid() {
		return getId();
	}

	public void setIdGrid(Long i) {
		setId(i);
	}

	public Long getId() {
		return id;
	}

	public DtoSubject getSubject() {
		return subject;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSubject(DtoSubject subject) {
		this.subject = subject;
	}

	public DtoPermissionsView() {
	}

	public DtoPermissionsView(DtoPermissionsForm form) {
		this.setValues(form.getValues());
	}

	public void add(ResourceType res, Action act) {
		DtoPermissionEntry bean = new DtoPermissionEntry();
		bean.setAction(act);
		bean.setType(res);
		bean.setEnable(true);
		bean.setSelected(true);
		values.add(bean);
	}

	public void add(DtoPermissionEntry v) {
		if (v.isEnable() && v.getSelectedSafe()) {
			this.values.add(v);
		}
	}

	public Set<DtoPermissionEntry> getValues() {
		return values;
	}

	public void clear() {
		this.setValues(new HashSet<DtoPermissionEntry>(values));
	}

	public void setValues(Set<DtoPermissionEntry> values) {
		this.values.clear();
		for (DtoPermissionEntry p : values) {
			if (p.isEnable() && p.getSelectedSafe()) {
				this.values.add(p);
			}
		}
	}

	public Collection<DtoPermissionEntry> getBy(ResourceType resource) {
		Collection<DtoPermissionEntry> all = new ArrayList<DtoPermissionEntry>();
		for (DtoPermissionEntry v : values) {
			if (v.getType().equals(resource)) {
				all.add(v);
			}
		}
		return all;
	}

	public Collection<DtoPermissionEntry> getBy(Action action) {
		Collection<DtoPermissionEntry> all = new ArrayList<DtoPermissionEntry>();
		for (DtoPermissionEntry v : values) {
			if (v.getAction().equals(action)) {
				all.add(v);
			}
		}
		return all;
	}

	public Collection<DtoPermissionEntry> getBy(ResourceType resource, Action action) {
		Collection<DtoPermissionEntry> all = new ArrayList<DtoPermissionEntry>();
		for (DtoPermissionEntry v : values) {
			if (v.getAction().equals(action) && v.getType().equals(resource)) {
				all.add(v);
			}
		}
		return all;
	}

	public DtoPermissionEntry getFirstBy(ResourceType resource, Action action) {
		Collection<DtoPermissionEntry> entries = getBy(resource, action);
		if (entries.isEmpty()) {
			return null;
		} else {
			return entries.iterator().next();
		}
	}

	@Override
	public String toString() {
		return "DtoPermissionsView [values=" + values + ", subject=" + subject + ", id=" + id + "]";
	}

}
