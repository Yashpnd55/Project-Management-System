package com.pms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
	
	private JwtAuthEntryPoint authEntryPoint;
	
	private CustomUserDetailsService userDetailsService;
	
	public SecurityConfig(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf -> csrf.disable())
		.exceptionHandling(exceptions -> exceptions
		        .authenticationEntryPoint(authEntryPoint) // Replaces the old .exceptionHandling() method
		    )
		 .sessionManagement(session -> session
		        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Functional style for session management
		    )
		.authorizeHttpRequests(auth -> auth.requestMatchers("/pms/register","/pms/login").permitAll()
		.anyRequest().authenticated());
		
		http.addFilterBefore(jwtAutheticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	  
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JWTAuthenticationFilter jwtAutheticationFilter() {
		return new JWTAuthenticationFilter();
	}
	
}
