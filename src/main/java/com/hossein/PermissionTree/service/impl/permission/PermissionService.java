package com.hossein.PermissionTree.service.impl.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hossein.PermissionTree.dao.repository.permission.IPermissionRepository;
import com.hossein.PermissionTree.exception.ApplicationException;
import com.hossein.PermissionTree.model.permission.Permission;
import com.hossein.PermissionTree.service.permission.IPermissionService;

@Service
public class PermissionService implements IPermissionService {
	
	@Autowired
	private IPermissionRepository iPermissionRepository;

	@Override
	public Permission load(Long permissionId) {
		return this.iPermissionRepository.findById(permissionId)
				.orElseThrow(() -> new ApplicationException("Permission Not Found"));
	}
	

}
