package com.hossein.PermissionTree.service.impl.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hossein.PermissionTree.controller.viewModel.Role.RoleViewModel;
import com.hossein.PermissionTree.controller.viewModel.User.UserViewModel;
import com.hossein.PermissionTree.dao.repository.role.IRoleRepository;
import com.hossein.PermissionTree.dao.repository.user.IUserRepository;
import com.hossein.PermissionTree.dto.user.UserDto;
import com.hossein.PermissionTree.exception.ApplicationException;
import com.hossein.PermissionTree.mapper.Role.RoleMapper;
import com.hossein.PermissionTree.model.role.Role;
import com.hossein.PermissionTree.model.user.UserModel;
import com.hossein.PermissionTree.service.user.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserRepository iUserRepository;
	
	@Autowired
	private IRoleRepository iRoleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	@Transactional
	public long saveEntity(UserModel entity) {
		Optional<UserModel> userModel = this.iUserRepository.findByUsername(entity.getUsername());
		if (userModel.isPresent()) throw new ApplicationException("Username Exists");
		
		Set<Role> userRoles = new HashSet<>();
		Role role = this.iRoleRepository.findById(3L).orElse(null);
		userRoles.add(role);
		entity.setRoles(userRoles);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		return this.iUserRepository.saveAndFlush(entity).getId();
	}

	@Override
	public UserModel loadLoggedInUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserModel loggedInUser = this.iUserRepository.findByUsername(user.getUsername()).orElse(null);
		return loggedInUser;
	}

	@Override
	public List<UserViewModel> getUsersByRoleId(Long roleId) {
		return this.iUserRepository.findUsersByRoleId(roleId);
	}

	@Override
	public List<UserModel> getAllUsers() {
		return this.iUserRepository.findAll();
	}

	@Override
	public UserModel load(Long id) {
		return this.iUserRepository.findById(id)
				.orElseThrow(() -> new ApplicationException("User Not Found"));
	}

	@Override
	public List<UserViewModel> search(UserDto data) {
		
		List<Long> roleIds = new ArrayList<>();
		if (data.getRoles() != null && data.getRoles().size() > 0) {
			
			data.getRoles().forEach(role -> {
				roleIds.add(role.getId());
			});
		}
		data.setRoleIds(roleIds);
		
		List<UserViewModel> result = this.iUserRepository.getAll(data);
		return result;
	}

	@Override
	@Transactional
	public long save(UserModel entity) {
		// check for duplicate username before saving or updating
		UserModel user = this.iUserRepository.getUserByUsername(entity.getUsername(), entity.getId());
		if (user != null) return 0;
		
		if (entity.getId() == null) {
			// saving a new user
			entity.setPassword(encoder.encode(entity.getPassword()));
			return this.iUserRepository.save(entity).getId();
		}
		
		// updating a user
		UserModel existingUser = this.iUserRepository.findById(entity.getId())
								.orElseThrow(() -> new ApplicationException("User Does Not Exist"));
		existingUser.setUsername(entity.getUsername());
		existingUser.setFirstName(entity.getFirstName());
		existingUser.setLastName(entity.getLastName());
		existingUser.setRoles(entity.getRoles());
		return this.iUserRepository.save(existingUser).getId();
	}
	
	@Override
	@Transactional
	public Boolean delete(Long id) {
		this.iUserRepository.deleteById(id);
		return this.iUserRepository.findById(id).orElse(null) == null;
	}

	@Override
	@Transactional
	public long changePassword(UserDto dto) {
		UserModel user = this.iUserRepository.findById(dto.getUserId())
				.orElseThrow(() -> new ApplicationException("User Not Found"));
		user.setPassword(encoder.encode(dto.getPassword()));
		UserModel updatedUser = this.iUserRepository.save(user);
		return updatedUser.getId();
	}
	
	@Override
	public List<RoleViewModel> getUserRoles(Long userId) {
		if (userId == 0) return this.roleMapper.mapEToVList(this.iRoleRepository.findAll());
		
		return this.iUserRepository.getAllRolesByUserId(userId);
	}

}
