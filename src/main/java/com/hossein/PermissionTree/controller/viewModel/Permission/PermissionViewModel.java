package com.hossein.PermissionTree.controller.viewModel.Permission;

public class PermissionViewModel {
	
	private Long permissionId;
	private String permissionName;
	private String permissionHierarchy;
	private Boolean permissionMenuItem;
	private Long permissionParentId;
	
	
	public Long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public String getPermissionHierarchy() {
		return permissionHierarchy;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public Boolean getPermissionMenuItem() {
		return permissionMenuItem;
	}
	public void setPermissionMenuItem(Boolean permissionMenuItem) {
		this.permissionMenuItem = permissionMenuItem;
	}
	public void setPermissionHierarchy(String permissionHierarchy) {
		this.permissionHierarchy = permissionHierarchy;
	}
	public Long getPermissionParentId() {
		return permissionParentId;
	}
	public void setPermissionParentId(Long permissionParentId) {
		this.permissionParentId = permissionParentId;
	}
}
