package com.pms.service.impl;


import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pms.dto.LoginDto;
import com.pms.dto.RegisterDto;
import com.pms.entity.Role;
import com.pms.entity.UserEntity;
import com.pms.repository.RoleRepositoty;
import com.pms.repository.UserRepository;
import com.pms.service.UserServce;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserServce {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepositoty roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public String registerUser(RegisterDto registerDto) {
		try {
			Optional<UserEntity> opt = this.userRepository.findByUsername(registerDto.getUsername());
			if(opt.isPresent()) {
				return "User with username " + registerDto.getUsername() + " already present";
			}
			else {
				UserEntity userEntity = new UserEntity();
				userEntity.setUsername(registerDto.getUsername());
				userEntity.setPassword(this.passwordEncoder.encode(registerDto.getPassword()));
				
				Role roles = this.roleRepository.findByName("USER").get();
				userEntity.setRoles(Collections.singletonList(roles));
				
				this.userRepository.save(userEntity);
				return "User " + registerDto.getUsername() + " registered successfully!!";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public String login(LoginDto logindto) {
		Authentication authentication = this.authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(logindto.getUsername(), logindto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "User signed success!!";
	}

}
