package com.nm.tests.config;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.config.SoaAppConfig;
import com.nm.config.SoaModuleConfig;
import com.nm.config.constants.AppConfigKeyDefault;
import com.nm.config.constants.ModuleConfigKey;
import com.nm.config.constants.ModuleConfigKeyDefault;
import com.nm.config.dao.DaoAppConfig;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestConfig.class)
public class TestSoaConfig {

	@Autowired
	private SoaAppConfig soaAppConfig;
	@Autowired
	private SoaModuleConfig soaModuleConfig;
	@Autowired
	private DaoAppConfig daoAppConfig;
	//
	protected Log log = LogFactory.getLog(getClass());

	public enum ModuleConfigOther implements ModuleConfigKey {
		DefaultDevise("");
		private final String defaut;
		private final boolean file;

		public boolean isPlainText() {
			return false;
		}

		private ModuleConfigOther(String key) {
			this.defaut = key;
			this.file = false;
		}

		public boolean isFile() {
			return file;
		}

		public String getDefaut() {
			return defaut;
		}
	}

	@Test
	@Transactional
	public void testSaveFile() throws Exception {
		File origin = new File(getClass().getResource("test.txt").getFile());
		soaAppConfig.setByte(AppConfigKeyDefault.AdminSmtp, FileUtils.readFileToByteArray(origin));
		daoAppConfig.flush();
		byte[] b = soaAppConfig.getBytes(AppConfigKeyDefault.AdminSmtp);
		File temp = File.createTempFile("yeah", "tmp");
		FileUtils.writeByteArrayToFile(temp, b);
		Assert.assertEquals(FileUtils.readFileToString(origin), FileUtils.readFileToString(temp));
	}

	@Test
	@Transactional
	public void testSaveConfigDuplic() throws Exception {
		soaAppConfig.setInt(AppConfigKeyDefault.AdminSmtp, 1);
		soaAppConfig.setInt(AppConfigKeyDefault.AdminSmtp, 1);
		soaAppConfig.setInt(AppConfigKeyDefault.AdminSmtp, 1);
		daoAppConfig.flush();
		Assert.assertEquals(1, soaAppConfig.getInt(AppConfigKeyDefault.AdminSmtp));
	}

	@Test
	@Transactional
	public void testSaveModuleDuplic() throws Exception {
		soaModuleConfig.setInt(ModuleConfigKeyDefault.DefaultDevise, 1);
		soaModuleConfig.setInt(ModuleConfigKeyDefault.DefaultDevise, 1);
		soaModuleConfig.setInt(ModuleConfigKeyDefault.DefaultDevise, 1);
		daoAppConfig.flush();
		Assert.assertEquals(1, soaModuleConfig.getInt(ModuleConfigKeyDefault.DefaultDevise));
	}

	@Test
	@Transactional
	public void testSaveModuleDuplicMany() throws Exception {
		soaModuleConfig.setInt(ModuleConfigKeyDefault.DefaultDevise, 1);
		soaModuleConfig.setInt(ModuleConfigOther.DefaultDevise, 2);
		daoAppConfig.flush();
		Assert.assertEquals(1, soaModuleConfig.getInt(ModuleConfigKeyDefault.DefaultDevise));
		Assert.assertEquals(2, soaModuleConfig.getInt(ModuleConfigOther.DefaultDevise));
	}

}
