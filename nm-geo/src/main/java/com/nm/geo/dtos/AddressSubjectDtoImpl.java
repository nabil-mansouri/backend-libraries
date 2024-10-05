package com.nm.geo.dtos;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class AddressSubjectDtoImpl implements AddressSubjectDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long subjectId;

	public AddressSubjectDtoImpl() {
	}

	public AddressSubjectDtoImpl(Long subjectId) {
		setSubjectId(subjectId);
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

}
