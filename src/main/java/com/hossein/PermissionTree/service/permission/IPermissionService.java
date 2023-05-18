package com.hossein.PermissionTree.service.permission;

import com.hossein.PermissionTree.model.permission.Permission;

public interface IPermissionService {
	
	Permission load(Long permissionId);
}
