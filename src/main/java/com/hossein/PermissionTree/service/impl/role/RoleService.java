package com.hossein.PermissionTree.service.impl.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hossein.PermissionTree.controller.viewModel.Permission.PermissionTreeViewModel;
import com.hossein.PermissionTree.controller.viewModel.Permission.PermissionViewModel;
import com.hossein.PermissionTree.controller.viewModel.Role.RoleViewModel;
import com.hossein.PermissionTree.controller.viewModel.User.UserViewModel;
import com.hossein.PermissionTree.dao.repository.role.IRoleRepository;
import com.hossein.PermissionTree.dto.role.RoleDto;
import com.hossein.PermissionTree.exception.ApplicationException;
import com.hossein.PermissionTree.model.permission.Permission;
import com.hossein.PermissionTree.model.role.Role;
import com.hossein.PermissionTree.service.role.IRoleService;
import com.hossein.PermissionTree.service.user.IUserService;

@Service
public class RoleService implements IRoleService {
	
	@Autowired
	private IRoleRepository iRoleRepository;
	
	@Autowired
	private IUserService iUserService;
	
	// for avoiding multiple database calls while making the tree
	// which would be more expensive, we would use a map and store
	// all permissions by their parentId as their key in this map
	private Map<String, List<PermissionTreeViewModel>> permissionMap = new HashMap<>(); 

	@Override
	public List<Role> getAllRoles() {
		return this.iRoleRepository.findAll();
	}

	@Override
	public Role load(Long id) {
		return this.iRoleRepository.findById(id).orElse(null);
	}

	@Override
	public List<RoleViewModel> search(RoleDto data) {
		return this.iRoleRepository.getAll(data);
	}

	@Override
	@Transactional
	public long save(Role entity) {
		return this.iRoleRepository.save(entity).getId();
	}

	@Override
	@Transactional
	public Boolean delete(Long id) {
		List<UserViewModel> users = this.iUserService.getUsersByRoleId(id);
		if (users.size() > 0) 
			throw new ApplicationException("Relation Exists Between Role And User");
		this.iRoleRepository.deleteById(id);
		return this.load(id) == null;
	}
	
	public void fillMap(List<PermissionViewModel> permissions) {
		permissionMap.clear();
		
		permissions.forEach(permission -> {
			String parentId = permission.getPermissionParentId() != null ? String.valueOf(permission.getPermissionParentId()) : "null";
			PermissionTreeViewModel permissionTreeViewModel = this.mapToTreeViewModel(permission);
			
			List<PermissionTreeViewModel> currentPermissions = permissionMap.get(parentId) != null ? 
					permissionMap.get(parentId) : new ArrayList<>();
			currentPermissions.add(permissionTreeViewModel);
			
			if (permissionMap.containsKey(parentId)) {
				permissionMap.replace(parentId, currentPermissions);
			} else {
				permissionMap.put(parentId, currentPermissions);
			}
		});
	}

	@Override
	public List<PermissionTreeViewModel> makePermissionTree(Long roleId) {
		List<PermissionTreeViewModel> tree = new ArrayList<>();
		
		List<PermissionViewModel> permissions = this.iRoleRepository.getAllPermissionsByRoleId(roleId);
		
		fillMap(permissions);
		
		List<PermissionTreeViewModel> rootPermissions = permissionMap.get("null");
		
		rootPermissions.forEach(rootPermission -> {
			
			String rootPermissionId = String.valueOf(rootPermission.getPermissionId());
			List<PermissionTreeViewModel> childPermissions = makeChildNodes(rootPermissionId);
			rootPermission.setPermissionChildren(childPermissions);
			
			tree.add(rootPermission);
		});
		
		return tree;
	}
	
	public List<PermissionTreeViewModel> makeChildNodes(String parentId) {
		List<PermissionTreeViewModel> childPermissions = permissionMap.get(parentId) != null 
				? permissionMap.get(parentId)
				: new ArrayList<>();
		
		if (childPermissions.size() == 0) {
			return childPermissions;
		} else {
			childPermissions.forEach(childPermission -> {
				String childPermissionId = String.valueOf(childPermission.getPermissionId());
				List<PermissionTreeViewModel> childOfChildPermissions = makeChildNodes(childPermissionId);
				childPermission.setPermissionChildren(childOfChildPermissions);
			});
		}
		
		return childPermissions;
	}
	
	public PermissionTreeViewModel mapToTreeViewModel(PermissionViewModel viewModel) {
		PermissionTreeViewModel treeViewModel = new PermissionTreeViewModel();
		
		treeViewModel.setPermissionId(viewModel.getPermissionId());
		treeViewModel.setPermissionName(viewModel.getPermissionName());
		treeViewModel.setPermissionHierarchy(viewModel.getPermissionHierarchy());
		treeViewModel.setPermissionMenuItem(viewModel.getPermissionMenuItem());
		treeViewModel.setPermissionSelected(
				(viewModel.getPermissionSelected() != null && viewModel.getPermissionSelected().equalsIgnoreCase("true")) ? true : false
						);
		
		return treeViewModel;
	}

}
