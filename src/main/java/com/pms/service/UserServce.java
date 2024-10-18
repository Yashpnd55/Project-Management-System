package com.pms.service;

import com.pms.dto.AuthResponseDto;
import com.pms.dto.LoginDto;
import com.pms.dto.RegisterDto;

public interface UserServce {
	
	public String registerUser(RegisterDto registerDto);
	public AuthResponseDto login(LoginDto logindto);
	
}
