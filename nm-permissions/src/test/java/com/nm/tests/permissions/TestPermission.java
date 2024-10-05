package com.nm.tests.permissions;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nm.auths.dtos.DtoAuthenticationDefault;
import com.nm.auths.models.User;
import com.nm.auths.models.UserGroup;
import com.nm.auths.soa.SoaAuthentication;
import com.nm.permissions.ActionUtils;
import com.nm.permissions.SoaPermission;
import com.nm.permissions.constants.Action;
import com.nm.permissions.constants.ActionDefault;
import com.nm.permissions.constants.ResourceType;
import com.nm.permissions.constants.ResourceTypeDefault;
import com.nm.permissions.converters.ResourceFetcher;
import com.nm.permissions.daos.DaoPermission;
import com.nm.permissions.daos.ResourceQueryBuilder;
import com.nm.permissions.daos.SubjectQueryBuilder;
import com.nm.permissions.dtos.DtoPermissionEntry;
import com.nm.permissions.dtos.DtoPermissionsForm;
import com.nm.permissions.dtos.DtoPermissionsView;
import com.nm.permissions.dtos.DtoSubjectDefault;
import com.nm.permissions.grids.GridEntriesSorted;
import com.nm.permissions.grids.GridEntriesSorter;
import com.nm.permissions.grids.GridEntriesSorterDefault;
import com.nm.permissions.grids.GridEntriesSorterInheritChildrenFirst;
import com.nm.permissions.grids.GridEntriesSorterMultiple;
import com.nm.permissions.grids.GridFinderStrategy;
import com.nm.permissions.grids.GridFinderStrategyDefault;
import com.nm.permissions.grids.GridFinderStrategyVoid;
import com.nm.permissions.grids.GridMergerStrategy;
import com.nm.permissions.grids.GridMergerStrategyDisjunction;
import com.nm.permissions.models.Permission;
import com.nm.permissions.models.PermissionGrid;
import com.nm.permissions.models.Resource;
import com.nm.permissions.models.Subject;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = { ConfigurationTestPermission.class })
public class TestPermission {

	@Autowired
	private SoaPermission soaPermission;
	@Autowired
	private SoaAuthentication soa;
	@Autowired
	private DaoPermission daoPermission;
	IGenericDao<Subject, Long> daoSub;
	//
	private OptionsList options = new OptionsList();
	protected Log log = LogFactory.getLog(getClass());

	private User user = new User();
	private UserGroup group = new UserGroup();

	@org.junit.Before
	public void setup() throws Exception {
		daoSub = AbstractGenericDao.get(Subject.class);
		DtoAuthenticationDefault dto = new DtoAuthenticationDefault("user1@example.com", "user1");
		soa.saveOrUpdate(dto, new OptionsList());
		user = AbstractGenericDao.get(User.class).get(dto.getUserId());
		group.setName("GROUP1");
		group.add(user);
		AbstractGenericDao.get(UserGroup.class).save(group);
	}

	@Test

	public void testShouldCreateSubjectUser() throws Exception {
		DtoSubjectDefault dto = new DtoSubjectDefault();
		dto.setIdUser(user.getId());
		soaPermission.getOrCreate(dto, options);
		daoSub.flush();
		Assert.assertNotNull(dto.getId());
		Assert.assertEquals(1, daoSub.find(SubjectQueryBuilder.getSubjectUser()).size());
		Assert.assertEquals(0, daoSub.find(SubjectQueryBuilder.getSubjectGroups()).size());
	}

	@Test
	public void testShouldCreateSubjectGroup() throws Exception {
		DtoSubjectDefault dto = new DtoSubjectDefault();
		dto.setIdGroup(group.getId());
		soaPermission.getOrCreate(dto, options);
		daoSub.flush();
		Assert.assertNotNull(dto.getId());
		Assert.assertEquals(1, daoSub.find(SubjectQueryBuilder.getSubjectGroups()).size());
		Assert.assertEquals(0, daoSub.find(SubjectQueryBuilder.getSubjectUser()).size());
	}

	@Test()

	public void testShouldCreateSubjectUserOnce() throws Exception {
		Set<Long> ids = Sets.newHashSet();
		for (int i = 0; i < 3; i++) {
			DtoSubjectDefault dto = new DtoSubjectDefault();
			dto.setIdUser(user.getId());
			soaPermission.getOrCreate(dto, options);
			daoSub.flush();
			ids.add(dto.getId());
		}
		Assert.assertEquals(1, ids.size());
	}

	@Test

	public void testShouldCreateSubjectGroupOnce() throws Exception {
		Set<Long> ids = Sets.newHashSet();
		for (int i = 0; i < 3; i++) {
			DtoSubjectDefault dto = new DtoSubjectDefault();
			dto.setIdGroup(group.getId());
			soaPermission.getOrCreate(dto, options);
			daoSub.flush();
			ids.add(dto.getId());
		}
		Assert.assertEquals(1, ids.size());
	}

	@Test

	public void testShouldCreateResources() throws Exception {
		Collection<ResourceType> types = new ResourceFetcher.ResourceFetcherImpl().resource();
		Collection<Resource> resources = daoPermission.getOrCreateResources(types);
		daoSub.flush();
		Assert.assertEquals(types.size(), resources.size());

	}

	@Test

	@SuppressWarnings("unchecked")
	public void testShouldCreateResourcesWithInheritance() throws Exception {
		Collection<ResourceType> types = new ResourceFetcher.ResourceFetcherImpl().resource();
		Collection<Resource> resources = daoPermission.getOrCreateResources(types);
		daoSub.flush();
		resources = (Collection<Resource>) daoSub.getHibernateTemplate()
				.findByCriteria(ResourceQueryBuilder.get().withType(ResourceTypeDefault.DefaultChild).getQuery());
		Assert.assertEquals(1, resources.size());
		Assert.assertNotNull(resources.iterator().next().getParent());
		Assert.assertEquals(ResourceTypeDefault.Default, resources.iterator().next().getParent().getType());
	}

	private Collection<Permission> selected() {
		Collection<Permission> pp = Lists.newArrayList();
		Collection<ResourceType> resources = new ResourceFetcher.ResourceFetcherImpl().resource();
		for (ResourceType r : resources) {
			for (Action a : r.lower()) {
				Permission p = new Permission();
				p.setAction(a);
				p.setResource(new Resource());
				p.getResource().setType(r);
				pp.add(p);
			}
		}
		return pp;
	}

	@Test
	public void testShouldCreateDefaultGrid() throws Exception {
		DtoSubjectDefault dto = new DtoSubjectDefault();
		dto.setIdGroup(group.getId());
		soaPermission.getOrCreate(dto, options);
		daoSub.flush();
		Assert.assertNotNull(dto.getId());
		Subject subject = daoSub.find(SubjectQueryBuilder.getSubjectGroups()).iterator().next();
		daoSub.refresh(subject);
		Assert.assertNotNull(subject.getGrid());
		for (Permission r : selected()) {
			Permission p = subject.getGrid().find(r.getAction(), r.getResource().getType());
			Assert.assertNotNull(p);
			Assert.assertTrue(p.isGranted());
		}
		Assert.assertNotEquals(0, selected().size());
	}

	@Test
	public void testShouldBuildEditGridForGroup() throws Exception {
		DtoSubjectDefault dto = new DtoSubjectDefault();
		dto.setIdGroup(group.getId());
		soaPermission.getOrCreate(dto, options);
		daoSub.flush();
		DtoPermissionsForm dtoGrid = (DtoPermissionsForm) soaPermission.editGrid(dto, options);
		Assert.assertNotNull(dtoGrid.getIdGrid());
		Collection<ResourceType> resources = new ResourceFetcher.ResourceFetcherImpl().resource();
		final Set<Action> actions = ActionUtils.available(daoPermission.getOrCreateResources(resources));
		Assert.assertEquals(resources.size(), dtoGrid.getResources().size());
		Assert.assertEquals(resources.size() * actions.size(), dtoGrid.getValues().size());
		for (Permission r : selected()) {
			Collection<DtoPermissionEntry> p = dtoGrid.getBy(r.getResource().getType(), r.getAction());
			Assert.assertTrue(p.iterator().next().isEnable());
			Assert.assertTrue(p.iterator().next().getSelectedSafe());
		}
		Assert.assertNotEquals(0, selected().size());
	}

	@Test

	public void testShouldBuildEditGridForUser() throws Exception {
		DtoSubjectDefault dto = new DtoSubjectDefault();
		dto.setIdUser(user.getId());
		soaPermission.getOrCreate(dto, options);
		daoSub.flush();
		DtoPermissionsForm dtoGrid = (DtoPermissionsForm) soaPermission.editGrid(dto, options);
		Assert.assertNotNull(dtoGrid.getIdGrid());
		Collection<ResourceType> resources = new ResourceFetcher.ResourceFetcherImpl().resource();
		final Set<Action> actions = ActionUtils.available(daoPermission.getOrCreateResources(resources));
		Assert.assertEquals(resources.size(), dtoGrid.getResources().size());
		Assert.assertEquals(resources.size() * actions.size(), dtoGrid.getValues().size());
		for (Permission r : selected()) {
			Collection<DtoPermissionEntry> p = dtoGrid.getBy(r.getResource().getType(), r.getAction());
			Assert.assertTrue(p.iterator().next().isEnable());
			Assert.assertTrue(p.iterator().next().getSelectedSafe());
		}
		Assert.assertNotEquals(0, selected().size());
	}

	@Test

	public void testShouldBuildViewGridForUserWithoutMerge() throws Exception {
		options.pushOverrides(GridFinderStrategy.class, new GridFinderStrategyVoid());
		//
		DtoSubjectDefault dto = new DtoSubjectDefault();
		dto.setIdUser(user.getId());
		soaPermission.getOrCreate(dto, options);
		daoSub.flush();
		DtoPermissionsView dtoGrid = (DtoPermissionsView) soaPermission.viewGrid(dto, options);
		System.out.println(dtoGrid);
		Collection<ResourceType> resources = new ResourceFetcher.ResourceFetcherImpl().resource();
		final Set<Action> actions = ActionUtils.available(daoPermission.getOrCreateResources(resources));
		Assert.assertEquals(resources.size() * actions.size(), dtoGrid.getValues().size());
		for (Permission r : selected()) {
			Collection<DtoPermissionEntry> p = dtoGrid.getBy(r.getResource().getType(), r.getAction());
			Assert.assertTrue(p.iterator().next().isEnable());
			Assert.assertTrue(p.iterator().next().getSelectedSafe());
		}
		Assert.assertNotEquals(0, selected().size());
	}

	@Test

	public void testShouldGrantPermission() throws Exception {
		options.pushOverrides(GridFinderStrategy.class, new GridFinderStrategyVoid());
		//
		DtoSubjectDefault dto = new DtoSubjectDefault();
		dto.setIdUser(user.getId());
		soaPermission.getOrCreate(dto, options);
		daoSub.flush();
		DtoPermissionsForm dtoGrid = (DtoPermissionsForm) soaPermission.editGrid(dto, options);
		DtoPermissionEntry toGrant = null;
		for (DtoPermissionEntry r : dtoGrid.getValues()) {
			if (!r.getSelectedSafe() && r.isEnable()) {
				toGrant = r;
			}
		}
		Assert.assertNotNull(toGrant);
		dtoGrid.getBy(toGrant.getType(), toGrant.getAction()).iterator().next().setSelected(true);
		soaPermission.saveOrUpdate(dtoGrid, options);
		daoSub.flush();
		// TEST IF SAVED
		dtoGrid = (DtoPermissionsForm) soaPermission.editGrid(dto, options);
		for (Permission r : selected()) {
			Collection<DtoPermissionEntry> p = dtoGrid.getBy(r.getResource().getType(), r.getAction());
			Assert.assertTrue(p.iterator().next().isEnable());
			Assert.assertTrue(p.iterator().next().getSelectedSafe());
		}
		Collection<DtoPermissionEntry> p = dtoGrid.getBy(toGrant.getType(), toGrant.getAction());
		Assert.assertTrue(p.iterator().next().isEnable());
		Assert.assertTrue(p.iterator().next().getSelectedSafe());
	}

	@Test

	public void testShouldRevokePermission() throws Exception {
		options.pushOverrides(GridFinderStrategy.class, new GridFinderStrategyVoid());
		//
		DtoSubjectDefault dto = new DtoSubjectDefault();
		dto.setIdUser(user.getId());
		soaPermission.getOrCreate(dto, options);
		daoSub.flush();
		DtoPermissionsForm dtoGrid = (DtoPermissionsForm) soaPermission.editGrid(dto, options);
		DtoPermissionEntry toGrant = null;
		for (DtoPermissionEntry r : dtoGrid.getValues()) {
			if (r.getSelectedSafe() && r.isEnable()) {
				toGrant = r;
			}
		}
		Assert.assertNotNull(toGrant);
		dtoGrid.getBy(toGrant.getType(), toGrant.getAction()).iterator().next().setSelected(false);
		soaPermission.saveOrUpdate(dtoGrid, options);
		daoSub.flush();
		// TEST IF SAVED
		dtoGrid = (DtoPermissionsForm) soaPermission.editGrid(dto, options);
		Collection<DtoPermissionEntry> p = dtoGrid.getBy(toGrant.getType(), toGrant.getAction());
		Assert.assertTrue(p.iterator().next().isEnable());
		Assert.assertFalse(p.iterator().next().getSelectedSafe());
	}

	@Test

	public void testShouldResetPermission() throws Exception {
		options.pushOverrides(GridFinderStrategy.class, new GridFinderStrategyVoid());
		//
		DtoSubjectDefault dto = new DtoSubjectDefault();
		dto.setIdUser(user.getId());
		soaPermission.getOrCreate(dto, options);
		daoSub.flush();
		soaPermission.resetGrid(dto, options);
		daoSub.flush();
		DtoPermissionsView dtoGrid = (DtoPermissionsView) soaPermission.viewGrid(dto, options);
		for (Permission r : selected()) {
			DtoPermissionEntry p = dtoGrid.getFirstBy(r.getResource().getType(), r.getAction());
			Assert.assertTrue(p.isEnable());
			Assert.assertFalse(p.getSelectedSafe());
		}
		Assert.assertNotEquals(0, selected().size());
	}

	@Test

	public void testShouldMergePermission() throws Exception {
		DtoSubjectDefault dtoUser = new DtoSubjectDefault();
		{
			dtoUser.setIdUser(user.getId());
			soaPermission.getOrCreate(dtoUser, options);
		}
		DtoSubjectDefault dtoGroup = new DtoSubjectDefault();
		{
			dtoGroup.setIdGroup(group.getId());
			soaPermission.getOrCreate(dtoGroup, options);
		}
		daoSub.flush();
		soaPermission.resetGrid(dtoGroup, options);
		soaPermission.resetGrid(dtoUser, options);
		daoSub.flush();
		DtoPermissionsForm dtoGridUserForm = (DtoPermissionsForm) soaPermission.editGrid(dtoUser, options);
		DtoPermissionsForm dtoGridGroupForm = (DtoPermissionsForm) soaPermission.editGrid(dtoGroup, options);
		// SHOULD HAS RESET
		DtoPermissionsView dtoGridUser = (DtoPermissionsView) soaPermission.viewGrid(dtoUser, options);
		DtoPermissionsView dtoGridGroup = (DtoPermissionsView) soaPermission.viewGrid(dtoGroup, options);
		Assert.assertFalse(dtoGridUser.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).getSelectedSafe());
		Assert.assertFalse(dtoGridGroup.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).getSelectedSafe());
		// GRANT FOR ALL
		dtoGridUserForm.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).get().setSelected(true);
		dtoGridGroupForm.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).get().setSelected(true);
		soaPermission.saveOrUpdate(dtoGridUserForm, options);
		soaPermission.saveOrUpdate(dtoGridGroupForm, options);
		daoSub.flush();
		// SHOULD HAS GRANT
		dtoGridUser = (DtoPermissionsView) soaPermission.viewGrid(dtoUser, options);
		dtoGridGroup = (DtoPermissionsView) soaPermission.viewGrid(dtoGroup, options);
		Assert.assertTrue(dtoGridUser.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).getSelectedSafe());
		Assert.assertTrue(dtoGridGroup.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).getSelectedSafe());
		// REVOKE FOR USER
		dtoGridUserForm.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).get().setSelected(false);
		dtoGridGroupForm.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).get().setSelected(true);
		soaPermission.saveOrUpdate(dtoGridUserForm, options);
		soaPermission.saveOrUpdate(dtoGridGroupForm, options);
		daoSub.flush();
		// SHOULD STILL GRANT BOTH
		dtoGridUser = (DtoPermissionsView) soaPermission.viewGrid(dtoUser, options);
		dtoGridGroup = (DtoPermissionsView) soaPermission.viewGrid(dtoGroup, options);
		Assert.assertTrue(dtoGridUser.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).getSelectedSafe());
		Assert.assertTrue(dtoGridGroup.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).getSelectedSafe());
		// REVOKE FOR GROUP BUT NOT USER
		dtoGridUserForm.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).get().setSelected(true);
		dtoGridGroupForm.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).get().setSelected(false);
		soaPermission.saveOrUpdate(dtoGridUserForm, options);
		soaPermission.saveOrUpdate(dtoGridGroupForm, options);
		daoSub.flush();
		// SHOULD GRANT FOR USER BUT NOT GROUP
		dtoGridUser = (DtoPermissionsView) soaPermission.viewGrid(dtoUser, options);
		dtoGridGroup = (DtoPermissionsView) soaPermission.viewGrid(dtoGroup, options);
		Assert.assertTrue(dtoGridUser.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).getSelectedSafe());
		Assert.assertFalse(dtoGridGroup.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).getSelectedSafe());
		// REVOKE FOR ALL
		dtoGridUserForm.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).get().setSelected(false);
		dtoGridGroupForm.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).get().setSelected(false);
		soaPermission.saveOrUpdate(dtoGridUserForm, options);
		soaPermission.saveOrUpdate(dtoGridGroupForm, options);
		daoSub.flush();
		// SHOULD GRANT FOR NO ONE
		dtoGridUser = (DtoPermissionsView) soaPermission.viewGrid(dtoUser, options);
		dtoGridGroup = (DtoPermissionsView) soaPermission.viewGrid(dtoGroup, options);
		Assert.assertFalse(dtoGridUser.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).getSelectedSafe());
		Assert.assertFalse(dtoGridGroup.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).getSelectedSafe());
	}

	@Test

	public void testShouldMergePermissionUsingDisjunction() throws Exception {
		PermissionGrid grid1 = new PermissionGrid();
		grid1.add(new Permission().setGranted(true).setAction(ActionDefault.Access)
				.setResource(new Resource().setType(ResourceTypeDefault.Default)));
		PermissionGrid grid2 = new PermissionGrid();
		grid2.add(new Permission().setGranted(true).setAction(ActionDefault.List)
				.setResource(new Resource().setType(ResourceTypeDefault.Default)));
		//
		GridMergerStrategy strategy = new GridMergerStrategyDisjunction();
		GridEntriesSorter sorter = new GridEntriesSorterDefault();
		PermissionGrid grid3 = strategy.merge(sorter.sort(new GridEntriesSorted(), Arrays.asList(grid1, grid2)));
		Assert.assertEquals(2, grid3.getPermissions().size());
		Assert.assertTrue(grid3.find(ActionDefault.Access, ResourceTypeDefault.Default).isGranted());
		Assert.assertTrue(grid3.find(ActionDefault.List, ResourceTypeDefault.Default).isGranted());
	}

	@Test

	public void testShouldFindGridForUser() throws Exception {
		{
			DtoSubjectDefault dto = new DtoSubjectDefault();
			dto.setIdGroup(group.getId());
			soaPermission.getOrCreate(dto, options);
		}
		DtoSubjectDefault dto = new DtoSubjectDefault();
		dto.setIdUser(user.getId());
		soaPermission.getOrCreate(dto, options);
		//
		Subject subject = daoPermission.getHibernateTemplate().get(Subject.class, dto.getId());
		GridFinderStrategy strategy = new GridFinderStrategyDefault();
		Collection<PermissionGrid> grids = strategy.finder(subject);
		Assert.assertEquals(2, grids.size());
	}

	@Test

	public void testShouldFindGridForGroup() throws Exception {
		{
			DtoSubjectDefault dto = new DtoSubjectDefault();
			dto.setIdUser(user.getId());
			soaPermission.getOrCreate(dto, options);
		}
		DtoSubjectDefault dto = new DtoSubjectDefault();
		dto.setIdGroup(group.getId());
		soaPermission.getOrCreate(dto, options);
		//
		Subject subject = daoPermission.getHibernateTemplate().get(Subject.class, dto.getId());
		GridFinderStrategy strategy = new GridFinderStrategyDefault();
		Collection<PermissionGrid> grids = strategy.finder(subject);
		Assert.assertEquals(1, grids.size());
	}

	@Test

	public void testShouldGrantWithInheritance() throws Exception {
		// MUST KEEP THIS ORDER
		GridEntriesSorterMultiple multiple = new GridEntriesSorterMultiple();
		multiple.add(new GridEntriesSorterDefault());
		multiple.add(new GridEntriesSorterInheritChildrenFirst());
		options.pushOverrides(GridEntriesSorter.class, multiple);
		//
		DtoSubjectDefault dto = new DtoSubjectDefault();
		dto.setIdGroup(group.getId());
		soaPermission.getOrCreate(dto, options);
		soaPermission.resetGrid(dto, options);
		daoSub.flush();
		// SHOULD NOT BE GRANTED
		DtoPermissionsView dtoGrid = (DtoPermissionsView) soaPermission.viewGrid(dto, options);
		Assert.assertFalse(dtoGrid.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).getSelectedSafe());
		Assert.assertFalse(dtoGrid.getFirstBy(ResourceTypeDefault.DefaultChild, ActionDefault.List).getSelectedSafe());
		// GRANT FOR PARENT
		DtoPermissionsForm dtoGridForm = (DtoPermissionsForm) soaPermission.editGrid(dto, options);
		dtoGridForm.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).get().setSelected(true);
		soaPermission.saveOrUpdate(dtoGridForm, options);
		daoSub.flush();
		// SHOULD BE GRANTED FOR PARENT AND CHILDREN
		dtoGrid = (DtoPermissionsView) soaPermission.viewGrid(dto, options);
		Assert.assertTrue(dtoGrid.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).getSelectedSafe());
		Assert.assertTrue(dtoGrid.getFirstBy(ResourceTypeDefault.DefaultChild, ActionDefault.List).getSelectedSafe());
		// REVOKE FOR CHILD
		dtoGridForm.getFirstBy(ResourceTypeDefault.DefaultChild, ActionDefault.List).get().setSelected(false);
		soaPermission.saveOrUpdate(dtoGridForm, options);
		daoSub.flush();
		// SHOULD BE GRANTED FOR PARENT BUT NOT CHILDREN
		dtoGrid = (DtoPermissionsView) soaPermission.viewGrid(dto, options);
		Assert.assertTrue(dtoGrid.getFirstBy(ResourceTypeDefault.Default, ActionDefault.List).getSelectedSafe());
		Assert.assertFalse(dtoGrid.getFirstBy(ResourceTypeDefault.DefaultChild, ActionDefault.List).getSelectedSafe());

	}
}
