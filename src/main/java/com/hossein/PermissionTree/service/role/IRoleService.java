package com.hossein.PermissionTree.service.role;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.RoleViewModel;
import com.hossein.PermissionTree.dto.role.RoleDto;
import com.hossein.PermissionTree.model.role.Role;

public interface IRoleService {

	List<Role> getAllRoles();
	
	Role load(Long id);
	
	List<RoleViewModel> search(RoleDto data);
	
	long save(Role entity);
	
	Boolean delete(Long id);
}
