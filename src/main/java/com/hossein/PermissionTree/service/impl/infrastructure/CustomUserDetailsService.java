package com.hossein.PermissionTree.service.impl.infrastructure;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hossein.PermissionTree.dao.repository.user.IUserRepository;
import com.hossein.PermissionTree.exception.ApplicationException;
import com.hossein.PermissionTree.model.user.UserModel;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private IUserRepository iUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel userModel = this.iUserRepository.findByUsername(username)
									.orElseThrow(() -> new ApplicationException("Username not found"));
		
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		
		userModel.getRoles().forEach(role -> {
			role.getPermissions().forEach(permission -> {
				authorities.add(new SimpleGrantedAuthority(permission.getName()));
			});
		});
		
		User user = new User(userModel.getUsername(), userModel.getPassword(), authorities);
		
		return user;
	}

}
