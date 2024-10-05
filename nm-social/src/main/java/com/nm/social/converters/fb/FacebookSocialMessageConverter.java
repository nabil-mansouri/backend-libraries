package com.nm.social.converters.fb;

import org.apache.commons.lang3.ArrayUtils;

import com.nm.social.models.SocialUser;
import com.nm.social.operations.args.SocialMessageInfoDefault;
import com.nm.social.operations.fb.DtoPostData;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class FacebookSocialMessageConverter extends DtoConverterDefault<DtoPostData, SocialMessageInfoDefault> {
	public DtoPostData toDto(DtoPostData data, SocialMessageInfoDefault entity, OptionsList options)
			throws DtoConvertException {
		data.message(entity.getDesc())//
				.link(entity.getUrl(), //
						entity.getImage(), //
						entity.getTitle(), entity.getCaption(), entity.getDescImg());
		SocialUser user = options.getOverrides(SocialUser.class);
		String[] tags = {};
		for (SocialUser u : user.getFriends()) {
			ArrayUtils.add(tags, u.getUuid());
		}
		data.tags(tags);
		return data;
	}

	public DtoPostData toDto(SocialMessageInfoDefault entity, OptionsList options) throws DtoConvertException {
		SocialUser user = options.getOverrides(SocialUser.class);
		return toDto(new DtoPostData(user.getUuid()), entity, options);
	}

}
