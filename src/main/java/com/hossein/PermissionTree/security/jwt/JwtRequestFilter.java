package com.hossein.PermissionTree.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hossein.PermissionTree.exception.ApplicationException;
import com.hossein.PermissionTree.service.impl.infrastructure.CustomUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String requestTokenHeader = request.getHeader("Authorization");
		
		String username = null;
		String jwtToken = null;
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			
			try {
				username = this.jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Unabled To Get Jwt Token");
			} catch (ExpiredJwtException e) {
				throw new ApplicationException("JWT Token Has Expired");
			}
			
		} else {
			System.out.println("JWT Token Does Not Begin With Bearer String");
		}
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
