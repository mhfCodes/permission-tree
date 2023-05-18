package com.hossein.PermissionTree.dto.role;

public class RolePermissionDto {
	
	private Long roleId;
	private String rolePermissionIds;

	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRolePermissionIds() {
		return rolePermissionIds;
	}
	public void setRolePermissionIds(String rolePermissionIds) {
		this.rolePermissionIds = rolePermissionIds;
	}

}
