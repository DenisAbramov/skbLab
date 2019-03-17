package ru.dabramov.skblab;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.dabramov.skblab.web.controller.RegistrationController;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkblabApplicationTests
{
	@Autowired
	private RegistrationController controller;

	@Test
	public void contextLoads()
	{
		assertThat(controller).isNotNull();
	}
}
