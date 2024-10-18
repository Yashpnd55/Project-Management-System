package com.pms.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTGenerator {
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		// Use java.time.Instant for current time
	    Instant currentDate = Instant.now();
	    
	    // Add JWT expiration in milliseconds
	    Instant expireDate = currentDate.plusMillis(SecurityConstants.JWT_EXPIRATION);
	    
	    // Example of generating a 64-byte key (512 bits) as a Base64 encoded string
	    String jwtSecret = SecurityConstants.JWT_SECRET;

	    // Convert the secret key to byte array
	    byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
	    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	    
	    // Convert java.time.Instant to java.util.Date for JWT compatibility
	    Date issuedAt = (Date) Date.from(currentDate);
	    Date expiration = (Date) Date.from(expireDate);
	    
	    // Generate the token
	    String token = Jwts
	                   .builder()
	                   .setSubject(username)
	                   .setIssuedAt(issuedAt)  // Use the converted java.util.Date
	                   .setExpiration(expiration) // Use the expiration java.util.Date
	                   .signWith(key , SignatureAlgorithm.HS512)
	                   .compact();
	    
	    return token;
	}
	
	public String getUSernameFromJWT(String token) {
		Claims claims = Jwts.parser()
				        .setSigningKey(SecurityConstants.JWT_SECRET)
				        .build()
				        .parseClaimsJws(token)
				        .getBody();
		
		return claims.getSubject();
				
	}
	
	public boolean validate(String token) {
		try {
			Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).build().parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			throw new AuthenticationCredentialsNotFoundException("JWT was Expired or Incorrect!!");
		}
	}

}
