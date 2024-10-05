package com.nm.auths.dtos;

import com.nm.utils.dtos.Dto;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DtoGroup implements Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String code;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public DtoGroup setCode(String code) {
		this.code = code;
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
