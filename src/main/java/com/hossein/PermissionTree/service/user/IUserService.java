package com.hossein.PermissionTree.service.user;

import com.hossein.PermissionTree.model.user.UserModel;

public interface IUserService {

	long saveEntity(UserModel entity);
	
	UserModel loadLoggedInUser();
}
