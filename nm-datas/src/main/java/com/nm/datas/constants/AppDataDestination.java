package com.nm.datas.constants;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nm.utils.json.EnumJsonConverterIn;
import com.nm.utils.json.EnumJsonConverterOut;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@JsonSerialize(using = EnumJsonConverterIn.class)
@JsonDeserialize(using = EnumJsonConverterOut.class)
public interface AppDataDestination {
	public enum AppDataDestinationDefault implements AppDataDestination {
		Database, FileSystem, URL
	}

}
