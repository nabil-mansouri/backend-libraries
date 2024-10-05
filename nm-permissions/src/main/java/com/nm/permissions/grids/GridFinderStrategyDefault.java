package com.nm.permissions.grids;

import java.util.Collection;

import com.google.inject.internal.Sets;
import com.nm.auths.models.User;
import com.nm.auths.models.UserGroup;
import com.nm.permissions.daos.DaoPermission;
import com.nm.permissions.daos.PermissionGridQueryBuilder;
import com.nm.permissions.daos.SubjectQueryBuilder;
import com.nm.permissions.models.PermissionGrid;
import com.nm.permissions.models.Subject;
import com.nm.permissions.models.SubjectGroup;
import com.nm.permissions.models.SubjectUser;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class GridFinderStrategyDefault implements GridFinderStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Collection<PermissionGrid> finder(Subject subject) throws NoDataFoundException {
		DaoPermission dao = ApplicationUtils.getBean(DaoPermission.class);
		Collection<PermissionGrid> founded = Sets.newHashSet();
		founded.add(subject.getGrid());
		if (subject instanceof SubjectUser) {
			User user = ((SubjectUser) subject).getUser();
			SubjectQueryBuilder query2 = SubjectQueryBuilder.getSubjectGroups().withGroups(user.getGroups());
			PermissionGridQueryBuilder query = PermissionGridQueryBuilder.get().withSubject(query2);
			founded.addAll(dao.find(query));
		} else if (subject instanceof SubjectGroup) {
			UserGroup group = ((SubjectGroup) subject).getGroup();
			SubjectQueryBuilder query2 = SubjectQueryBuilder.getSubjectGroups().withGroup(group);
			PermissionGridQueryBuilder query = PermissionGridQueryBuilder.get().withSubject(query2);
			founded.addAll(dao.find(query));
		} else {
			throw new NoDataFoundException("Could not identify the type of subject:" + subject);
		}
		return founded;
	}

}
