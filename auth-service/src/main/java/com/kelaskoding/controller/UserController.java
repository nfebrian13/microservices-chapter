package com.kelaskoding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kelaskoding.entity.UserInfo;
import com.kelaskoding.service.UserInfoService;

@Controller
@RequestMapping("/api/auth")
public class UserController {

	@Autowired
	private UserInfoService userInfoService;

	@PostMapping("/register")
	public ResponseEntity<String> addUser(@RequestBody UserInfo userInfo) {
		userInfoService.addUser(userInfo);
		return ResponseEntity.ok("User added successfully");
	}
}