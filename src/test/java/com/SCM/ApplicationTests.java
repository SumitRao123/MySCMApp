package com.SCM;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.SCM.services.EmailService;

@SpringBootTest
class ApplicationTests {
    
	


	@Test
	void contextLoads() {

	}
     @Autowired
	private EmailService service;

    @Test
	void sendEmailTest() {
		service.sendEmail("amitkherkar169@gmail.com", "Just managing the emails",
				"Hi mama kasai ho");
	}
	
}
