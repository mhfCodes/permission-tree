package com.hossein.PermissionTree.controller.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hossein.PermissionTree.controller.viewModel.User.UserViewModel;
import com.hossein.PermissionTree.dto.user.UserDto;
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
	
	@PostMapping("/checkOldPassword")
	@PreAuthorize("hasRole('ROLE_23')")
	public Boolean checkOldPassword(@RequestBody UserDto dto) {
		return this.iUserService.checkOldPassword(dto);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_23')")
	public long updateAccount(@RequestBody UserDto dto) {
		return this.iUserService.updateAccount(this.mapper.mapDToE(dto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_24')")
	public Boolean deleteAccount(@PathVariable Long id) {
		return this.iUserService.delete(id);
	}
	
}
