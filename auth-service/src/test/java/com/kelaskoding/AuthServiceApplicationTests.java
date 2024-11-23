package com.kelaskoding;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kelaskoding.entity.UserInfo;
import com.kelaskoding.repository.UserInfoRepository;

@SpringBootTest
class AuthServiceApplicationTests {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Test
	void contextLoads() {
		addedUserInfo();
	}

	void addedUserInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setName("Nana Febriana");
		userInfo.setEmail("febrian@gmail.com");
		userInfo.setPassword(passwordEncoder.encode("p@ssw0rd"));
		
		userInfoRepository.save(userInfo);
	}
}
