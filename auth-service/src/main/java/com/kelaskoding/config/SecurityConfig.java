package com.kelaskoding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
//                .requestMatchers("/h2-console/**").permitAll()  // Mengizinkan akses tanpa autentikasi ke H2 console
				.requestMatchers("/api/auth/register", 
								 "/api/auth/generateToken", 
								 "/api/auth/validateToken",
								 "/h2-console/**")
				.permitAll().anyRequest().authenticated() // Mengharuskan autentikasi untuk request lainnya
				.and().csrf().disable() // Menonaktifkan CSRF untuk H2 console
				.headers().frameOptions().disable(); // Menonaktifkan frame options untuk H2 console
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
