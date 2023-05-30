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

import com.hossein.PermissionTree.controller.viewModel.User.UserViewModel;
import com.hossein.PermissionTree.dao.repository.role.IRoleRepository;
import com.hossein.PermissionTree.dao.repository.user.IUserRepository;
import com.hossein.PermissionTree.dto.user.UserDto;
import com.hossein.PermissionTree.exception.ApplicationException;
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

		if (data.getRoleIds() != null) {
			List<String> roleIdsList = new ArrayList<>();
			data.getRoles().forEach(role -> {
				roleIdsList.add(String.valueOf(role.getId()));
			});
			
			String roleIds = String.join(",", roleIdsList);
			data.setRoleIds(roleIds);
		}
		
		return this.iUserRepository.getAll(data);
	}

	@Override
//	@Transactional
	public long save(UserModel entity) {
		
		//TODO bug report: fail entity saving first time
		// don't close the modal and send again with new info
		// it will throw oracle exception unique constraint
		if (entity.getId() == null) {
			// saving a new user
			// check for duplicate username
			UserModel user = this.iUserRepository.findByUsername(entity.getUsername())
					.orElse(null);
			if (user != null) return 0;
		}
		
		entity.setPassword(encoder.encode(entity.getPassword()));
		
		return this.iUserRepository.save(entity).getId();
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

}
