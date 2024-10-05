package com.nm.dictionnary.dtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.nm.utils.dtos.Dto;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public interface DtoDictionnaryValue extends Dto {

	Long getIdValue();

}
