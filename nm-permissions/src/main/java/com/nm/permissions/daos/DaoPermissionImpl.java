package com.nm.permissions.daos;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import com.nm.app.async.FutureTaskContractEach;
import com.nm.app.async.FuturedTask;
import com.nm.permissions.constants.Action;
import com.nm.permissions.constants.ResourceType;
import com.nm.permissions.models.Permission;
import com.nm.permissions.models.PermissionGrid;
import com.nm.permissions.models.Resource;
import com.nm.permissions.models.Subject;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class DaoPermissionImpl extends AbstractGenericDao<PermissionGrid, Long>implements DaoPermission {
	@Override
	protected Class<PermissionGrid> getClassName() {
		return PermissionGrid.class;
	}

	@Override
	protected String getIdPropertyName() {
		return "id";
	}

	public PermissionGrid getOrCreateGrid(Subject subject, Collection<Resource> resources) {
		PermissionGrid grid = findFirstOrNull(PermissionGridQueryBuilder.get().withSubject(subject));
		if (grid == null) {
			grid = new PermissionGrid();
			grid.setSubject(subject);
			for (Resource resource : resources) {
				Collection<Action> actions = new HashSet<Action>();
				// LOWER
				actions.addAll(resource.getType().lower());
				for (Action action : actions) {
					Permission perm = new Permission();
					perm.setAction(action);
					perm.setGranted(true);
					perm.setResource(resource);
					grid.add(perm);
				}
			}
			saveOrUpdate(grid);
		}
		return grid;
	}

	private static class FutureRessourceParent extends FutureTaskContractEach<Resource> {

		public FutureRessourceParent(Resource model) {
			super(model);
		}

		@Override
		public void execute(Collection<Resource> all, final Resource current) {
			final ResourceType parent = current.getType().parent();
			if (parent != null) {
				Iterator<Resource> it = all.stream().filter(u->parent.equals(u.getType())).iterator();
				while (it.hasNext()) {
					it.next().addChild(current);
				}
			}
		}
	}

	public Collection<Resource> getOrCreateResources(Collection<ResourceType> types) {
		IGenericDao<Resource, Long> resourceDao = AbstractGenericDao.get(Resource.class);
		Collection<Resource> resources = resourceDao.find(ResourceQueryBuilder.get().withType(types));
		if (resources.isEmpty()) {
			FuturedTask task = new FuturedTask();
			for (ResourceType type : types) {
				Resource resource = new Resource();
				resource.getAvailable().addAll(type.selectable());
				resource.setType(type);
				resources.add(resource);
				task.push(new FutureRessourceParent(resource));
			}
			task.start();
			resourceDao.saveOrUpdate(resources);
		}
		return resources;
	}

}
