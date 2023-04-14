package com.hossein.PermissionTree;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hossein.PermissionTree.dao.repository.permission.IPermissionRepository;
import com.hossein.PermissionTree.dao.repository.role.IRoleRepository;
import com.hossein.PermissionTree.dao.repository.user.IUserRepository;
import com.hossein.PermissionTree.model.permission.Permission;
import com.hossein.PermissionTree.model.role.Role;
import com.hossein.PermissionTree.model.user.UserModel;

@SpringBootApplication
public class SpringSecurityHierarchy1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityHierarchy1Application.class, args);
	}
	
//	@Bean
//	CommandLineRunner run(IUserRepository iUserRepository, IRoleRepository iRoleRepository, IPermissionRepository iPermissionRepository) {
//		
//		return args -> {
//			
////			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
////			
////			Permission productsPermission = iPermissionRepository.save(new Permission("Products", "1", true, null));
////		
////			Permission productCreatePermission = iPermissionRepository.save(new Permission("Create Product", "1-2", false, productsPermission));
////			
////			Permission productReadPermission = iPermissionRepository.save(new Permission("Read Product", "1-3", false, productsPermission));
////			
////			Permission productUpdatePermission = iPermissionRepository.save(new Permission("Update Product", "1-4", false, productsPermission));
////			
////			Permission productDeletePermission = iPermissionRepository.save(new Permission("Delete Product", "1-5", false, productsPermission));
////			
////			Permission adminPanelPermission = iPermissionRepository.save(new Permission("Admin Panel", "6", true, null));
////			
////			Permission userManagementPermission = iPermissionRepository.save(new Permission("User Management", "6-7", true, adminPanelPermission));
////			
////			Permission roleManagementPermission = iPermissionRepository.save(new Permission("Role Management", "6-8", true, adminPanelPermission));
////			
////			Permission userCreatePermission = iPermissionRepository.save(new Permission("Create User", "6-7-9", false, userManagementPermission));
////			
////			Permission userReadPermission = iPermissionRepository.save(new Permission("Read User", "6-7-10", false, userManagementPermission));
////			
////			Permission userUpdatePermission = iPermissionRepository.save(new Permission("Update User", "6-7-11", false, userManagementPermission));
////			
////			Permission userDeletePermission = iPermissionRepository.save(new Permission("Delete User", "6-7-12", false, userManagementPermission));
////			
////			Permission userRoleUpdatePermission = iPermissionRepository.save(new Permission("Update Associated Roles", "6-7-13", false, userManagementPermission));
////			
////			Permission roleCreatePermission = iPermissionRepository.save(new Permission("Create Role", "6-8-14", false, roleManagementPermission));
////			
////			Permission roleReadPermission = iPermissionRepository.save(new Permission("Read Role", "6-8-15", false, roleManagementPermission));
////			
////			Permission roleUpdatePermission = iPermissionRepository.save(new Permission("Update Role", "6-8-16", false, roleManagementPermission));
////			
////			Permission roleDeletePermission = iPermissionRepository.save(new Permission("Delete Role", "6-8-17", false, roleManagementPermission));
////			
////			Permission rolePermissionUpdatePermission = iPermissionRepository.save(new Permission("Update Associated Permissions", "6-8-18", false, roleManagementPermission));
////			
//		
////			Role adminRole = iRoleRepository.save(new Role("ADMIN_ROLE", Set.of(productsPermission, productCreatePermission, productReadPermission, productUpdatePermission, productDeletePermission,
////					adminPanelPermission, userManagementPermission, roleManagementPermission, userCreatePermission, userReadPermission, userUpdatePermission, userDeletePermission,
////					userRoleUpdatePermission, roleCreatePermission, roleReadPermission, roleUpdatePermission, roleDeletePermission, rolePermissionUpdatePermission)));
////			
////			Role operatorRole = iRoleRepository.save(new Role("OPERATOR_ROLE", Set.of(productsPermission, productCreatePermission, productReadPermission, productUpdatePermission, productDeletePermission,
////																userReadPermission, roleReadPermission)));
////			
////			Role userRole = iRoleRepository.save(new Role("USER_ROLE", Set.of(productsPermission, productCreatePermission, productReadPermission, productUpdatePermission, productDeletePermission)));
////						
////			UserModel adminUser = new UserModel("adminUser", passwordEncoder.encode("adminUser007"), "Kaiden", "Reuben", Set.of(adminRole)); // only admin
////			UserModel operatorUser = new UserModel("operatorUser", passwordEncoder.encode("operatorUser007"), "Bruno", "Bernardina", Set.of(operatorRole)); // only operator
////			UserModel janelAgathe = new UserModel("janelAgathe", passwordEncoder.encode("janelAgathe007"), "Janel", "Agathe", Set.of(userRole)); // only user
////			UserModel orvarDaud = new UserModel("orvarDaud", passwordEncoder.encode("orvarDaud007"), "Orvar", "Daud", Set.of(adminRole, userRole)); // both admin and user
////			UserModel durgaBellamy = new UserModel("durgaBellamy", passwordEncoder.encode("durgaBellamy007"), "Durga", "Bellamy", Set.of(operatorRole, userRole)); // both operator and user
////			
////			iUserRepository.saveAll(List.of(adminUser, operatorUser, janelAgathe, orvarDaud, durgaBellamy));
//			
//			
////			Permission accountPermission = iPermissionRepository.save(new Permission("Account Settings", "21", true, null));
////			
////			Permission accountReadPermission = iPermissionRepository.save(new Permission("Read Account Settings", "21-22", false, accountPermission));
////			
////			Permission accountUpdatePermission = iPermissionRepository.save(new Permission("Update Account Settings", "21-23", false, accountPermission));
////			
////			Permission accountDeletePermission = iPermissionRepository.save(new Permission("Delete Account", "21-24", false, accountPermission));
////			
////			List<Role> roles = iRoleRepository.findAll();
////			roles.forEach(role -> {
////				role.getPermissions().add(accountPermission);
////				role.getPermissions().add(accountReadPermission);
////				role.getPermissions().add(accountUpdatePermission);
////				role.getPermissions().add(accountDeletePermission);
////				
////				iRoleRepository.save(role);
////			});
//			
//		};
//		
//	}

}
