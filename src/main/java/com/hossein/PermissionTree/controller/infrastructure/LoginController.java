package com.hossein.PermissionTree.controller.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hossein.PermissionTree.dto.infrastructure.AuthReq;
import com.hossein.PermissionTree.dto.infrastructure.AuthRes;
import com.hossein.PermissionTree.exception.ApplicationException;
import com.hossein.PermissionTree.security.jwt.JwtTokenUtil;
import com.hossein.PermissionTree.service.impl.infrastructure.CustomUserDetailsService;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@PostMapping
	public ResponseEntity<?> authenticate(@RequestBody AuthReq authReq) throws Exception {
		this.authenticate(authReq.getUsername(), authReq.getPassword());
		final UserDetails useDetails = userDetailsService.loadUserByUsername(authReq.getUsername());
		final String token = jwtTokenUtil.generateToken(useDetails);
		return ResponseEntity.ok(new AuthRes(token));
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new ApplicationException("USER_DISABLED");
		} catch (BadCredentialsException e ) {
			throw new ApplicationException("INVALID_CREDENTIALS");
		}
	}

}
