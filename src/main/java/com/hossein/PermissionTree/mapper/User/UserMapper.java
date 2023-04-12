package com.hossein.PermissionTree.mapper.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hossein.PermissionTree.dto.user.UserDto;
import com.hossein.PermissionTree.model.user.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(source = "username", target = "username")
	@Mapping(source = "password", target = "password")
	@Mapping(source = "userFirstName", target = "firstName")
	@Mapping(source = "userLastName", target = "lastName")
	UserModel mapDToE(UserDto dto);
	
}
