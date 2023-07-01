package com.hossein.PermissionTree.dto.user;

import java.util.List;
import java.util.Set;

import com.hossein.PermissionTree.dto.role.RoleDto;

public class UserDto {
	
	private Long userId;
	private String username;
	private String password;
	private String userFirstName;
	private String userLastName;
	private Set<RoleDto> roles;
	private List<Long> roleIds;
	private Long userBalance;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public Set<RoleDto> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}
	public List<Long> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}
	public Long getUserBalance() {
		return userBalance;
	}
	public void setUserBalance(Long userBalance) {
		this.userBalance = userBalance;
	}
	
}
