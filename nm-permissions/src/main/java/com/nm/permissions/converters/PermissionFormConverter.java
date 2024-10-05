package com.nm.permissions.converters;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.google.api.client.util.Maps;
import com.google.common.collect.Sets;
import com.nm.permissions.ActionUtils;
import com.nm.permissions.constants.Action;
import com.nm.permissions.constants.ResourceType;
import com.nm.permissions.daos.DaoPermission;
import com.nm.permissions.daos.DaoResource;
import com.nm.permissions.dtos.DtoPermissionEntry;
import com.nm.permissions.dtos.DtoPermissionsForm;
import com.nm.permissions.dtos.DtoResource;
import com.nm.permissions.dtos.DtoResourceDefault;
import com.nm.permissions.dtos.DtoSubject;
import com.nm.permissions.dtos.DtoSubjectDefault;
import com.nm.permissions.models.Permission;
import com.nm.permissions.models.PermissionGrid;
import com.nm.permissions.models.Resource;
import com.nm.permissions.models.Subject;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.graphs.GraphInfo;
import com.nm.utils.graphs.iterators.GraphIteratorBuilder;
import com.nm.utils.graphs.listeners.IteratorListenerGraphInfo;

/**
 * 
 * @author nabilmansouri
 *
 */
public class PermissionFormConverter extends DtoConverterDefault<DtoPermissionsForm, PermissionGrid> {
	private DtoConverterRegistry registry;
	private DaoPermission dao;

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public void setDao(DaoPermission dao) {
		this.dao = dao;
	}

	public DtoPermissionsForm toDto(final DtoPermissionsForm dto, final PermissionGrid entity, OptionsList options)
			throws DtoConvertException {
		try {
			dto.setId(entity.getId());
			ResourceFetcher fetcher = options.getOverrides(ResourceFetcher.class,
					new ResourceFetcher.ResourceFetcherImpl());
			Collection<Resource> resources = dao.getOrCreateResources(fetcher.resource());
			//
			Class<? extends DtoSubject> clazzS = options.dtoForModel(Subject.class, DtoSubjectDefault.class);
			DtoSubject sub = registry.search(clazzS, entity.getSubject()).toDto(entity.getSubject(), options);
			dto.setSubject(sub);
			//
			Class<? extends DtoResource> clazzR = options.dtoForModel(Resource.class, DtoResourceDefault.class);
			DtoConverter<DtoResource, Resource> converter = registry.search(clazzR, Resource.class);
			//
			final Set<Action> actions = ActionUtils.available(resources);
			for (Resource r : resources) {
				dto.getResources().add(converter.toDto(r, options));
			}
			dto.setActions(actions);
			// RESOURCE TREE
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
										// DO NOT DEFAULT VALUE ON FORM
										// value.setSelected(false);
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

	public PermissionGrid toEntity(DtoPermissionsForm dto, OptionsList options) throws DtoConvertException {
		try {
			PermissionGrid entity = new PermissionGrid();
			if (dto.getId() != null) {
				entity = dao.get(dto.getId());
			}
			return toEntity(entity, dto, options);
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public PermissionGrid toEntity(PermissionGrid entity, DtoPermissionsForm dto, OptionsList options)
			throws DtoConvertException {
		try {
			DaoResource daoR = ApplicationUtils.getBean(DaoResource.class);
			Subject subject = registry.search(dto.getSubject(), Subject.class).toEntity(dto.getSubject(), options);
			entity.setSubject(subject);
			//
			Collection<String> idResources = Sets.newHashSet();
			for (DtoResource r : dto.getResources()) {
				idResources.add(r.getIdResource());
			}
			Collection<Resource> resources = (daoR.findByIds(idResources));
			Map<ResourceType, Resource> resourcesBy = Maps.newHashMap();
			for (Resource r : resources) {
				resourcesBy.put(r.getType(), r);
			}
			//
			entity.getPermissions().clear();
			for (DtoPermissionEntry entry : dto.getValues()) {
				if (entry.isEnable() && entry.getSelected() != null) {
					Permission perm = new Permission();
					perm.setAction(entry.getAction());
					perm.setGranted(entry.getSelected());
					perm.setResource(resourcesBy.get(entry.getType()));
					entity.add(perm);
				}
			}
			return entity;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
