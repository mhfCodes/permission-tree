package com.hossein.PermissionTree.dao.repository.impl.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.hossein.PermissionTree.controller.viewModel.RoleViewModel;
import com.hossein.PermissionTree.dao.config.GenericRepository;
import com.hossein.PermissionTree.dto.role.RoleDto;

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

}
