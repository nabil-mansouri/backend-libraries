package com.nm.app.log;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class GeneralLogConverterImpl implements GeneralLogConverter {

	public GeneralLogDto convert(GeneralLog m) {
		GeneralLogDto bean = new GeneralLogDto();
		bean.setLevel(m.getLevel());
		bean.setMessage(m.getCommentaires());
		bean.setDate(m.getCreatedAt());
		return bean;
	}

}
