package com.hossein.PermissionTree.dao.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hossein.PermissionTree.dao.repository.impl.user.UserCustomRepository;
import com.hossein.PermissionTree.model.user.UserModel;

public interface IUserRepository extends JpaRepository<UserModel, Long>, UserCustomRepository {

	Optional<UserModel> findByUsername(String username);
}
