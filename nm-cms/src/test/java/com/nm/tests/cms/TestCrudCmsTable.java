package com.nm.tests.cms;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

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

import com.nm.cms.constants.CmsOptions;
import com.nm.cms.constants.CmsTableHeader.CmsTableHeaderDefault;
import com.nm.cms.dao.QueryBuilderCms;
import com.nm.cms.dtos.CmsDtoContentsFile;
import com.nm.cms.dtos.CmsDtoContentsIndexedTable;
import com.nm.cms.dtos.CmsDtoContentsIndexedTable.TableOperation;
import com.nm.cms.dtos.CmsDtoContentsIndexedTableRow;
import com.nm.cms.dtos.CmsDtoContentsIndexedTableRow.RowOperation;
import com.nm.cms.dtos.CmsDtoContentsTable;
import com.nm.cms.dtos.CmsDtoImpl;
import com.nm.cms.models.Cms;
import com.nm.cms.models.CmsContentsIndexedTable;
import com.nm.cms.soa.SoaCms;
import com.nm.datas.constants.AppDataMediaType;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.node.DaoModelNode;
import com.nm.utils.node.DaoModelNodeImpl;
import com.nm.utils.node.DtoNode;
import com.nm.utils.node.ModelNode;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestCms.class)
public class TestCrudCmsTable {

	@Autowired
	private SoaCms soaCms;
	private IGenericDao<Cms, Long> daoCms;
	//
	protected Log log = LogFactory.getLog(getClass());
	private CmsDtoImpl dto = new CmsDtoImpl();

	@Before
	public void setUp() throws Exception {
		daoCms = AbstractGenericDao.get(Cms.class);
		CmsDtoContentsTable table = new CmsDtoContentsTable();
		for (int i = 0; i < 2; i++) {
			List<String> row = table.createRow();
			row.add("A" + i);
			row.add("B" + i);
		}
		//
		DaoModelNode dao = new DaoModelNodeImpl(AbstractGenericDao.get(ModelNode.class).getHibernateTemplate());
		ModelNode node = new ModelNode();
		node.setId(new BigInteger("1"));
		node.setType(dao.types(Cms.class));
		dao.save(node);
		dto.setSubject(new DtoNode(node.getId()));
		dto.setContents(table);
		dto.setOwner(new DtoNode(node.getId()));
	}

	@Test
	@Transactional
	public void testShouldCreate() throws Exception {
		OptionsList options = new OptionsList();
		soaCms.saveOrUpdate(dto, options);
		daoCms.flush();
		Assert.assertNotNull(dto.getId());
	}

	@Test
	@Transactional
	public void testShouldCreateWithSubject() throws Exception {
		OptionsList options = new OptionsList();
		options.withOption(CmsOptions.CmsSubjectDto);
		soaCms.saveOrUpdate(dto, options);
		daoCms.flush();
		Assert.assertNotNull(dto.getId());
	}

	private CmsDtoContentsIndexedTable buildIndexedTable() {
		CmsDtoContentsIndexedTable table = new CmsDtoContentsIndexedTable();
		for (int i = 0; i < 5; i++) {
			CmsDtoContentsIndexedTableRow row = table.createRowDto();
			row.setOperation(RowOperation.Created);
			row.createCell().setDataString("A" + i);
			row.createCell().setDataString("B" + i);
			row.createCell().setDataString("C" + i);
		}
		return table;
	}

	@Test
	@Transactional
	public void testShouldCreateIndexTable() throws Exception {
		OptionsList options = new OptionsList();
		options.pushDtoForModel(CmsContentsIndexedTable.class, CmsDtoContentsIndexedTable.class);
		CmsDtoContentsIndexedTable table = buildIndexedTable();
		dto.setContents(table);
		soaCms.saveOrUpdate(dto, options);
		daoCms.flush();
		Assert.assertNotNull(dto.getId());
		CmsDtoImpl cms = (CmsDtoImpl) soaCms.fetch(QueryBuilderCms.get().withId(dto.getId()), options).iterator()
				.next();
		table = (CmsDtoContentsIndexedTable) cms.getContents();
		Assert.assertEquals(5, table.getRows().size());
		for (int i = 0; i < 5; i++) {
			CmsDtoContentsIndexedTableRow row = table.getRows().get(i);
			Assert.assertEquals("A" + i, row.getCells().get(0).getDataString());
			Assert.assertEquals("C" + i, row.getCells().get(2).getDataString());
		}
	}

	@Test
	@Transactional
	public void testShouldCreateIndexTableWithHeaders() throws Exception {
		OptionsList options = new OptionsList();
		options.pushDtoForModel(CmsContentsIndexedTable.class, CmsDtoContentsIndexedTable.class);
		options.add(CmsOptions.SaveHeaders);
		CmsDtoContentsIndexedTable table = buildIndexedTable();
		table.getHeaders().add(CmsTableHeaderDefault.Header1);
		table.getHeaders().add(CmsTableHeaderDefault.Header2);
		table.getHeaders().add(CmsTableHeaderDefault.Header3);
		table.getHeaders().add(CmsTableHeaderDefault.Header4);
		dto.setContents(table);
		soaCms.saveOrUpdate(dto, options);
		daoCms.flush();
		Assert.assertNotNull(dto.getId());
		CmsDtoImpl cms = (CmsDtoImpl) soaCms.fetch(QueryBuilderCms.get().withId(dto.getId()), options).iterator()
				.next();
		table = (CmsDtoContentsIndexedTable) cms.getContents();
		Assert.assertEquals(5, table.getRows().size());
		for (int i = 0; i < 5; i++) {
			CmsDtoContentsIndexedTableRow row = table.getRows().get(i);
			Assert.assertEquals("A" + i, row.getCells().get(0).getDataString());
			Assert.assertEquals("C" + i, row.getCells().get(2).getDataString());
			Assert.assertEquals("C" + i, row.getCellByHeader(CmsTableHeaderDefault.Header3).getDataString());
			Assert.assertEquals(CmsTableHeaderDefault.Header1, row.getCells().get(0).getHeader());
			Assert.assertEquals(CmsTableHeaderDefault.Header3, row.getCells().get(2).getHeader());
		}
	}

	@Test
	@Transactional
	public void testShouldUpdateRowOnIndexTable() throws Exception {
		OptionsList options = new OptionsList();
		options.withOption(CmsOptions.CmsSubjectDto);
		options.pushDtoForModel(CmsContentsIndexedTable.class, CmsDtoContentsIndexedTable.class);
		CmsDtoContentsIndexedTable table = buildIndexedTable();
		dto.setContents(table);
		soaCms.saveOrUpdate(dto, options);
		daoCms.flush();
		Assert.assertNotNull(dto.getId());
		//
		dto = (CmsDtoImpl) soaCms.fetch(QueryBuilderCms.get().withId(dto.getId()), options).iterator().next();
		table = (CmsDtoContentsIndexedTable) dto.getContents();
		table.getRows().get(4).setOperation(RowOperation.Updated).getCells().get(1).setDataString(null).setDataInt(5);
		soaCms.saveOrUpdate(dto, options);
		daoCms.flush();
		Assert.assertNotNull(dto.getId());
		//
		dto = (CmsDtoImpl) soaCms.fetch(QueryBuilderCms.get().withId(dto.getId()), options).iterator().next();
		table = (CmsDtoContentsIndexedTable) dto.getContents();
		Assert.assertEquals(5, table.getRows().size());
		for (int i = 0; i < 5; i++) {
			CmsDtoContentsIndexedTableRow row = table.getRows().get(i);
			if (i == 4) {
				Assert.assertEquals(5, row.getCells().get(1).getDataInt().intValue());
				Assert.assertNull(row.getCells().get(1).getDataString());
			}
			Assert.assertEquals("A" + i, row.getCells().get(0).getDataString());
			Assert.assertEquals("C" + i, row.getCells().get(2).getDataString());

		}
	}

	@Test
	@Transactional
	public void testShouldCreateRowOnIndexTable() throws Exception {
		OptionsList options = new OptionsList();
		options.withOption(CmsOptions.CmsSubjectDto);
		options.pushDtoForModel(CmsContentsIndexedTable.class, CmsDtoContentsIndexedTable.class);
		CmsDtoContentsIndexedTable table = buildIndexedTable();
		dto.setContents(table);
		soaCms.saveOrUpdate(dto, options);
		daoCms.flush();
		Assert.assertNotNull(dto.getId());
		//
		dto = (CmsDtoImpl) soaCms.fetch(QueryBuilderCms.get().withId(dto.getId()), options).iterator().next();
		table = (CmsDtoContentsIndexedTable) dto.getContents();
		table.createRowDto().setOperation(RowOperation.Created).createCell().setDataInt(5);
		soaCms.saveOrUpdate(dto, options);
		daoCms.flush();
		Assert.assertNotNull(dto.getId());
		//
		dto = (CmsDtoImpl) soaCms.fetch(QueryBuilderCms.get().withId(dto.getId()), options).iterator().next();
		table = (CmsDtoContentsIndexedTable) dto.getContents();
		Assert.assertEquals(6, table.getRows().size());
		for (int i = 0; i < 6; i++) {
			CmsDtoContentsIndexedTableRow row = table.getRows().get(i);
			if (i == 5) {
				Assert.assertEquals(5, row.getCells().get(0).getDataInt().intValue());
			} else {
				Assert.assertEquals("A" + i, row.getCells().get(0).getDataString());
				Assert.assertEquals("C" + i, row.getCells().get(2).getDataString());
			}
		}
	}

	@Test
	@Transactional
	public void testShouldDeleteRowOnIndexTable() throws Exception {
		OptionsList options = new OptionsList();
		options.withOption(CmsOptions.CmsSubjectDto);
		options.pushDtoForModel(CmsContentsIndexedTable.class, CmsDtoContentsIndexedTable.class);
		CmsDtoContentsIndexedTable table = buildIndexedTable();
		dto.setContents(table);
		soaCms.saveOrUpdate(dto, options);
		daoCms.flush();
		Assert.assertNotNull(dto.getId());
		//
		dto = (CmsDtoImpl) soaCms.fetch(QueryBuilderCms.get().withId(dto.getId()), options).iterator().next();
		table = (CmsDtoContentsIndexedTable) dto.getContents();
		table.getRows().get(0).setOperation(RowOperation.Deleted);
		table.getRows().get(2).setOperation(RowOperation.Deleted);
		soaCms.saveOrUpdate(dto, options);
		daoCms.flush();
		Assert.assertNotNull(dto.getId());
		//
		dto = (CmsDtoImpl) soaCms.fetch(QueryBuilderCms.get().withId(dto.getId()), options).iterator().next();
		table = (CmsDtoContentsIndexedTable) dto.getContents();
		Assert.assertEquals(3, table.getRows().size());
		Assert.assertEquals("A1", table.getRows().get(0).getCells().get(0).getDataString());
		Assert.assertEquals("C1", table.getRows().get(0).getCells().get(2).getDataString());
	}

	@Test
	@Transactional
	public void testShouldCreateTableFromFile() throws Exception {
		byte[] file = FileUtils.readFileToByteArray(new File(getClass().getResource("test.txt").getFile()));
		OptionsList options = new OptionsList();
		options.withOption(CmsOptions.CmsSubjectDto);
		options.pushDtoForModel(CmsContentsIndexedTable.class, CmsDtoContentsIndexedTable.class);
		AppDataDtoImpl app = new AppDataDtoImpl().setFile(file).withGroup(AppDataMediaType.Csv);
		dto.setContents(new CmsDtoContentsFile(app));
		soaCms.saveOrUpdate(dto, options);
		daoCms.flush();
		Assert.assertNotNull(dto.getId());
		//
		dto = (CmsDtoImpl) soaCms.fetch(QueryBuilderCms.get().withId(dto.getId()), options).iterator().next();
		CmsDtoContentsIndexedTable table = (CmsDtoContentsIndexedTable) dto.getContents();
		Assert.assertEquals(5, table.getRows().size());
		for (int i = 1; i <= 5; i++) {
			CmsDtoContentsIndexedTableRow row = table.getRows().get(i - 1);
			Assert.assertEquals(5, row.getCells().size());
			Assert.assertEquals("A" + i, row.getCells().get(0).getDataString());
			Assert.assertEquals("C" + i, row.getCells().get(2).getDataString());
		}
		AbstractGenericDao.get(Cms.class).flush();
	}

	@Test
	@Transactional
	public void testShouldUpdateTableOnIndexTable() throws Exception {
		OptionsList options = new OptionsList();
		options.withOption(CmsOptions.CmsSubjectDto);
		options.pushDtoForModel(CmsContentsIndexedTable.class, CmsDtoContentsIndexedTable.class);
		CmsDtoContentsIndexedTable table = buildIndexedTable();
		dto.setContents(table);
		soaCms.saveOrUpdate(dto, options);
		daoCms.flush();
		Assert.assertNotNull(dto.getId());
		//
		dto = (CmsDtoImpl) soaCms.fetch(QueryBuilderCms.get().withId(dto.getId()), options).iterator().next();
		table = (CmsDtoContentsIndexedTable) dto.getContents();
		table.setOperation(TableOperation.Updated);
		// UPDATE A ROW AND CREATE A NEW ONE WITHOUT SETTING OPERATION ON ROW
		table.getRows().get(4).getCells().get(1).setDataString(null).setDataInt(5);
		table.createRowDto().createCell().setDataString("OK");
		soaCms.saveOrUpdate(dto, options);
		daoCms.flush();
		Assert.assertNotNull(dto.getId());
		//
		dto = (CmsDtoImpl) soaCms.fetch(QueryBuilderCms.get().withId(dto.getId()), options).iterator().next();
		table = (CmsDtoContentsIndexedTable) dto.getContents();
		Assert.assertEquals(6, table.getRows().size());
		for (int i = 0; i < 5; i++) {
			CmsDtoContentsIndexedTableRow row = table.getRows().get(i);
			if (i == 4) {
				Assert.assertEquals(5, row.getCells().get(1).getDataInt().intValue());
				Assert.assertNull(row.getCells().get(1).getDataString());
			}
			Assert.assertEquals("A" + i, row.getCells().get(0).getDataString());
			Assert.assertEquals("C" + i, row.getCells().get(2).getDataString());

		}
	}

	@Test
	@Transactional
	public void testShouldDeleteTableOnIndexTable() throws Exception {
		OptionsList options = new OptionsList();
		options.withOption(CmsOptions.CmsSubjectDto);
		options.pushDtoForModel(CmsContentsIndexedTable.class, CmsDtoContentsIndexedTable.class);
		CmsDtoContentsIndexedTable table = buildIndexedTable();
		dto.setContents(table);
		soaCms.saveOrUpdate(dto, options);
		daoCms.flush();
		Assert.assertNotNull(dto.getId());
		//
		dto = (CmsDtoImpl) soaCms.fetch(QueryBuilderCms.get().withId(dto.getId()), options).iterator().next();
		table = (CmsDtoContentsIndexedTable) dto.getContents();
		table.setOperation(TableOperation.Deleted);
		// DELETING ALL
		soaCms.saveOrUpdate(dto, options);
		daoCms.flush();
		Assert.assertNotNull(dto.getId());
		//
		dto = (CmsDtoImpl) soaCms.fetch(QueryBuilderCms.get().withId(dto.getId()), options).iterator().next();
		table = (CmsDtoContentsIndexedTable) dto.getContents();
		Assert.assertEquals(0, table.getRows().size());
	}

}
