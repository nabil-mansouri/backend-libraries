package com.nm.datas.processors;

import org.apache.commons.codec.digest.DigestUtils;

import com.nm.datas.constants.AppDataDestination.AppDataDestinationDefault;
import com.nm.datas.dtos.AppDataDto;
import com.nm.datas.models.AppData;
import com.nm.datas.models.AppDataText;
import com.nm.utils.http.HttpUtilsExt;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class AppDataProcessorURL extends AppDataProcessor {
	public boolean accept(AppDataDto dto) {
		return dto.getDestination().equals(AppDataDestinationDefault.URL);
	}

	@Override
	public boolean accept(AppData entity) {
		return entity.getDestination().equals(AppDataDestinationDefault.URL);
	}

	public AppData create(AppDataDto dto) {
		return new AppDataText();
	}

	public void pushToDestination(AppData data, AppDataDto context) throws Exception {
		AppDataText path = (AppDataText) data;
		path.setFile(context.getUrl());
		path.setHash(DigestUtils.md5Hex(path.getFile()));
	}

	public void removeFromDestination(AppData data) throws Exception {
		AppDataText path = (AppDataText) data;
		HttpUtilsExt.delete(path.getFile());
	}

	public void pullFromDestination(AppData data, AppDataDto context) throws Exception {
		String url = toURL(data);
		switch (data.getKind()) {
		case Byte:
			context.setFile(HttpUtilsExt.getBytes(url));
			break;
		case Text:
			context.setText(HttpUtilsExt.get(url));
			break;
		}
	}

	public String toURL(AppData data) {
		AppDataText path = (AppDataText) data;
		return path.getFile();
	}

}
