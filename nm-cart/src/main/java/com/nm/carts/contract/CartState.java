package com.nm.carts.contract;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nm.utils.json.EnumJsonConverterIn;
import com.nm.utils.json.EnumJsonConverterOut;

/**
 * 
 * @author nabilmansouri
 *
 */
@JsonSerialize(using = EnumJsonConverterIn.class)
@JsonDeserialize(using = EnumJsonConverterOut.class)
public interface CartState {

	public String name();

}
