package com.hossein.PermissionTree.dao.repository.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hossein.PermissionTree.controller.viewModel.User.UserViewModel;
import com.hossein.PermissionTree.dao.config.GenericRepository;

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

}
