package com.hossein.PermissionTree.dao.repository.impl.role;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.RoleViewModel;
import com.hossein.PermissionTree.dto.role.RoleDto;

public interface RoleCustomRepository {
	
	List<RoleViewModel> getAll(RoleDto data);
}
