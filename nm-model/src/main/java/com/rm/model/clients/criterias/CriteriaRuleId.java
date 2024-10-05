package com.rm.model.clients.criterias;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_client_criterias_rule_id")
public class CriteriaRuleId extends CriteriaRule {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CriteriaRuleId() {
	}

	public CriteriaRuleId(Collection<Long> ids) {
		this.getIds().addAll(ids);
	}

	@ElementCollection(targetClass = Long.class)
	@CollectionTable(name = "nm_client_criterias_rule_id_list", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "data")
	private Collection<Long> ids = new HashSet<Long>();

	public Collection<Long> getIds() {
		return ids;
	}

	public void setIds(Collection<Long> ids) {
		this.ids = ids;
	}

}
