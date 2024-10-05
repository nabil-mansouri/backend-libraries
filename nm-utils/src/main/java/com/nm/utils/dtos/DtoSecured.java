package com.nm.utils.dtos;

/**
 * 
 * @author nabilmansouri
 *
 */
public interface DtoSecured extends Dto {
	Class<?> securedModelClass();

	Long idSecuredModel();
}
