package com.hossein.PermissionTree.dto.infrastructure;

public class AuthRes {
	
	private final String jwtToken;
	
	public AuthRes(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getJwtToken() {
		return jwtToken;
	}

}
