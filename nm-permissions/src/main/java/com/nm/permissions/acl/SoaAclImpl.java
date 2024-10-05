package com.nm.permissions.acl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.common.collect.Lists;
import com.nm.permissions.acl.dtos.DtoAcl;
import com.nm.permissions.acl.dtos.DtoAclObjectId;
import com.nm.permissions.acl.dtos.DtoAclPermission;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SoaAclImpl implements SoaAcl {

	private MutableAclService aclServiceMutable;
	private DtoConverterRegistry registry;
	private ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy = new ObjectIdentityRetrievalStrategyImpl();
	private SidRetrievalStrategy sidRetrievalStrategy = new SidRetrievalStrategyImpl();

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public void setAclServiceMutable(MutableAclService aclServiceMutable) {
		this.aclServiceMutable = aclServiceMutable;
	}

	public <T> Collection<T> filter(Collection<T> list, Collection<Permission> perms) throws AclException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CollectionFilterer<T> filterer = new CollectionFilterer<T>(list);
		for (T domainObject : filterer) {
			// Ignore nulls or entries
			if (domainObject == null) {
				continue;
			}
			if (!hasPermission(authentication, domainObject, perms)) {
				filterer.remove(domainObject);
			}
		}
		return (Collection<T>) filterer.getFilteredObject();
	}

	private boolean hasPermission(Authentication authentication, Object domainObject, Collection<Permission> perms) {
		try {
			// Obtain the OID applicable to the domain object
			ObjectIdentity objectIdentity = objectIdentityRetrievalStrategy.getObjectIdentity(domainObject);
			// Obtain the SIDs applicable to the principal
			List<Sid> sids = sidRetrievalStrategy.getSids(authentication);
			// Lookup only ACLs for SIDs we're interested in
			Acl acl = aclServiceMutable.readAclById(objectIdentity, sids);
			return acl.isGranted(new ArrayList<Permission>(perms), sids, false);
		} catch (Exception ignore) {
			return false;
		}
	}

	public <T> Collection<T> filter(Collection<T> list, Permission... permissions) throws AclException {
		return filter(list, Arrays.asList(permissions));
	}

	public void checkAuthorization(DtoAclObjectId object, Collection<? extends DtoAclPermission> perm) throws AccessDeniedException {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				throw new AccessDeniedException("Cannot authorize for anonymous user");
			}
			OptionsList options = new OptionsList();
			DtoConverter<DtoAclObjectId, ObjectIdentity> converter = registry.search(object.getClass(), ObjectIdentity.class);
			ObjectIdentity identity = converter.toEntity(object, options);
			// PERMISSIONS
			DtoConverter<DtoAclPermission, Permission> conv = registry.search(perm.iterator().next().getClass(), Permission.class);
			List<Permission> perms = Lists.newArrayList();
			for (DtoAclPermission p : perm) {
				perms.add(conv.toEntity(p, options));
			}
			// COMPUTE
			List<Sid> sids = sidRetrievalStrategy.getSids(authentication);
			org.springframework.security.acls.model.Acl acl = aclServiceMutable.readAclById(identity, sids);
			if (!acl.isGranted(perms, sids, false)) {
				throw new AccessDeniedException("Current user is not authorized to access object: " + object);
			}
		} catch (Exception e) {
			throw new AccessDeniedException(e.getMessage(), e);
		}
	}

	public void checkAuthorization(Object object, Permission... permissions) throws AccessDeniedException {
		checkAuthorization(object, Arrays.asList(permissions));
	}

	public void checkAuthorization(Object object, Collection<Permission> perms) throws AccessDeniedException {
		try {
			if (object == null) {
				throw new AccessDeniedException("Cannot authorize for null objects");
			}
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				throw new AccessDeniedException("Cannot authorize for anonymous user");
			}
			List<Sid> sids = sidRetrievalStrategy.getSids(authentication);
			ObjectIdentity objectIdentity = objectIdentityRetrievalStrategy.getObjectIdentity(object);
			org.springframework.security.acls.model.Acl acl = aclServiceMutable.readAclById(objectIdentity, sids);
			if (!acl.isGranted(new ArrayList<Permission>(perms), sids, false)) {
				throw new AccessDeniedException("Current user is not authorized to access object: " + object);
			}
		} catch (Exception e) {
			throw new AccessDeniedException(e.getMessage(), e);
		}
	}

	public Serializable saveAcl(DtoAcl dto) throws AclException {
		try {
			OptionsList options = new OptionsList();
			DtoConverter<DtoAcl, ObjectIdentity> converter1 = registry.search(dto.getClass(), ObjectIdentity.class);
			ObjectIdentity identity = converter1.toEntity(dto, options);
			DtoConverter<DtoAcl, Sid> converter2 = registry.search(dto.getClass(), Sid.class);
			Sid sid = converter2.toEntity(dto, options);
			DtoConverter<DtoAcl, Permission> converter3 = registry.search(dto.getClass(), Permission.class);
			Permission perm = converter3.toEntity(dto, options);
			MutableAcl acl = null;
			try {
				acl = (MutableAcl) aclServiceMutable.readAclById(identity);
			} catch (NotFoundException nfe) {
				acl = aclServiceMutable.createAcl(identity);
			}
			// Now grant some permissions via an access control entry (ACE)
			acl.insertAce(acl.getEntries().size(), perm, sid, dto.grant());
			aclServiceMutable.updateAcl(acl);
			return acl.getId();
		} catch (Exception e) {
			throw new AclException(e);
		}
	}

	public void deleteAcl(DtoAcl dto, boolean withChildren) throws AclException {
		try {
			OptionsList options = new OptionsList();
			DtoConverter<DtoAcl, ObjectIdentity> converter = registry.search(dto.getClass(), ObjectIdentity.class);
			ObjectIdentity identity = converter.toEntity(dto, options);
			aclServiceMutable.deleteAcl(identity, withChildren);
		} catch (Exception e) {
			throw new AclException(e);
		}
	}

}
