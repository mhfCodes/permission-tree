package com.hossein.PermissionTree.mapper.User;

import java.util.HashSet;
import java.util.Set;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hossein.PermissionTree.controller.viewModel.User.UserViewModel;
import com.hossein.PermissionTree.dto.user.UserDto;
import com.hossein.PermissionTree.model.user.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(source = "username", target = "username")
	@Mapping(source = "password", target = "password")
	@Mapping(source = "userFirstName", target = "firstName")
	@Mapping(source = "userLastName", target = "lastName")
	UserModel mapDToE(UserDto dto);
	
	@Mapping(source = "id", target = "userId")
	@Mapping(source = "username", target = "username")
	@Mapping(source = "firstName", target = "userFirstName")
	@Mapping(source = "lastName", target = "userLastName")
	UserViewModel mapEToV(UserModel entity);
	
	@AfterMapping
	default void setPermissionsIds(@MappingTarget UserViewModel userViewModel, UserModel entity) {
		Set<String> permissionIds = new HashSet<>();
		entity.getRoles().forEach(role -> {
			role.getPermissions().forEach(permission -> {
				permissionIds.add("ROLE_" + permission.getId());
			});
		});
		userViewModel.setPermissionIds(permissionIds);
	}
	
}
