package com.hossein.PermissionTree.controller.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hossein.PermissionTree.controller.viewModel.User.UserViewModel;
import com.hossein.PermissionTree.mapper.User.UserMapper;
import com.hossein.PermissionTree.service.user.IUserService;

@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class AccountController {

	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private UserMapper mapper;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_22')")
	public UserViewModel getLoggedInUser() {
		return this.mapper.mapEToV(this.iUserService.loadLoggedInUser());
	}
	
}
