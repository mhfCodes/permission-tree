package com.hossein.PermissionTree.service.user;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.User.UserViewModel;
import com.hossein.PermissionTree.dto.user.UserDto;
import com.hossein.PermissionTree.model.user.UserModel;

public interface IUserService {

	long saveEntity(UserModel entity);
	
	UserModel loadLoggedInUser();
	
	List<UserViewModel> getUsersByRoleId(Long roleId);
	
	List<UserModel> getAllUsers();
	
	UserModel load(Long id);
	
	List<UserViewModel> search(UserDto data);
	
	long save(UserModel entity);
	
	long changePassword(UserDto dto);
	
	Boolean delete(Long id);
}
