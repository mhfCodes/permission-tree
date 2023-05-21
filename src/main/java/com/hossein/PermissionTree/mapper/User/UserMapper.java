package com.hossein.PermissionTree.mapper.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hossein.PermissionTree.controller.viewModel.User.UserViewModel;
import com.hossein.PermissionTree.dto.user.UserDto;
import com.hossein.PermissionTree.mapper.Role.RoleMapper;
import com.hossein.PermissionTree.model.user.UserModel;

@Mapper(
		componentModel = "spring",
		uses = RoleMapper.class
		)
public interface UserMapper {

	@Mapping(source = "userId", target = "id")
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
	
	List<UserViewModel> mapEToVList(List<UserModel> entityList);
	
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
