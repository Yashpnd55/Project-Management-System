package com.pms.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

public class SecurityConstants {
	
	public static final long JWT_EXPIRATION = 70000;	
	public static final String JWT_SECRET = "secret";

}
