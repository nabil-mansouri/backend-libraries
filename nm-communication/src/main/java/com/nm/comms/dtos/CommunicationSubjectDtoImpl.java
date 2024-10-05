package com.nm.comms.dtos;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class CommunicationSubjectDtoImpl implements CommunicationSubjectDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private MessageContentDto content = new MessageContentDtoImpl();
	private boolean any;

	public MessageContentDto getContent() {
		return content;
	}

	public CommunicationSubjectDtoImpl setContent(MessageContentDto content) {
		this.content = content;
		return this;
	}

	public boolean isAny() {
		return any;
	}

	public CommunicationSubjectDtoImpl setAny(boolean any) {
		this.any = any;
		return this;
	}

	public Long getId() {
		return id;
	}

	public CommunicationSubjectDtoImpl setId(Long id) {
		this.id = id;
		return this;
	}
}
