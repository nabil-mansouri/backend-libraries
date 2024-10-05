package com.nm.comms.dtos;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class CommunicationActorDtoImpl implements CommunicationActorDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String mail;

	public String getMail() {
		return mail;
	}

	public CommunicationActorDtoImpl setMail(String mail) {
		this.mail = mail;
		return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
