package com.nm.app.log;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public interface SoaGeneralLogService {

	public List<GeneralLogDto> fetch(long limit, long offset);

	public List<GeneralLogDto> fetch(DetachedCriteria criteria, Integer limit, Integer offset);

	public void pushMessage(GeneralLogLevel level, String message);

	public void pushMessage(Exception e);

}
