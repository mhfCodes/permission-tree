package com.hossein.PermissionTree.controller.roleManagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hossein.PermissionTree.controller.viewModel.Role.RoleViewModel;
import com.hossein.PermissionTree.dto.role.RoleDto;
import com.hossein.PermissionTree.mapper.Role.RoleMapper;
import com.hossein.PermissionTree.service.role.IRoleService;

@RestController
@RequestMapping("/api/role")
@CrossOrigin
public class RoleManagementController {

	@Autowired
	private IRoleService iRoleService;
	
	@Autowired
	private RoleMapper mapper;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_15')")
	public List<RoleViewModel> getAll() {
		return this.mapper.mapEToVList(this.iRoleService.getAllRoles());
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_15')")
	public RoleViewModel load(@PathVariable Long id) {
		return this.mapper.mapEToV(this.iRoleService.load(id));
	}
	
	@PostMapping("/search")
	@PreAuthorize("hasRole('ROLE_15')")
	public List<RoleViewModel> search(@RequestBody RoleDto data) {
		return this.iRoleService.search(data);
	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_14', 'ROLE_16')")
	public long save(@RequestBody RoleDto dto) {
		return this.iRoleService.save(this.mapper.mapDToE(dto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_17')")
	public Boolean delete(@PathVariable Long id) {
		return this.iRoleService.delete(id);
	}
	
	
}
