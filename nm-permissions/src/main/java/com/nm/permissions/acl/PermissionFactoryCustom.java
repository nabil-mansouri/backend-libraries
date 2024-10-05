package com.nm.permissions.acl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.CumulativePermission;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.model.Permission;
import org.springframework.util.Assert;

import com.nm.permissions.constants.Action;
import com.nm.utils.json.EnumJsonConverterRegistry;

/**
 * @See org.springframework.security.acls.domain.DefaultPermissionFactory
 * @author Nabil MANSOURI
 *
 */
public class PermissionFactoryCustom implements PermissionFactory, ApplicationListener<ContextRefreshedEvent> {
	private final Map<Integer, Permission> registeredPermissionsByInteger = new HashMap<Integer, Permission>();
	private final Map<String, Permission> registeredPermissionsByName = new HashMap<String, Permission>();
	private EnumJsonConverterRegistry registry;

	public PermissionFactoryCustom(EnumJsonConverterRegistry r) {
		super();
		setRegistry(r);
	}

	public void setRegistry(EnumJsonConverterRegistry registry) {
		this.registry = registry;
	}

	public void refresh() {
		Set<String> keys = registry.findKeysByInterface(Action.class);
		int i = 0;
		// maximum of 32 shift
		for (String k : keys) {
			registerPermission(new BasePermission(1 << i, (char) i) {

				private static final long serialVersionUID = 1L;

			}, k);
			i++;
		}
	}

	public void onApplicationEvent(ContextRefreshedEvent event) {
		refresh();
	}

	protected void registerPermission(Permission perm, String permissionName) {
		Assert.notNull(perm, "Permission required");
		Assert.hasText(permissionName, "Permission name required");
		Integer mask = Integer.valueOf(perm.getMask());
		// Ensure no existing Permission uses this integer or code
		Assert.isTrue(!registeredPermissionsByInteger.containsKey(mask),
				"An existing Permission already provides mask " + mask);
		Assert.isTrue(!registeredPermissionsByName.containsKey(permissionName),
				"An existing Permission already provides name '" + permissionName + "'");
		// Register the new Permission
		registeredPermissionsByInteger.put(mask, perm);
		registeredPermissionsByName.put(permissionName, perm);
	}

	public Permission buildFromMask(int mask) {
		if (registeredPermissionsByInteger.containsKey(Integer.valueOf(mask))) {
			// The requested mask has an exact match against a
			// statically-defined
			// Permission, so return it
			return registeredPermissionsByInteger.get(Integer.valueOf(mask));
		}

		// To get this far, we have to use a CumulativePermission
		CumulativePermission permission = new CumulativePermission();

		for (int i = 0; i < 32; i++) {
			int permissionToCheck = 1 << i;

			if ((mask & permissionToCheck) == permissionToCheck) {
				Permission p = registeredPermissionsByInteger.get(Integer.valueOf(permissionToCheck));

				if (p == null) {
					throw new IllegalStateException(
							"Mask '" + permissionToCheck + "' does not have a corresponding static Permission");
				}
				permission.set(p);
			}
		}

		return permission;
	}

	public String getName(Permission perm) {
		for (String key : registeredPermissionsByName.keySet()) {
			if (registeredPermissionsByName.get(key).getMask() == perm.getMask()) {
				return key;
			}
		}
		// Maybe cumultative or other...
		return null;
	}

	public Action getAction(Permission perm) {
		String name = getName(perm);
		if (name != null) {
			return (Action) registry.find(name);
		}
		return null;
	}

	public Permission buildFromName(String name) {
		Permission p = registeredPermissionsByName.get(name);

		if (p == null) {
			throw new IllegalArgumentException("Unknown permission '" + name + "'");
		}

		return p;
	}

	public Permission buildFromAction(Action name) {
		return buildFromName(EnumJsonConverterRegistry.key(name));
	}

	public List<Permission> buildFromNames(List<String> names) {
		if ((names == null) || (names.size() == 0)) {
			return Collections.emptyList();
		}

		List<Permission> permissions = new ArrayList<Permission>(names.size());

		for (String name : names) {
			permissions.add(buildFromName(name));
		}

		return permissions;
	}
}
