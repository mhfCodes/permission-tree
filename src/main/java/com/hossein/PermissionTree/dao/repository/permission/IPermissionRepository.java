package com.hossein.PermissionTree.dao.repository.permission;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hossein.PermissionTree.dao.repository.impl.permission.PermissionCustomRepository;
import com.hossein.PermissionTree.model.permission.Permission;

public interface IPermissionRepository extends JpaRepository<Permission, Long>, PermissionCustomRepository {

}
