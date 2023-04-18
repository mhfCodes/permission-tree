package com.hossein.PermissionTree.mapper.Permission;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hossein.PermissionTree.controller.viewModel.Permission.PermissionViewModel;
import com.hossein.PermissionTree.model.permission.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

	@Mapping(source = "id", target = "permissionId")
	@Mapping(source = "name", target = "permissionName")
	@Mapping(source = "hierarchy", target = "permissionHierarchy")
	@Mapping(source = "menuItem", target = "permissionMenuItem")
	@Mapping(source = "parent.id", target = "permissionParentId")
	PermissionViewModel mapEToV(Permission entity);
	
	List<PermissionViewModel> mapEToVList(List<Permission> entityList);
	
}
