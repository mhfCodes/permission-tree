package com.hossein.PermissionTree.controller.viewModel.Permission;

import java.util.List;

public class PermissionTreeViewModel {

	private Long permissionId;
	private String permissionName;
	private String permissionHierarchy;
	private Boolean permissionMenuItem;
	private Boolean permissionSelected;
	private List<PermissionTreeViewModel> permissionChildren;
	
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
	public Boolean getPermissionSelected() {
		return permissionSelected;
	}
	public void setPermissionSelected(Boolean permissionSelected) {
		this.permissionSelected = permissionSelected;
	}
	public List<PermissionTreeViewModel> getPermissionChildren() {
		return permissionChildren;
	}
	public void setPermissionChildren(List<PermissionTreeViewModel> permissionChildren) {
		this.permissionChildren = permissionChildren;
	}
}
