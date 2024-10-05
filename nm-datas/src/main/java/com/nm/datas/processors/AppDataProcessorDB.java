package com.nm.datas.processors;

import java.io.File;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import com.nm.config.SoaModuleConfig;
import com.nm.datas.constants.AppDataDestination.AppDataDestinationDefault;
import com.nm.datas.constants.FolderType.FolderTypeDefault;
import com.nm.datas.constants.ModuleConfigKeyData;
import com.nm.datas.dtos.AppDataDto;
import com.nm.datas.models.AppData;
import com.nm.datas.models.AppDataByte;
import com.nm.datas.models.AppDataText;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.PathUtilsExt;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class AppDataProcessorDB extends AppDataProcessor {
	public boolean accept(AppDataDto dto) {
		return dto.getDestination().equals(AppDataDestinationDefault.Database);
	}

	@Override
	public boolean accept(AppData entity) {
		return entity.getDestination().equals(AppDataDestinationDefault.Database);
	}

	public AppData create(AppDataDto dto) {
		switch (dto.getKind()) {
		case Byte:
			return new AppDataByte();
		case Text:
			return new AppDataText();

		}
		throw new IllegalArgumentException("Could not determine type of :" + dto.getKind());
	}

	public void pushToDestination(AppData data, AppDataDto dto) throws Exception {
		switch (dto.getKind()) {
		case Byte:
			AppDataByte byt = (AppDataByte) data;
			byt.setFile(dto.getFile());
			byt.setHash(DigestUtils.md5Hex(byt.getFile()));
			break;
		case Text:
			AppDataText text = (AppDataText) data;
			text.setFile(dto.getText());
			text.setHash(DigestUtils.md5Hex(text.getFile()));
			break;

		}
	}

	public void removeFromDestination(AppData data) throws Exception {
		// DONE ON CASCADE
	}

	public void pullFromDestination(AppData data, AppDataDto dto) throws Exception {
		switch (data.getKind()) {
		case Byte:
			AppDataByte byt = (AppDataByte) data;
			dto.setFile(byt.getFile());
			break;
		case Text:
			AppDataText text = (AppDataText) data;
			dto.setText(text.getFile());
			break;
		}
	}

	public String toURL(AppData data) throws Exception {
		SoaModuleConfig soaConfig = ApplicationUtils.getBean(SoaModuleConfig.class);
		//
		String fsPath = PathUtilsExt.buildFSPath(soaConfig.getText(ModuleConfigKeyData.BaseDir),
				FolderTypeDefault.TempPublic.paths(), realName(data));
		File file = new File(fsPath);
		if (!file.exists()) {
			switch (data.getKind()) {
			case Byte:
				AppDataByte byt = (AppDataByte) data;
				FileUtils.writeByteArrayToFile(file, byt.getFile());
				break;
			case Text:
				AppDataText text = (AppDataText) data;
				FileUtils.writeStringToFile(file, text.getFile());
				break;
			}
		}
		//
		String urlPath = PathUtilsExt.buildURL(FolderTypeDefault.TempPublic.paths(), realName(data));
		return urlPath;
	}

}
