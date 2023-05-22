package com.hossein.PermissionTree.controller.userManagement;

import java.util.List;

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
@RequestMapping("/api/user")
@CrossOrigin
public class UserManagementController {

	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private UserMapper mapper;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_10')")
	public List<UserViewModel> getAll() {
		return this.mapper.mapEToVList(this.iUserService.getAllUsers());
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_10')")
	public UserViewModel load(@PathVariable Long id) {
		return this.mapper.mapEToV(this.iUserService.load(id));
	}
	
	@PostMapping("/search")
	@PreAuthorize("hasRole('ROLE_10')")
	public List<UserViewModel> search(@RequestBody UserDto data) {
		return this.iUserService.search(data);
	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_9', 'ROLE_11')")
	public long save(@RequestBody UserDto dto) {
		return this.iUserService.save(this.mapper.mapDToE(dto));
	}
	
	@PostMapping("/changePassword")
	@PreAuthorize("hasRole('ROLE_11')")
	public long changePassword(@RequestBody UserDto dto) {
		return this.iUserService.changePassword(dto);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_12')")
	public Boolean delete(@PathVariable Long id) {
		return this.iUserService.delete(id);
	}
	
}
