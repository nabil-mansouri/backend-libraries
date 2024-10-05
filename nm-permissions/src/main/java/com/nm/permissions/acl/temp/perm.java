package com.nm.permissions.acl.temp;

/**
 * DELETE IT?
 * 
 * @author Nabil MANSOURI
 *
 */
public class perm {

	// @PreAuthorize("hasPermission(#entity, 'ADMINISTRATION') or
	// hasRole('ROLE_ADMIN')")
	// private static final Logger LOG =
	// LoggerFactory.getLogger(ObjectIdentityListener.class);
	//
	// static private PermissionService permissionService;
	//
	// @Autowired(required = true)
	// @Qualifier("permissionService")
	// public void setSearchService(PermissionService _permissionService)
	// {
	// permissionService = _permissionService;
	// }
	//
	// @PreRemove
	// public void preRemove(Object object) {
	// if(object instanceof Persistable) {
	// LOG.info("Deleting object identity for class {} id {} ",
	// persistable.getClass(), persistable.getId());
	// permissionService.deleteObjectIdentity((Persistable) object);
	// }
	// }
	//
	// @PostConstruct
	// public void init() {
	// Assert.notNull(permissionService, "'permissionService' is required");
	// }

	// public void deleteObjectIdentity(Persistable persistable) {
	// try{
	// MutableAcl acl = (MutableAcl)
	// mutableAclService.readAclById(identity(persistable));
	// mutableAclService.deleteAcl(acl.getObjectIdentity(), true);
	// } catch (NotFoundException e){
	// LOG.info("Could not find ACL for target {}", persistable);
	// }
	// }
	// TODO build hibernate model for acl
	// TODO or DTO implementing interface that build ObjectIdentity=> better
	// (customizable for user library)
	// TODO IMPLEMENT Interface for protected models?
	// TODO Implement interface for protected QueryBuilder? (on getCriteria?)
	// TODO load permissions using grid of permissions => grantauthority
	// TODO utiliser la table acl (et la class du mmodel) pour filtrer les
	// querybuilder? AOP?
}
