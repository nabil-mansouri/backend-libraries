package com.nm.datas.constants;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import com.nm.utils.json.EnumJsonConverterIn;
import com.nm.utils.json.EnumJsonConverterOut;

/***
 * 
 * @author nabilmansouri
 *
 */
@JsonSerialize(using = EnumJsonConverterIn.class)
@JsonDeserialize(using = EnumJsonConverterOut.class)
public interface FolderType {
	public List<String> paths();

	public enum FolderTypeDefault implements FolderType {
		Base(), //
		TempPrivate("private", "temp"), //
		Images("public", "images"), //
		Videos("public", "videos"), //
		TempPublic("public", "temp");
		private final List<String> all;

		private FolderTypeDefault(String... all) {
			this.all = Lists.newArrayList(all);
		}

		public List<String> paths() {
			return all;
		}
	}
}
