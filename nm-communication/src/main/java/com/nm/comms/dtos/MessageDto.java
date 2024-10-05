package com.nm.comms.dtos;

import com.nm.app.triggers.DtoTrigger;
import com.nm.utils.dtos.Dto;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public interface MessageDto extends Dto {
	public MessageDto setId(Long id);

	public MessageDto setChannelId(Long id);

	public Long getId();

	public Long getChannelId();

	public DtoTrigger getTrigger();
}
