package com.hossein.PermissionTree.dao.repository.impl.user;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.User.UserViewModel;

public interface UserCustomRepository {
	
	List<UserViewModel> findUsersByRoleId(Long roleId);
}
