package com.nm.tests.fb;

import org.springframework.core.io.UrlResource;

import com.nm.social.operations.twitter.DtoPostData;
import com.nm.tests.FakeModelMessage;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class FakeConverterTwitter extends DtoConverterDefault<DtoPostData, FakeModelMessage> {
	public DtoPostData toDto(DtoPostData data, FakeModelMessage entity, OptionsList options) throws DtoConvertException {
		try {
			return (DtoPostData) data.withMedia(new UrlResource(entity.getImage()));
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public DtoPostData toDto(FakeModelMessage entity, OptionsList options) throws DtoConvertException {
		return toDto(new DtoPostData(entity.getTitle()), entity, options);
	}

}
