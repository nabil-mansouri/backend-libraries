package com.nm.permissions;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.nm.permissions.converters.ResourceFetcher;
import com.nm.permissions.daos.DaoPermission;
import com.nm.permissions.daos.PermissionGridQueryBuilder;
import com.nm.permissions.dtos.DtoPermissionGrid;
import com.nm.permissions.dtos.DtoPermissionsForm;
import com.nm.permissions.dtos.DtoPermissionsView;
import com.nm.permissions.dtos.DtoSubject;
import com.nm.permissions.grids.GridEntriesSorted;
import com.nm.permissions.grids.GridEntriesSorter;
import com.nm.permissions.grids.GridEntriesSorterDefault;
import com.nm.permissions.grids.GridFinderStrategy;
import com.nm.permissions.grids.GridFinderStrategyDefault;
import com.nm.permissions.grids.GridMergerStrategy;
import com.nm.permissions.grids.GridMergerStrategyDisjunction;
import com.nm.permissions.models.PermissionGrid;
import com.nm.permissions.models.Resource;
import com.nm.permissions.models.Subject;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SoaPermissionImpl implements SoaPermission {
	@Autowired
	private DtoConverterRegistry registry;
	@Autowired
	private DaoPermission dao;

	public void setDao(DaoPermission dao) {
		this.dao = dao;
	}

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public Subject getOrCreate(DtoSubject dto, OptionsList options) throws PermissionException {
		try {
			if (dto.getIdSubject() == null) {
				Subject s = registry.search(dto, Subject.class).toEntity(dto, options);
				AbstractGenericDao.get(Subject.class).save(s);
				dto.setIdSubject(s.getId());
				//
				ResourceFetcher fetcher = options.getOverrides(ResourceFetcher.class,
						new ResourceFetcher.ResourceFetcherImpl());
				// BUILD DEFAULT GRID
				Collection<Resource> resources = dao.getOrCreateResources(fetcher.resource());
				dao.getOrCreateGrid(s, resources);
				return s;
			} else {
				return AbstractGenericDao.get(Subject.class).get(dto.getIdSubject());
			}
		} catch (Exception e) {
			throw new PermissionException(e);
		}
	}

	public DtoPermissionGrid editGrid(DtoSubject dto, OptionsList options) throws PermissionException {
		try {
			Subject subject = getOrCreate(dto, options);
			PermissionGrid grid = subject.getGrid();
			Class<DtoPermissionGrid> clazz = options.dtoForModel(PermissionGrid.class, DtoPermissionsForm.class);
			return registry.search(clazz, grid).toDto(grid, options);
		} catch (Exception e) {
			throw new PermissionException(e);
		}
	}

	public DtoPermissionGrid viewGrid(DtoSubject dto, OptionsList options) throws PermissionException {
		try {
			Subject subject = getOrCreate(dto, options);
			GridFinderStrategy finder = options.getOverrides(GridFinderStrategy.class, new GridFinderStrategyDefault());
			Collection<PermissionGrid> founded = finder.finder(subject);
			GridMergerStrategy merger = options.getOverrides(GridMergerStrategy.class,
					new GridMergerStrategyDisjunction());
			GridEntriesSorter sorter = options.getOverrides(GridEntriesSorter.class, new GridEntriesSorterDefault());
			PermissionGrid merged = merger.merge(sorter.sort(new GridEntriesSorted(), founded));
			Class<DtoPermissionGrid> clazz = options.dtoForModel(PermissionGrid.class, DtoPermissionsView.class);
			return registry.search(clazz, merged).toDto(merged, options);
		} catch (Exception e) {
			throw new PermissionException(e);
		}
	}

	public DtoPermissionGrid resetGrid(DtoSubject dto, OptionsList options) throws PermissionException {
		try {
			Subject subject = getOrCreate(dto, options);
			PermissionGrid grid = subject.getGrid();
			grid.getPermissions().clear();
			Class<DtoPermissionGrid> clazz = options.dtoForModel(PermissionGrid.class, DtoPermissionsForm.class);
			return registry.search(clazz, grid).toDto(grid, options);
		} catch (Exception e) {
			throw new PermissionException(e);
		}
	}

	public DtoPermissionGrid saveOrUpdate(DtoPermissionGrid dto, OptionsList options) throws PermissionException {
		try {
			PermissionGrid grid = registry.search(dto, PermissionGrid.class).toEntity(dto, options);
			dao.saveOrUpdate(grid);
			dto.setIdGrid(grid.getId());
			return dto;
		} catch (Exception e) {
			throw new PermissionException(e);
		}
	}

	public Collection<DtoPermissionGrid> fetch(PermissionGridQueryBuilder query, OptionsList options)
			throws PermissionException {
		try {
			Collection<PermissionGrid> grids = dao.find(query);
			Class<DtoPermissionGrid> clazz = options.dtoForModel(PermissionGrid.class, DtoPermissionsForm.class);
			return registry.search(clazz, PermissionGrid.class).toDto(grids, options);
		} catch (Exception e) {
			throw new PermissionException(e);
		}
	}

}
