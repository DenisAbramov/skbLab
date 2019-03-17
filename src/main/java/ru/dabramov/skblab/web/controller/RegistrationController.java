package ru.dabramov.skblab.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.dabramov.skblab.mail.data.MessageId;
import ru.dabramov.skblab.mail.listener.MessageListener;
import ru.dabramov.skblab.mail.shared.UserInfo;
import ru.dabramov.skblab.persistence.model.User;
import ru.dabramov.skblab.service.IUserService;
import ru.dabramov.skblab.service.MessagingService;
import ru.dabramov.skblab.web.error.UserAlreadyExistException;

import java.util.concurrent.TimeoutException;


/**
 * Контроллер для формы / карточки регистрации
 * @author Dabramov
 */
@Controller
public class RegistrationController
{
	private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	private IUserService userService;
	@Autowired
	private MessagingService messagingService;
	@Autowired
	private MessageListener<UserInfo> messageListener;


	@GetMapping(value = { "/", "/index" ,"/registration"})
	public String viewRegistrationForm(Model model)
	{
		model.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping("/registration")
	public String viewRegistrationForm(@ModelAttribute User user, Model model)
	{
		final MessageId msgID;
		try
		{
			// сохраним в бд
			user = userService.registerNewUserAccount(user);
			// отправим в шину
			msgID = messagingService.send(MessageBuilder.withPayload(new UserInfo(user)).build());
			// синхронно получим и обработаем
			messageListener.handleMessage(messagingService.receive(msgID, UserInfo.class));
		}
		catch (final UserAlreadyExistException e)
		{
			// если такой пользователь уже имеется сообщим об этом
			model.addAttribute("errorMessage", e.getMessage());
			return "registration";
		}
		catch (TimeoutException e)
		{
			// если упало по таймауту, то данные позже обработаются в асинхронном лисенере SimpleMessageListener
			model.addAttribute("user", user);
			return "successRegister";
		}
		catch (Exception e)
		{
			// в случае любой другой ошибки напишем в лог и попросим повторить
			log.error(e.getMessage());
			// удалим сохраненного юзера
			userService.deleteUser(user);
			model.addAttribute("errorMessage", "К сожалению, сервис недоступен, попробуйте позднее.");
			return "registration";
		}
		model.addAttribute("user", user);
		return "successRegister";
	}
}
