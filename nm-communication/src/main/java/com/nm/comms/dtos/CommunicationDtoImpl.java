package com.nm.comms.dtos;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class CommunicationDtoImpl implements CommunicationDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private CommunicationActorDto owner;
	private CommunicationSubjectDto subject;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CommunicationActorDto getOwner() {
		return owner;
	}

	public void setOwner(CommunicationActorDto owner) {
		this.owner = owner;
	}

	public CommunicationSubjectDto getSubject() {
		return subject;
	}

	public void setSubject(CommunicationSubjectDto subject) {
		this.subject = subject;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
