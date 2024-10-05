package com.nm.permissions.dtos;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class DtoSubjectDefault implements DtoSubject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long idGroup;
	private Long idUser;
	private String name;
	private boolean typeGroup;

	public void setIdSubject(Long id) {
		setId(id);
	}

	public boolean isTypeGroup() {
		return typeGroup;
	}

	public void setTypeGroup(boolean typeGroup) {
		this.typeGroup = typeGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIdGroup() {
		return idGroup;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdSubject() {
		return id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
