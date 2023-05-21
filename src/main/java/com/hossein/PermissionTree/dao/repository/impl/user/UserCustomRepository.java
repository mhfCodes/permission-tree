package com.hossein.PermissionTree.dao.repository.impl.user;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.User.UserViewModel;
import com.hossein.PermissionTree.dto.user.UserDto;

public interface UserCustomRepository {
	
	List<UserViewModel> findUsersByRoleId(Long roleId);
	
	List<UserViewModel> getAll(UserDto data);
}
