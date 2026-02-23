package com.eDigest.journalApp;

import com.eDigest.journalApp.entity.User;
import com.eDigest.journalApp.repository.UserRepository;
import com.eDigest.journalApp.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JournalAppApplicationTests {

	@Autowired
	private UserRepository userRepository;

	public enum Role {
		ADMIN,
		USER
	}

	@Test
	void contextLoads() {
		assertEquals(3, 2+1);
		assertNotNull(userRepository.findByUserName("Name"));
	}

	

}
