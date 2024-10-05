package com.nm.permissions.converters;

import java.util.Collection;

import com.nm.auths.models.User;
import com.nm.auths.models.UserGroup;
import com.nm.permissions.daos.SubjectQueryBuilder;
import com.nm.permissions.dtos.DtoSubjectDefault;
import com.nm.permissions.models.Subject;
import com.nm.permissions.models.SubjectGroup;
import com.nm.permissions.models.SubjectUser;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author nabilmansouri
 *
 */
public class SubjectConverter extends DtoConverterDefault<DtoSubjectDefault, Subject> {

	@Override
	public Collection<Class<? extends Subject>> managedEntity() {
		return ListUtils.all(Subject.class, SubjectUser.class, SubjectGroup.class);
	}

	public DtoSubjectDefault toDto(DtoSubjectDefault dto, Subject entity, OptionsList options)
			throws DtoConvertException {
		dto.setId(entity.getId());
		if (entity instanceof SubjectGroup) {
			UserGroup group = ((SubjectGroup) entity).getGroup();
			dto.setIdGroup(group.getId());
			dto.setTypeGroup(true);
			dto.setName(group.getName());
		} else if (entity instanceof SubjectUser) {
			User user = ((SubjectUser) entity).getUser();
			dto.setIdUser(user.getId());
			dto.setName(user.getName());
		} else {
			throw new DtoConvertException("Could not identify type of :" + entity);
		}
		return dto;
	}

	@Override
	public Subject toEntity(DtoSubjectDefault dto, OptionsList options) throws DtoConvertException {
		try {
			IGenericDao<Subject, Long> dao = AbstractGenericDao.get(Subject.class);
			if (dto.getId() == null) {
				if (dto.getIdGroup() != null) {
					IGenericDao<UserGroup, Long> daoG = AbstractGenericDao.get(UserGroup.class);
					Collection<Subject> exist = dao
							.find(SubjectQueryBuilder.getSubjectGroups().withGroup(dto.getIdGroup()));
					if (exist.isEmpty()) {
						SubjectGroup entity = new SubjectGroup();
						entity.setGroup(daoG.load(dto.getIdGroup()));
						dao.save(entity);
						return entity;
					} else {
						return exist.iterator().next();
					}
				} else if (dto.getIdUser() != null) {
					IGenericDao<User, Long> daoG = AbstractGenericDao.get(User.class);
					Collection<Subject> exist = dao
							.find(SubjectQueryBuilder.getSubjectUser().withUser(dto.getIdUser()));
					if (exist.isEmpty()) {
						SubjectUser entity = new SubjectUser();
						entity.setUser(daoG.load(dto.getIdUser()));
						dao.save(entity);
						return entity;
					} else {
						return exist.iterator().next();
					}
				} else {
					throw new DtoConvertException("Could not identify entity for  :" + dto);
				}
			} else {
				// NO UPDATE
				return dao.get(dto.getId());
			}
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
