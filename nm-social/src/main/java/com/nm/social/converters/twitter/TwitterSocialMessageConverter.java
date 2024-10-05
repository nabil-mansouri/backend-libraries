package com.nm.social.converters.twitter;

import org.springframework.core.io.UrlResource;

import com.nm.social.operations.args.SocialMessageInfoDefault;
import com.nm.social.operations.twitter.DtoPostData;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class TwitterSocialMessageConverter extends DtoConverterDefault<DtoPostData, SocialMessageInfoDefault> {
	public DtoPostData toDto(DtoPostData data, SocialMessageInfoDefault entity, OptionsList options)
			throws DtoConvertException {
		try {
			return (DtoPostData) data.withMedia(new UrlResource(entity.getImage()));
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public DtoPostData toDto(SocialMessageInfoDefault entity, OptionsList options) throws DtoConvertException {
		return toDto(new DtoPostData(entity.getTitle()), entity, options);
	}

}
