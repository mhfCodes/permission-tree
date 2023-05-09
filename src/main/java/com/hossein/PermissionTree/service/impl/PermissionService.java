package com.hossein.PermissionTree.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hossein.PermissionTree.controller.viewModel.Permission.PermissionViewModel;
import com.hossein.PermissionTree.dao.repository.permission.IPermissionRepository;
import com.hossein.PermissionTree.model.permission.Permission;
import com.hossein.PermissionTree.service.permission.IPermissionService;

@Service
public class PermissionService implements IPermissionService {
	
	@Autowired
	private IPermissionRepository iPermissionRepository;

	// for avoiding multiple database calls while making the tree
	// which would be more expensive, we would use a map and store
	// all permissions by their parentId as their key in this map
	private Map<String, List<PermissionViewModel>> permissionMap = new HashMap<>(); 
	
	public void fillMap(List<Permission> permissions) {
		permissionMap.clear();
		
		permissions.forEach(permission -> {
			String parentId = permission.getParent() != null ? String.valueOf(permission.getParent().getId()) : "null";
			PermissionViewModel permissionViewModel = this.mapEntityToViewModel(permission);
			
			List<PermissionViewModel> currentPermissions = permissionMap.get(parentId) != null ? 
					permissionMap.get(parentId) : new ArrayList<>();
			currentPermissions.add(permissionViewModel);
			
			if (permissionMap.containsKey(parentId)) {
				permissionMap.replace(parentId, currentPermissions);
			} else {
				permissionMap.put(parentId, currentPermissions);
			}
		});
	}
	
	@Override
	public List<PermissionViewModel> makePermissionTree() {
		List<PermissionViewModel> permissionViewModels = new ArrayList<>();
		
		List<Permission> permissions = this.iPermissionRepository.findAll();
		
		fillMap(permissions);
		
		List<PermissionViewModel> rootPermissions = permissionMap.get("null");
		
		rootPermissions.forEach(rootPermission -> {
			
			String rootPermissionId = String.valueOf(rootPermission.getPermissionId());
			List<PermissionViewModel> childPermissions = makeChildNodes(rootPermissionId);
			rootPermission.setChildren(childPermissions);
			
			permissionViewModels.add(rootPermission);
		});
		
		return permissionViewModels;
	}
	
	
	public List<PermissionViewModel> makeChildNodes(String parentId) {
		List<PermissionViewModel> childPermissions = permissionMap.get(parentId) != null 
				? permissionMap.get(parentId)
				: new ArrayList<>();
		
		if (childPermissions.size() == 0) {
			return childPermissions;
		} else {
			childPermissions.forEach(childPermission -> {
				String childPermissionId = String.valueOf(childPermission.getPermissionId());
				List<PermissionViewModel> childOfChildPermissions = makeChildNodes(childPermissionId);
				childPermission.setChildren(childOfChildPermissions);
			});
		}
		
		return childPermissions;
	}
	
	public PermissionViewModel mapEntityToViewModel(Permission entity) {
		PermissionViewModel viewModel = new PermissionViewModel();
		
		viewModel.setPermissionId(entity.getId());
		viewModel.setPermissionName(entity.getName());
		viewModel.setPermissionHierarchy(entity.getHierarchy());
		viewModel.setPermissionMenuItem(entity.getMenuItem());
		
		return viewModel;
	}
	

}
