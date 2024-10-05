package com.nm.tests.data;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.config.SoaModuleConfig;
import com.nm.datas.SoaAppData;
import com.nm.datas.constants.AppDataContentKind;
import com.nm.datas.constants.AppDataDestination.AppDataDestinationDefault;
import com.nm.datas.constants.AppDataOptions;
import com.nm.datas.constants.ModuleConfigKeyData;
import com.nm.datas.daos.DaoAppData;
import com.nm.datas.daos.QueryBuilderAppData;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.datas.models.AppData;
import com.nm.datas.models.AppDataByte;
import com.nm.datas.models.AppDataPath;
import com.nm.datas.models.AppDataText;
import com.nm.utils.ByteUtils;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.http.HttpUtilsExt;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestDatas.class)
public class TestSoaData {

	@Autowired
	private SoaAppData soaAppData;
	@Autowired
	private DaoAppData dao;
	@Autowired
	private SoaModuleConfig soaModule;
	//
	protected Log log = LogFactory.getLog(getClass());
	private String base;

	@Before
	public void before() {
		Path currentRelativePath = Paths.get("");
		base = currentRelativePath.toAbsolutePath().toString();
		System.out.println("Base path:" + base);
		soaModule.setText(ModuleConfigKeyData.BaseDir, base);
	}

	@Test
	@Transactional
	public void testShouldSaveTextInDB() throws Exception {
		AppDataDtoImpl dto = new AppDataDtoImpl().setText("HELLO")//
				.setKind(AppDataContentKind.Text)//
				.setDestination(AppDataDestinationDefault.Database)//
				.setType("application/pdf");
		AppData data = soaAppData.save(dto, new OptionsList());
		dao.flush();
		Assert.assertNotNull(data.getId());
		Assert.assertTrue(dao.get(data.getId()) instanceof AppDataText);
		//
		OptionsList options = new OptionsList();
		options.withOption(AppDataOptions.values());
		dto = (AppDataDtoImpl) soaAppData.fetch(data.getId(), options);
		Assert.assertEquals("HELLO", dto.getText());
		System.out.println("File path:" + dto.getUrl());
		String read = FileUtils.readFileToString(new File(base + File.separator + dto.getUrl()));
		Assert.assertEquals("HELLO", read);
	}

	@Test
	@Transactional
	public void testShouldSaveUniqueTextInDB() throws Exception {
		AppDataDtoImpl dto = new AppDataDtoImpl().setText("HELLO")//
				.setKind(AppDataContentKind.Text)//
				.setDestination(AppDataDestinationDefault.Database)//
				.setType("application/pdf");
		AppData data = soaAppData.saveUnique(dto, new OptionsList());
		dao.flush();
		Assert.assertNotNull(data.getId());
		Long before = data.getId();
		dto.setId(null);
		data = soaAppData.saveUnique(dto, new OptionsList());
		Assert.assertEquals(before, data.getId());
	}

	@Test
	@Transactional
	public void testShouldSaveUniqueByteInDB() throws Exception {
		AppDataDtoImpl dto = new AppDataDtoImpl().setFile("HELLO".getBytes())//
				.setKind(AppDataContentKind.Byte)//
				.setDestination(AppDataDestinationDefault.Database)//
				.setType("application/pdf");
		AppData data = soaAppData.saveUnique(dto, new OptionsList());
		dao.flush();
		Assert.assertNotNull(data.getId());
		Long before = data.getId();
		dto.setId(null);
		data = soaAppData.saveUnique(dto, new OptionsList());
		Assert.assertEquals(before, data.getId());
	}

	@Test
	@Transactional
	public void testShouldSaveByteInDB() throws Exception {
		AppDataDtoImpl dto = new AppDataDtoImpl().setFile("HELLO".getBytes())//
				.setKind(AppDataContentKind.Byte)//
				.setDestination(AppDataDestinationDefault.Database)//
				.setType("application/pdf");
		AppData data = soaAppData.save(dto, new OptionsList());
		dao.flush();
		Assert.assertNotNull(data.getId());
		Assert.assertTrue(dao.get(data.getId()) instanceof AppDataByte);
		//
		OptionsList options = new OptionsList();
		options.withOption(AppDataOptions.values());
		dto = (AppDataDtoImpl) soaAppData.fetch(data.getId(), options);
		Assert.assertEquals(ByteUtils.bytesToHex("HELLO".getBytes()), ByteUtils.bytesToHex(dto.getFile()));
		System.out.println("File path:" + dto.getUrl());
		byte[] read = FileUtils.readFileToByteArray(new File(base + File.separator + dto.getUrl()));
		Assert.assertEquals(ByteUtils.bytesToHex("HELLO".getBytes()), ByteUtils.bytesToHex(read));
	}

	@Test
	@Transactional
	public void testShouldSaveByteFile() throws Exception {
		AppDataDtoImpl dto = new AppDataDtoImpl().setFile("HELLO".getBytes())//
				.setKind(AppDataContentKind.Byte)//
				.setDestination(AppDataDestinationDefault.FileSystem)//
				.setType("application/pdf");
		AppData data = soaAppData.save(dto, new OptionsList());
		dao.flush();
		Assert.assertNotNull(data.getId());
		Assert.assertTrue(dao.get(data.getId()) instanceof AppDataPath);
		//
		OptionsList options = new OptionsList();
		options.withOption(AppDataOptions.values());
		dto = (AppDataDtoImpl) soaAppData.fetch(data.getId(), options);
		Assert.assertEquals(ByteUtils.bytesToHex("HELLO".getBytes()), ByteUtils.bytesToHex(dto.getFile()));
		System.out.println("File path:" + dto.getUrl());
		byte[] read = FileUtils.readFileToByteArray(new File(base + File.separator + dto.getUrl()));
		Assert.assertEquals(ByteUtils.bytesToHex("HELLO".getBytes()), ByteUtils.bytesToHex(read));
	}

	@Test
	@Transactional
	public void testShouldSaveUniqueByteFile() throws Exception {
		AppDataDtoImpl dto = new AppDataDtoImpl().setFile("HELLO".getBytes())//
				.setKind(AppDataContentKind.Byte)//
				.setDestination(AppDataDestinationDefault.FileSystem)//
				.setType("application/pdf");
		AppData data = soaAppData.saveUnique(dto, new OptionsList());
		dao.flush();
		Assert.assertNotNull(data.getId());
		Long before = data.getId();
		dto.setId(null);
		data = soaAppData.saveUnique(dto, new OptionsList());
		Assert.assertEquals(before, data.getId());
	}

	String URL = "https://www.facebook.com/images/fb_icon_325x325.png";

	@Test
	@Transactional
	public void testShouldSaveByteURL() throws Exception {
		AppDataDtoImpl dto = new AppDataDtoImpl().setUrl(URL)//
				.setKind(AppDataContentKind.Byte)//
				.setDestination(AppDataDestinationDefault.URL)//
				.setType("application/pdf");
		AppData data = soaAppData.save(dto, new OptionsList());
		dao.flush();
		Assert.assertNotNull(data.getId());
		Assert.assertTrue(dao.get(data.getId()) instanceof AppDataText);
		Assert.assertEquals(URL, ((AppDataText) data).getFile());
		//
		OptionsList options = new OptionsList();
		options.withOption(AppDataOptions.values());
		dto = (AppDataDtoImpl) soaAppData.fetch(data.getId(), options);
		Assert.assertNotEquals(URL, (dto.getFile()));
		System.out.println("File path:" + dto.getUrl());
		byte[] read = HttpUtilsExt.getBytes(dto.getUrl());
		Assert.assertEquals(ByteUtils.bytesToHex(dto.getFile()), ByteUtils.bytesToHex(read));
		Assert.assertTrue(dto.isAbsURL());
	}

	@Test
	@Transactional
	public void testShouldSaveUniqueByteURL() throws Exception {
		AppDataDtoImpl dto = new AppDataDtoImpl().setUrl(URL)//
				.setKind(AppDataContentKind.Byte)//
				.setDestination(AppDataDestinationDefault.URL)//
				.setType("application/pdf");
		AppData data = soaAppData.saveUnique(dto, new OptionsList());
		dao.flush();
		Assert.assertNotNull(data.getId());
		Long before = data.getId();
		dto.setId(null);
		data = soaAppData.saveUnique(dto, new OptionsList());
		Assert.assertEquals(before, data.getId());
	}

	@Test
	@Transactional
	public void testShouldRemoveTextInDB() throws Exception {
		OptionsList options = new OptionsList();
		AppDataDtoImpl dto = new AppDataDtoImpl().setText("HELLO")//
				.setKind(AppDataContentKind.Text)//
				.setDestination(AppDataDestinationDefault.Database)//
				.setType("application/pdf");
		AppData data = soaAppData.save(dto, new OptionsList());
		dao.flush();
		Assert.assertNotNull(data.getId());
		Assert.assertFalse(soaAppData.fetch(QueryBuilderAppData.get(), options).isEmpty());
		soaAppData.remove(dto);
		dao.flush();
		Assert.assertTrue(soaAppData.fetch(QueryBuilderAppData.get(), options).isEmpty());
	}

	@Test
	@Transactional
	public void testShouldRemoveByteInDB() throws Exception {
		OptionsList options = new OptionsList();
		AppDataDtoImpl dto = new AppDataDtoImpl().setFile("HELLO".getBytes())//
				.setKind(AppDataContentKind.Byte)//
				.setDestination(AppDataDestinationDefault.Database)//
				.setType("application/pdf");
		AppData data = soaAppData.save(dto, new OptionsList());
		dao.flush();
		Assert.assertNotNull(data.getId());
		Assert.assertFalse(soaAppData.fetch(QueryBuilderAppData.get(), options).isEmpty());
		soaAppData.remove(dto);
		dao.flush();
		Assert.assertTrue(soaAppData.fetch(QueryBuilderAppData.get(), options).isEmpty());
	}

	@Test
	@Transactional
	public void testShouldRemoveTextInFileSystem() throws Exception {
		OptionsList options = new OptionsList();
		options.withOption(AppDataOptions.values());
		AppDataDtoImpl dto = new AppDataDtoImpl().setText("HELLO")//
				.setKind(AppDataContentKind.Text)//
				.setDestination(AppDataDestinationDefault.FileSystem)//
				.setType("application/pdf");
		AppData data = soaAppData.save(dto, options);
		dao.flush();
		Assert.assertNotNull(data.getId());
		Assert.assertFalse(soaAppData.fetch(QueryBuilderAppData.get(), options).isEmpty());
		dto = (AppDataDtoImpl) soaAppData.fetch(dto.getId(), options);
		File file = new File(base + File.separator + dto.getUrl());
		Assert.assertTrue(file.exists());
		soaAppData.remove(dto);
		dao.flush();
		Assert.assertTrue(soaAppData.fetch(QueryBuilderAppData.get(), options).isEmpty());
		Assert.assertFalse(file.exists());
	}

	@Test
	@Transactional
	public void testShouldRemoveByteInURL() throws Exception {
		OptionsList options = new OptionsList();
		AppDataDtoImpl dto = new AppDataDtoImpl().setUrl(URL)//
				.setKind(AppDataContentKind.Byte)//
				.setDestination(AppDataDestinationDefault.URL)//
				.setType("application/pdf");
		AppData data = soaAppData.save(dto, new OptionsList());
		dao.flush();
		Assert.assertNotNull(data.getId());
		Assert.assertFalse(soaAppData.fetch(QueryBuilderAppData.get(), options).isEmpty());
		soaAppData.remove(dto);
		dao.flush();
		Assert.assertTrue(soaAppData.fetch(QueryBuilderAppData.get(), options).isEmpty());
	}
}
