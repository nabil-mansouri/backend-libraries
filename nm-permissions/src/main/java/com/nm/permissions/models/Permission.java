package com.nm.permissions.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;

import org.hibernate.annotations.Type;

import com.nm.permissions.constants.Action;
import com.nm.utils.hibernate.impl.ModelTimeable;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(schema="mod_perm",name = "perm_permission")
public class Permission extends ModelTimeable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "seq_permission", sequenceName = "seq_permission", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_permission")
	private Long id;
	@Column(nullable = false)
	private boolean granted;
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private Action action;
	@ManyToOne(optional = false)
	private Resource resource;
	@ManyToOne(optional = false)
	private PermissionGrid grid;

	public PermissionGrid getGrid() {
		return grid;
	}

	public void setGrid(PermissionGrid grid) {
		this.grid = grid;
	}

	public Action getAction() {
		return action;
	}

	public Long getId() {
		return id;
	}

	public Resource getResource() {
		return resource;
	}

	public boolean isGranted() {
		return granted;
	}

	public Permission setAction(Action action) {
		this.action = action;
		return this;
	}

	public Permission setGranted(boolean enable) {
		this.granted = enable;
		return this;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Permission setResource(Resource resource) {
		this.resource = resource;
		return this;
	}

	@AssertTrue(message = "permission.errors.action.notavailable")
	protected boolean assertActionAvailable() {
		return this.resource.getAvailable().contains(this.action);
	}
}
