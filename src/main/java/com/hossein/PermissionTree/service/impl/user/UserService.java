package com.hossein.PermissionTree.service.impl.user;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hossein.PermissionTree.dao.repository.role.IRoleRepository;
import com.hossein.PermissionTree.dao.repository.user.IUserRepository;
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
	
	@Override
	@Transactional
	public long saveEntity(UserModel entity) {
		Set<Role> userRoles = new HashSet<>();
		Role role = this.iRoleRepository.findById(3L).orElse(null);
		userRoles.add(role);
		entity.setRoles(userRoles);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		return this.iUserRepository.saveAndFlush(entity).getId();
	}

}
