package com.nm.social.converters;

import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;

import com.nm.social.constants.SocialEventStatus;
import com.nm.social.constants.SocialOptions;
import com.nm.social.dtos.DtoSocialState;
import com.nm.social.dtos.SocialEventDto;
import com.nm.social.models.SocialEvent;
import com.nm.social.models.SocialEventRelation;
import com.nm.social.models.SocialNetwork;
import com.nm.social.models.SocialUser;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class ConverterSocialState extends DtoConverterDefault<DtoSocialState, SocialUser> {

	@Override
	public DtoSocialState toDto(DtoSocialState dto, SocialUser entity, OptionsList options) throws DtoConvertException {
		DtoSocialState state = new DtoSocialState();
		state.setMine(entity.getDetails());
		state.setProvider(entity.getType());
		if (options.contains(SocialOptions.FriendsStates)) {
			for (SocialUser friend : entity.getFriends()) {
				state.getFriends().add(friend.getDetails());
			}
		}
		if (options.contains(SocialOptions.NetworkStates)) {
			for (SocialNetwork friend : entity.getNetworks()) {
				state.getNetworks().add(friend.getDetails());
			}
		}
		if (options.contains(SocialOptions.EventStates)) {
			for (Entry<SocialEventStatus, SocialEventRelation> entry : entity.getEvents().entrySet()) {
				SocialEventDto[] arr = new SocialEventDto[0];
				for (SocialEvent ev : entry.getValue().getEvents()) {
					arr = ArrayUtils.add(arr, ev.getDetails());
				}
				state.getEvents().put(entry.getKey(), arr);
			}
		}
		return state;
	}

}
