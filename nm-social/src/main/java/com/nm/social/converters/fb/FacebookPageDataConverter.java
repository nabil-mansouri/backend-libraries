package com.nm.social.converters.fb;

import java.nio.channels.NotYetBoundException;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.social.facebook.api.PagePostData;
import org.springframework.social.facebook.api.PostData;
import org.springframework.util.MultiValueMap;

import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class FacebookPageDataConverter extends DtoConverterDefault<PostData, PagePostData> {

	public PostData toDto(PostData dto, PagePostData entity, OptionsList options) throws DtoConvertException {
		throw new NotYetBoundException();
	}

	@Override
	public PagePostData toEntity(PagePostData entity, PostData dto, OptionsList options) throws DtoConvertException {
		MultiValueMap<String, Object> params = dto.toRequestParameters();
		if (setted(params, "link")) {
			entity.link(string(params, "link"), string(params, "picture"), string(params, "name"),
					string(params, "caption"), string(params, "description"));
		}
		entity.message(string(params, "message")).place(string(params, "place"));
		//
		if (params.get("tags") != null) {
			String[] tags = {};
			for (Object t : params.get("tags")) {
				tags = ArrayUtils.add(tags, string(t));
			}
			entity.tags(tags);
		}
		return entity;
	}

	private boolean setted(MultiValueMap<String, Object> params, String key) {
		return params.getFirst(key) != null;
	}

	private String string(Object o) {
		return (o != null) ? o.toString() : null;
	}

	private String string(MultiValueMap<String, Object> params, String key) {
		return (params.getFirst(key) != null) ? params.getFirst(key).toString() : null;
	}
}
