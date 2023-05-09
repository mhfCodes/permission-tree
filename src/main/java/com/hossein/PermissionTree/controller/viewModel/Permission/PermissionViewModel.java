package com.hossein.PermissionTree.controller.viewModel.Permission;

import java.util.List;

public class PermissionViewModel {
	
	private Long permissionId;
	private String permissionName;
	private String permissionHierarchy;
	private Boolean permissionMenuItem;
	private List<PermissionViewModel> children;
	
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
	public List<PermissionViewModel> getChildren() {
		return children;
	}
	public void setChildren(List<PermissionViewModel> children) {
		this.children = children;
	}

}
