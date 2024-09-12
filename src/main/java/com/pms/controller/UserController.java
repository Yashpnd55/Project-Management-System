package com.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pms.dto.LoginDto;
import com.pms.dto.RegisterDto;
import com.pms.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/pms")
public class UserController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody(required = true) RegisterDto registerDto) {
		return ResponseEntity.ok(this.userServiceImpl.registerUser(registerDto));
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody(required = true) LoginDto logindto) {
		String msg = this.userServiceImpl.login(logindto);
		return new ResponseEntity<>(msg, HttpStatus.OK);
		
	}

}
