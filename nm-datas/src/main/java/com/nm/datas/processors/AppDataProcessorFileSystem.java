package com.nm.datas.processors;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.nm.config.SoaModuleConfig;
import com.nm.datas.constants.AppDataDestination.AppDataDestinationDefault;
import com.nm.datas.constants.ModuleConfigKeyData;
import com.nm.datas.dtos.AppDataDto;
import com.nm.datas.models.AppData;
import com.nm.datas.models.AppDataPath;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.PathUtilsExt;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class AppDataProcessorFileSystem extends AppDataProcessor {
	public boolean accept(AppDataDto dto) {
		return dto.getDestination().equals(AppDataDestinationDefault.FileSystem);
	}

	@Override
	public boolean accept(AppData entity) {
		return entity.getDestination().equals(AppDataDestinationDefault.FileSystem);
	}

	public AppData create(AppDataDto dto) {
		AppDataPath entity = new AppDataPath();
		entity.setFolderType(dto.getFolder());
		return entity;
	}

	public void pushToDestination(AppData data, AppDataDto dto) throws Exception {
		SoaModuleConfig soaConfig = ApplicationUtils.getBean(SoaModuleConfig.class);
		//
		AppDataPath path = (AppDataPath) data;
		List<String> paths = path.getFolderType().paths();
		String fsPath = PathUtilsExt.buildFSPath(soaConfig.getText(ModuleConfigKeyData.BaseDir), paths, realName(data));
		File file = new File(fsPath);
		switch (data.getKind()) {
		case Byte:
			FileUtils.writeByteArrayToFile(file, dto.getFile());
			path.setSize((int) FileUtils.sizeOf(file));
			break;
		case Text:
			FileUtils.writeStringToFile(file, dto.getText());
			path.setSize((int) FileUtils.sizeOf(file));
			break;
		}
		FileInputStream in = new FileInputStream(file);
		try {
			path.setHash(DigestUtils.md5Hex(in));
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public void removeFromDestination(AppData data) throws Exception {
		SoaModuleConfig soaConfig = ApplicationUtils.getBean(SoaModuleConfig.class);
		//
		AppDataPath path = (AppDataPath) data;
		List<String> paths = path.getFolderType().paths();
		String fsPath = PathUtilsExt.buildFSPath(soaConfig.getText(ModuleConfigKeyData.BaseDir), paths, realName(data));
		File file = new File(fsPath);
		FileUtils.deleteQuietly(file);
	}

	public void pullFromDestination(AppData data, AppDataDto context) throws Exception {
		SoaModuleConfig soaConfig = ApplicationUtils.getBean(SoaModuleConfig.class);
		//
		AppDataPath path = (AppDataPath) data;
		List<String> paths = path.getFolderType().paths();
		String fsPath = PathUtilsExt.buildFSPath(soaConfig.getText(ModuleConfigKeyData.BaseDir), paths, realName(data));
		File file = new File(fsPath);
		switch (data.getKind()) {
		case Byte:
			context.setFile(FileUtils.readFileToByteArray(file));
			break;
		case Text:
			context.setText(FileUtils.readFileToString(file));
			break;
		}
	}

	public String toURL(AppData data) {
		AppDataPath path = (AppDataPath) data;
		List<String> paths = path.getFolderType().paths();
		String urlPath = PathUtilsExt.buildURL(paths, realName(data));
		return urlPath;
	}

}
