package com.nm.auths.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.nm.utils.hibernate.impl.ModelTimeable;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(schema = "mod_auth", name = "user_group")
public class UserGroup extends ModelTimeable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "user_group_seq", sequenceName = "user_group_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_group_seq")
	private Long id;
	@Column(nullable = true)
	private String code;
	@Column(nullable = false)
	private String name;
	@ManyToMany()
	@JoinTable(schema = "mod_auth", name = "user_group_users")
	private List<User> users = new ArrayList<User>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void remove(User bean) {
		while (users.contains(bean)) {
			users.remove(bean);
		}
	}

	public void add(User user) {
		this.users.add(user);
		user.getGroups().add(this);
	}
}
