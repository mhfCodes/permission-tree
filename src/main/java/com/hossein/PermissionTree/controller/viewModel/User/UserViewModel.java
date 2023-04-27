package com.hossein.PermissionTree.controller.viewModel.User;

import java.util.Set;

public class UserViewModel {

	private Long userId;
	private String username;
	private String userFirstName;
	private String userLastName;
	private Set<String> permissionIds;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public Set<String> getPermissionIds() {
		return permissionIds;
	}
	public void setPermissionIds(Set<String> permissionIds) {
		this.permissionIds = permissionIds;
	}
	
}
