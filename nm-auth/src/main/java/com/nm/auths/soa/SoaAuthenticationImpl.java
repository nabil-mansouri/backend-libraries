package com.nm.auths.soa;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nm.auths.AuthenticationException;
import com.nm.auths.constants.AuthenticationOptions;
import com.nm.auths.daos.QueryBuilderUser;
import com.nm.auths.daos.QueryBuilderUserGroup;
import com.nm.auths.dtos.DtoAuthentication;
import com.nm.auths.dtos.DtoGroup;
import com.nm.auths.dtos.DtoUser;
import com.nm.auths.dtos.DtoUserDefault;
import com.nm.auths.models.Authentication;
import com.nm.auths.models.User;
import com.nm.auths.models.UserGroup;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class SoaAuthenticationImpl implements SoaAuthentication {
	private AuthenticationManager manager;
	private DtoConverterRegistry registry;

	public void setManager(AuthenticationManager manager) {
		this.manager = manager;
	}

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	@Override
	public Collection<DtoUser> fetch(QueryBuilderUser q, final OptionsList options) throws AuthenticationException {
		try {
			IGenericDao<User, Long> dao = AbstractGenericDao.get(User.class);
			Class<DtoUser> clazz = options.dtoForModel(User.class, DtoUserDefault.class);
			DtoConverter<DtoUser, User> converter = registry.search(clazz, User.class);
			return dao.find(q).stream().map(u -> {
				try {
					return converter.toDto(u, options);
				} catch (Exception e) {
					throw new IllegalArgumentException(e);
				}
			}).collect(Collectors.toList());
		} catch (Exception e) {
			throw new AuthenticationException(e);
		}
	}

	@Override
	public Collection<DtoGroup> fetch(QueryBuilderUserGroup q, OptionsList options) throws AuthenticationException {
		try {
			IGenericDao<UserGroup, Long> dao = AbstractGenericDao.get(UserGroup.class);
			Class<DtoGroup> clazz = options.dtoForModel(UserGroup.class, DtoGroup.class);
			DtoConverter<DtoGroup, UserGroup> converter = registry.search(clazz, UserGroup.class);
			return dao.find(q).stream().map(u -> {
				try {
					return converter.toDto(u, options);
				} catch (Exception e) {
					throw new IllegalArgumentException(e);
				}
			}).collect(Collectors.toList());
		} catch (Exception e) {
			throw new AuthenticationException(e);
		}
	}

	@Override
	public UserGroup saveOrUpdate(DtoGroup dto, OptionsList options) throws AuthenticationException {
		try {
			IGenericDao<UserGroup, Long> dao = AbstractGenericDao.get(UserGroup.class);
			UserGroup group = new UserGroup();
			if (dto.getId() != null) {
				group = dao.findFirst(QueryBuilderUserGroup.get().withId(dto.getId()));
			} else if (dto.getCode() != null) {
				group = dao.findFirstOrDefault(QueryBuilderUserGroup.get().withCode(dto.getCode()), group);
			}
			group.setCode(dto.getCode());
			group.setName(dto.getName());
			dao.saveOrUpdate(group);
			return group;
		} catch (Exception e) {
			throw new AuthenticationException(e);
		}
	}

	@Override
	public void pushToGroup(DtoGroup dto, DtoUser dtoA) throws AuthenticationException {
		try {
			IGenericDao<UserGroup, Long> dao = AbstractGenericDao.get(UserGroup.class);
			UserGroup group = new UserGroup();
			if (dto.getId() != null) {
				group = dao.findFirst(QueryBuilderUserGroup.get().withId(dto.getId()));
			} else {
				group = dao.findFirst(QueryBuilderUserGroup.get().withCode(dto.getCode()));
			}
			group.add(dao.getHibernateTemplate().load(User.class, dtoA.getUserId()));
		} catch (Exception e) {
			throw new AuthenticationException(e);
		}
	}

	public void authenticate(DtoAuthentication dto, OptionsList options) throws AuthenticationException {
		try {
			org.springframework.security.core.Authentication auth = registry
					.search(dto, org.springframework.security.core.Authentication.class).toEntity(dto, options);
			auth = manager.authenticate(auth);
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (Exception e) {
			throw new AuthenticationException(e);
		}
	}

	public Authentication saveOrUpdate(DtoAuthentication dto, OptionsList options) throws AuthenticationException {
		try {
			IGenericDao<User, Long> daoUser = AbstractGenericDao.get(User.class);
			IGenericDao<Authentication, Long> dao = AbstractGenericDao.get(Authentication.class);
			Authentication auth = registry.search(dto, Authentication.class).toEntity(dto, options);
			if (dto.getUserId() == null) {
				User user = new User();
				user.setName(dto.getFullName());
				daoUser.save(user);
				auth.setUser(user);
			} else {
				auth.setUser(daoUser.load(dto.getUserId()));
			}
			dao.saveOrUpdate(auth);
			dto.setId(auth.getId());
			dto.setUserId(auth.getUser().getId());
			if (options.contains(AuthenticationOptions.Authenticate)) {
				authenticate(dto, options);
			}
			return auth;
		} catch (Exception e) {
			throw new AuthenticationException(e);
		}
	}

}
