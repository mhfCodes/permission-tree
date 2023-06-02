package com.hossein.PermissionTree.dao.repository.impl.user;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.Role.RoleViewModel;
import com.hossein.PermissionTree.controller.viewModel.User.UserViewModel;
import com.hossein.PermissionTree.dto.user.UserDto;
import com.hossein.PermissionTree.model.user.UserModel;

public interface UserCustomRepository {
	
	List<UserViewModel> findUsersByRoleId(Long roleId);
	
	List<UserViewModel> getAll(UserDto data);
	
	List<RoleViewModel> getAllRolesByUserId(Long userId);
	
	UserModel getUserByUsername(String username, Long userId);
}
