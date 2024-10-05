package com.nm.permissions.grants;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.api.client.util.Lists;
import com.nm.auths.grants.AuthorityGranter;
import com.nm.auths.models.Authentication;
import com.nm.permissions.SoaPermission;
import com.nm.permissions.constants.Action;
import com.nm.permissions.constants.ResourceType;
import com.nm.permissions.dtos.DtoSubjectDefault;
import com.nm.permissions.grids.GridEntriesSorted;
import com.nm.permissions.grids.GridEntriesSorter;
import com.nm.permissions.grids.GridFinderStrategy;
import com.nm.permissions.grids.GridMergerStrategy;
import com.nm.permissions.models.Permission;
import com.nm.permissions.models.PermissionGrid;
import com.nm.permissions.models.Subject;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class PermissionAuthorityGranter implements AuthorityGranter {
	@Autowired
	private SoaPermission soaPermission;

	public OptionsList options() {
		OptionsList options = new OptionsList();
		options.pushOverrides(GridMergerStrategy.class, merger());
		options.pushOverrides(GridEntriesSorter.class, sorter());
		options.pushOverrides(GridFinderStrategy.class, finder());
		return options;
	}

	public final List<GrantedAuthority> buildUserAuthority(Authentication auth, List<GrantedAuthority> authorities) {
		DtoSubjectDefault dto = new DtoSubjectDefault();
		dto.setIdUser(auth.getUser().getId());
		//
		OptionsList options = new OptionsList();
		options.pushOverrides(GridFinderStrategy.class, finder());
		options.pushOverrides(GridEntriesSorter.class, sorter());
		options.pushOverrides(GridMergerStrategy.class, merger());
		//
		try {
			Subject subject = soaPermission.getOrCreate(dto, options);
			GridEntriesSorted dorted = sorter().sort(new GridEntriesSorted(), finder().finder(subject));
			PermissionGrid merged = merger().merge(dorted);
			for (Permission p : merged.getPermissions()) {
				if (p.isGranted()) {
					authorities.addAll(toAuthority(p));
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return authorities;
	}

	public static String authorityName(ResourceType r, Action a) {
		// ROLE PREFIX MANDATORY
		return String.format("ROLE_%s.%s", r.name(), a.name());
	}

	protected String key(Permission p) {
		return authorityName(p.getResource().getType(), p.getAction());
	}

	protected Collection<GrantedAuthority> toAuthority(Permission p) {
		Collection<GrantedAuthority> all = Lists.newArrayList();
		all.add(new SimpleGrantedAuthority(key(p)));
		return all;
	}

	protected abstract GridMergerStrategy merger();

	protected abstract GridEntriesSorter sorter();

	protected abstract GridFinderStrategy finder();
}
