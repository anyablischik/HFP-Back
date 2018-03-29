package com.craut.project.craut;

import com.craut.project.craut.service.RegistrationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrautApplicationTests {

	@Test
	public void contextLoads() {
		assertEquals(2, 1+1);
	}

}
