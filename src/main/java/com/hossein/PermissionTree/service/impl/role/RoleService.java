package com.hossein.PermissionTree.service.impl.role;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hossein.PermissionTree.controller.viewModel.RoleViewModel;
import com.hossein.PermissionTree.dao.repository.role.IRoleRepository;
import com.hossein.PermissionTree.dto.role.RoleDto;
import com.hossein.PermissionTree.exception.ApplicationException;
import com.hossein.PermissionTree.model.role.Role;
import com.hossein.PermissionTree.service.role.IRoleService;

@Service
public class RoleService implements IRoleService {
	
	@Autowired
	private IRoleRepository iRoleRepository;

	@Override
	public List<Role> getAllRoles() {
		return this.iRoleRepository.findAll();
	}

	@Override
	public Role load(Long id) {
		return this.iRoleRepository.findById(id).orElse(null);
	}

	@Override
	public List<RoleViewModel> search(RoleDto data) {
		return this.iRoleRepository.getAll(data);
	}

	@Override
	@Transactional
	public long save(Role entity) {
		return this.iRoleRepository.save(entity).getId();
	}

	@Override
	@Transactional
	public Boolean delete(Long id) {
		this.iRoleRepository.deleteById(id);
		return this.load(id) == null;
	}

}