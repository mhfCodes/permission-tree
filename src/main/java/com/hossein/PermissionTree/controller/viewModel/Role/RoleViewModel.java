package com.hossein.PermissionTree.controller.viewModel.Role;

import java.util.Set;

import com.hossein.PermissionTree.controller.viewModel.Permission.PermissionViewModel;

public class RoleViewModel {
	
	private Long roleId;
	private String roleName;
	private String roleSelected;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleSelected() {
		return roleSelected;
	}
	public void setRoleSelected(String roleSelected) {
		this.roleSelected = roleSelected;
	}

}
