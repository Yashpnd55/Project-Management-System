package com.pms.service;

import com.pms.dto.LoginDto;
import com.pms.dto.RegisterDto;

public interface UserServce {
	
	public String registerUser(RegisterDto registerDto);
	public String login(LoginDto logindto);
	
}
