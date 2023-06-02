package com.hossein.PermissionTree.dao.repository.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.hossein.PermissionTree.controller.viewModel.Role.RoleViewModel;
import com.hossein.PermissionTree.controller.viewModel.User.UserViewModel;
import com.hossein.PermissionTree.dao.config.GenericRepository;
import com.hossein.PermissionTree.dto.user.UserDto;
import com.hossein.PermissionTree.model.user.UserModel;

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
		
		hql.append("select distinct"
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
		
		if (data.getRoleIds() != null && data.getRoleIds().size() > 0) {
			hql.append(" and r.id in (:roleIds)");
			params.put("roleIds", data.getRoleIds());
		}
		
		hql.append(" order by e.id");
		
		return super.getAll(hql.toString(), params, UserViewModel.class);
	}

	@Override
	public List<RoleViewModel> getAllRolesByUserId(Long userId) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		
		hql.append("select"
				+ " r.id as roleId, r.name as roleName");
		
		if (userId != null && userId > 0) {
			hql.append(", max((case when e.id = :userId then 'true' else 'false' end)) as roleSelected");
			params.put("userId", userId);
		}
		
		hql.append(" from UserModel e"
				+ " right join e.roles r"
				+ " group by r.id, r.name"
				+ " order by r.id");
		
		return super.getAll(hql.toString(), params, RoleViewModel.class);
	}

	@Override
	public UserModel getUserByUsername(String username, Long userId) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		
		hql.append("select"
				+ " e"
				+ " from UserModel e"
				+ " where 1=1");
		
		if (StringUtils.hasText(username)) {
			hql.append(" and e.username like :username");
			params.put("username", username);
		}
		
		if (userId != null && userId > 0) {
			hql.append(" and e.id <> :userId");
			params.put("userId", userId);
		}
		
		return super.find(hql.toString(), params);
	}

}
