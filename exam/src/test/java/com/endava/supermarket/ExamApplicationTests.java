package com.endava.supermarket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ExamApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;
	@Test
	public void contextLoads() throws Throwable {
		Assertions.assertNotNull(
				this.applicationContext, "The application context should have loaded");
	}

}
