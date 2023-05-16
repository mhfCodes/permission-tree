package com.hossein.PermissionTree.controller.permission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hossein.PermissionTree.controller.viewModel.Permission.PermissionViewModel;
import com.hossein.PermissionTree.service.permission.IPermissionService;

@RestController
@RequestMapping("/api/permission")
@CrossOrigin
public class PermissionController {	
	
}
