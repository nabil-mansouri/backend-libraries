package com.nm.permissions.converters;

import java.util.Collection;
import java.util.Set;

import com.nm.permissions.ActionUtils;
import com.nm.permissions.constants.Action;
import com.nm.permissions.daos.DaoPermission;
import com.nm.permissions.dtos.DtoPermissionEntry;
import com.nm.permissions.dtos.DtoPermissionsView;
import com.nm.permissions.models.Permission;
import com.nm.permissions.models.PermissionGrid;
import com.nm.permissions.models.Resource;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.graphs.GraphInfo;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;
import com.nm.utils.graphs.listeners.IteratorListenerGraphInfo;

/**
 * 
 * @author nabilmansouri
 *
 */
public class PermissionViewConverter extends DtoConverterDefault<DtoPermissionsView, PermissionGrid> {

	private DaoPermission dao;

	public void setDao(DaoPermission dao) {
		this.dao = dao;
	}

	public DtoPermissionsView toDto(final DtoPermissionsView dto, final PermissionGrid entity, OptionsList options)
			throws DtoConvertException {
		try {
			dto.setId(entity.getId());
			ResourceFetcher fetcher = options.getOverrides(ResourceFetcher.class,
					new ResourceFetcher.ResourceFetcherImpl());
			Collection<Resource> resources = dao.getOrCreateResources(fetcher.resource());
			final Set<Action> actions = ActionUtils.available(resources);
			// NO NEED TO SET VIEW => Null when merged
			// resources tree
			for (Resource r : resources) {
				// ITERATE ONLY OVER ROOT
				if (r.root()) {
					GraphIteratorBuilder.buildDeep().iterate(r, new IteratorListenerGraphInfo() {

						public void onFounded(GraphInfo node) {
							Resource r = (Resource) node.getCurrent();
							for (Action a : actions) {
								DtoPermissionEntry value = new DtoPermissionEntry();
								value.setAction(a);
								value.setType(r.getType());
								value.setLevel(node.level());
								if (r.getAvailable().contains(a)) {
									value.setEnable(true);
									Permission p = entity.find(a, r);
									if (p != null) {
										value.setSelected(p.isGranted());
									} else {
										// DEFAULT TO FALSE
										value.setSelected(false);
									}
								} else {
									value.setEnable(false);
									value.setSelected(false);
								}
								dto.getValues().add(value);
							}
						}
					});
				}
			}
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public PermissionGrid toEntity(DtoPermissionsView bean, OptionsList options) throws DtoConvertException {
		try {
			throw new IllegalStateException("Could not build from view");
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
