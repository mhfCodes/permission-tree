package com.hossein.PermissionTree.mapper.Role;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hossein.PermissionTree.controller.viewModel.RoleViewModel;
import com.hossein.PermissionTree.dto.role.RoleDto;
import com.hossein.PermissionTree.model.role.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	
	@Mapping(source = "id", target = "id")
	@Mapping(source = "name", target = "name")
	Role mapDToE(RoleDto dto);

	@Mapping(source = "id", target = "roleId")
	@Mapping(source = "name", target = "roleName")
	RoleViewModel mapEToV(Role entity);
	
	List<RoleViewModel> mapEToVList(List<Role> entityList);
	
}
