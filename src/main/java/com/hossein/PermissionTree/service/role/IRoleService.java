package com.hossein.PermissionTree.service.role;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.Permission.PermissionTreeViewModel;
import com.hossein.PermissionTree.controller.viewModel.Role.RoleViewModel;
import com.hossein.PermissionTree.dto.role.RoleDto;
import com.hossein.PermissionTree.dto.role.RolePermissionDto;
import com.hossein.PermissionTree.model.role.Role;

public interface IRoleService {

	List<Role> getAllRoles();
	
	Role load(Long id);
	
	List<RoleViewModel> search(RoleDto data);
	
	long save(Role entity);
	
	Boolean delete(Long id);
	
	List<PermissionTreeViewModel> makePermissionTree(Long roleId);
	
	long updateRolePermissions(RolePermissionDto dto);
}
