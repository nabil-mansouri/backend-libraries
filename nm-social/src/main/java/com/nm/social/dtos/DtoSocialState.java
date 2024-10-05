package com.nm.social.dtos;

import java.util.Collection;
import java.util.Map;

import com.google.api.client.util.Maps;
import com.google.common.collect.Lists;
import com.nm.auths.constants.AuthenticationProvider;
import com.nm.social.constants.SocialEventStatus;
import com.nm.utils.dtos.Dto;

/**
 * FRIENDS OF SOCIAL NETWORKS
 * 
 * @author Nabil MANSOURI
 *
 */
public class DtoSocialState implements Dto {

	private static final long serialVersionUID = 1L;
	private AuthenticationProvider provider;
	private SocialUserDto mine;
	private Collection<SocialUserDto> friends = Lists.newArrayList();
	private Collection<SocialNetworkDto> networks = Lists.newArrayList();
	private Map<SocialEventStatus, SocialEventDto[]> events = Maps.newHashMap();

	public SocialUserDto getMine() {
		return mine;
	}

	public void setMine(SocialUserDto mine) {
		this.mine = mine;
	}

	public AuthenticationProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthenticationProvider provider) {
		this.provider = provider;
	}

	public Map<SocialEventStatus, SocialEventDto[]> getEvents() {
		return events;
	}

	public void setEvents(Map<SocialEventStatus, SocialEventDto[]> events) {
		this.events = events;
	}

	/**
	 * @return the networks
	 */
	public Collection<SocialNetworkDto> getNetworks() {
		return networks;
	}

	/**
	 * @param networks
	 *            the networks to set
	 */
	public void setNetworks(Collection<SocialNetworkDto> networks) {
		this.networks = networks;
	}

	public Collection<SocialUserDto> getFriends() {
		return friends;
	}

	public void setFriends(Collection<SocialUserDto> users) {
		this.friends = users;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
