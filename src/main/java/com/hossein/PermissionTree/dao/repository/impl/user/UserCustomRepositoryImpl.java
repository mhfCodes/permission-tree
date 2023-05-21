package com.hossein.PermissionTree.dao.repository.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.hossein.PermissionTree.controller.viewModel.User.UserViewModel;
import com.hossein.PermissionTree.dao.config.GenericRepository;
import com.hossein.PermissionTree.dto.user.UserDto;

@Repository
public class UserCustomRepositoryImpl extends GenericRepository implements UserCustomRepository {

	@Override
	public List<UserViewModel> findUsersByRoleId(Long roleId) {
		
		Map<String, Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		
		hql.append("select"
				+ " u.id as userId"
				+ " from UserModel u"
				+ " join u.roles r"
				+ " where 1=1");
		
		if (roleId != null && roleId > -1) {
			hql.append(" and r.id = :roleId");
			params.put("roleId", roleId);
		}
		
		return super.getAll(hql.toString(), params, UserViewModel.class);
	}

	@Override
	public List<UserViewModel> getAll(UserDto data) {
		
		Map<String, Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		
		hql.append("select"
				+ " e.id as userId, e.username as username,"
				+ " e.firstName as userFirstName, e.lastName as userLastName"
				+ " from UserModel e"
				+ " join e.roles r"
				+ " where 1=1");
		
		if (StringUtils.hasText(data.getUsername())) {
			hql.append(" and e.username like :username");
			params.put("username", "%"+data.getUsername()+"%");
		}
		
		if (StringUtils.hasText(data.getUserFirstName())) {
			hql.append(" and e.firstName like :userFirstName");
			params.put("userFirstName", "%"+data.getUserFirstName()+"%");
		}
		
		if (StringUtils.hasText(data.getUserLastName())) {
			hql.append(" and e.lastName like :userLastName");
			params.put("userLastName", "%"+data.getUserLastName()+"%");
		}
		
		if (StringUtils.hasText(data.getRoleIds())) {
			hql.append(" and r.id in (:roleIds)");
			params.put("roleIds", data.getRoleIds());
		}
		
		return super.getAll(hql.toString(), params, UserViewModel.class);
	}

}
