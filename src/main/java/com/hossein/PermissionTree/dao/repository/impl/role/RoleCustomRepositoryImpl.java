package com.hossein.PermissionTree.dao.repository.impl.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.hossein.PermissionTree.controller.viewModel.Permission.PermissionViewModel;
import com.hossein.PermissionTree.controller.viewModel.Role.RoleViewModel;
import com.hossein.PermissionTree.dao.config.GenericRepository;
import com.hossein.PermissionTree.dto.role.RoleDto;
import com.hossein.PermissionTree.model.permission.Permission;

@Repository
public class RoleCustomRepositoryImpl extends GenericRepository implements RoleCustomRepository {

	@Override
	public List<RoleViewModel> getAll(RoleDto data) {
		Map<String, Object> params =  new HashMap<>();
		StringBuilder hql = new StringBuilder();
	
		hql.append("select"
				+ " e.id as roleId, e.name as roleName"
				+ " from Role e where 1=1");
		
		if (data.getId() != null && data.getId() > 0) {
			hql.append(" and e.id = :roleId");
			params.put("roleId", data.getId());
		}
		
		if (StringUtils.hasText(data.getName())) {
			hql.append(" and e.name like :roleName");
			params.put("roleName", "%"+data.getName()+"%");
		}
		
		return super.getAll(hql.toString(), params, RoleViewModel.class);
	}

	@Override
	public List<PermissionViewModel> getAllPermissionsByRoleId(Long roleId) {
		
		Map<String, Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		
		hql.append("select"
				+ " p.id as permissionId,"
				+ " p.name as permissionName,"
				+ " p.hierarchy as permissionHierarchy,"
				+ " p.menuItem as permissionMenuItem,"
				+ " p.parent.id as permissionParentId");
		
		if (roleId != null && roleId > -1) {
			hql.append(", max((case when e.id = :roleId then 'true' else 'false' end)) as permissionSelected");
			params.put("roleId", roleId);
		}
		
		hql.append(" from Role e"
				+ " right join e.permissions p"
				+ " group by p.id, p.name, p.hierarchy, p.menuItem, p.parent.id"
				+ " order by p.id");
		
		return super.getAll(hql.toString(), params, PermissionViewModel.class);
		
	}

}
