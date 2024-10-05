package com.nm.cms.constants;

import java.util.Collection;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nm.utils.ListUtils;
import com.nm.utils.json.EnumJsonConverterIn;
import com.nm.utils.json.EnumJsonConverterOut;

/**
 * 
 * @author nabilmansouri
 * 
 */
@JsonSerialize(using = EnumJsonConverterIn.class)
@JsonDeserialize(using = EnumJsonConverterOut.class)
public interface CmsTableHeader {
	public boolean optionnal();
 
	public enum CmsTableHeaderDefault implements CmsTableHeader {
		Header(true), Header1(true), Header2(true), Header3(true), Header4(true);
		private final boolean optionnal;

		private CmsTableHeaderDefault(boolean optionnal) {
			this.optionnal = optionnal;
		}

		public boolean optionnal() {
			return optionnal;
		}

		public Collection<CmsTableHeader> getAll() {
			return ListUtils.cast(CmsTableHeaderDefault.values());
		}

	}

}