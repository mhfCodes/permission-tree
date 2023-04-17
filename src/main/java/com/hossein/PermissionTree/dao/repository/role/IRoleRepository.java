package com.hossein.PermissionTree.dao.repository.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hossein.PermissionTree.dao.repository.impl.role.RoleCustomRepository;
import com.hossein.PermissionTree.model.role.Role;

public interface IRoleRepository extends JpaRepository<Role, Long>, RoleCustomRepository  {

	Optional<Role> findById(Long id);
}
