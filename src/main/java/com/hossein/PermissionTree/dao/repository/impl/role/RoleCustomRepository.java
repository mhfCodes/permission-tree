package com.hossein.PermissionTree.dao.repository.impl.role;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.Permission.PermissionViewModel;
import com.hossein.PermissionTree.controller.viewModel.Role.RoleViewModel;
import com.hossein.PermissionTree.dto.role.RoleDto;

public interface RoleCustomRepository {
	
	List<RoleViewModel> getAll(RoleDto data);
	
	List<PermissionViewModel> getAllPermissionsByRoleId(Long roleId);
}
