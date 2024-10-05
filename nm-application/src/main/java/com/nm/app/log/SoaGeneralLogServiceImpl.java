package com.nm.app.log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class SoaGeneralLogServiceImpl implements SoaGeneralLogService {
	protected Log log = LogFactory.getLog(getClass());
	// DAO
	private GeneralLogDao generalLogDao;
	private GeneralLogConverter logConverter;

	public void setGeneralLogDao(GeneralLogDao generalLogDao) {
		this.generalLogDao = generalLogDao;
	}

	public void setLogConverter(GeneralLogConverter logConverter) {
		this.logConverter = logConverter;
	}

	public void pushMessage(Exception e) {
		pushMessage(GeneralLogLevel.ERROR, ExceptionUtils.getStackTrace(e));
	}

	public List<GeneralLogDto> fetch(DetachedCriteria criteria, Integer limit, Integer offset) {
		List<GeneralLogDto> messagesBean = new ArrayList<GeneralLogDto>();
		Collection<GeneralLog> messages = generalLogDao.find(criteria, offset, limit);
		for (GeneralLog m : messages) {
			GeneralLogDto bean = new GeneralLogDto();
			bean.setLevel(m.getLevel());
			bean.setMessage(m.getCommentaires());
			bean.setDate(m.getCreatedAt());
			messagesBean.add(bean);
		}
		return messagesBean;
	}

	public void pushMessage(GeneralLogLevel level, String message) {
		generalLogDao.saveOrUpdate(new GeneralLog(level, message));
	}

	public List<GeneralLogDto> fetch(long limit, long offset) {
		List<GeneralLogDto> messagesBean = new ArrayList<GeneralLogDto>();
		Collection<GeneralLog> messages = generalLogDao.find(offset, limit);
		for (GeneralLog m : messages) {
			messagesBean.add(logConverter.convert(m));
		}
		return messagesBean;
	}
}
