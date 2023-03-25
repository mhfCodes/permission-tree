package com.hossein.PermissionTree.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class home {

	@GetMapping
	public String getHome() {
		return new Date().toString();
	}
	
}
