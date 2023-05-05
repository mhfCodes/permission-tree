package com.hossein.PermissionTree.service.user;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.User.UserViewModel;
import com.hossein.PermissionTree.model.user.UserModel;

public interface IUserService {

	long saveEntity(UserModel entity);
	
	UserModel loadLoggedInUser();
	
	List<UserViewModel> getUsersByRoleId(Long roleId);
}
