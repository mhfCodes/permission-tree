package com.hossein.PermissionTree.service.permission;

import java.util.List;

import com.hossein.PermissionTree.controller.viewModel.Permission.PermissionViewModel;

public interface IPermissionService {

	List<PermissionViewModel> makePermissionTree();
	
}
