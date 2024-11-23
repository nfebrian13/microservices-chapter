package com.kelaskoding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorize -> {
			authorize.requestMatchers(antMatcher("/h2-console/**")).permitAll()
					.requestMatchers(antMatcher(HttpMethod.POST, "/api/auth/register")).permitAll()
					.requestMatchers(antMatcher(HttpMethod.POST, "/api/auth/generateToken")).permitAll()
					.requestMatchers(antMatcher(HttpMethod.POST, "/api/auth/validateToken")).permitAll().anyRequest()
					.authenticated();
		}).headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return httpSecurity.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
