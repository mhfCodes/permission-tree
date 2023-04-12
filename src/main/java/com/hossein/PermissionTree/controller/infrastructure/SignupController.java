package com.hossein.PermissionTree.controller.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hossein.PermissionTree.dto.user.UserDto;
import com.hossein.PermissionTree.mapper.User.UserMapper;
import com.hossein.PermissionTree.service.user.IUserService;

@RestController
@RequestMapping("/signup")
public class SignupController {

	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private UserMapper mapper;
	
	@PostMapping
	public ResponseEntity<?> signup(@RequestBody UserDto dto) {
		Long userId = this.iUserService.saveEntity(this.mapper.mapDToE(dto));
		return ResponseEntity.ok(userId);
	}
	
}
