package ru.dabramov.skblab;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.dabramov.skblab.mail.listener.UserInfoMessageListener;
import ru.dabramov.skblab.mail.shared.UserInfo;
import ru.dabramov.skblab.persistence.model.State;
import ru.dabramov.skblab.persistence.model.User;
import ru.dabramov.skblab.service.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoMessageListenerTest
{
    @Autowired
    private UserInfoMessageListener listener;

    private Message<UserInfo> message;
    private User user;

    @Autowired
    private IUserService userService;

    @Before
    public void SetUp()
    {
        message = MessageBuilder.withPayload(new UserInfo("Иван", "Иванович", "Иванов", "2mailTest@mail.ru", "APPROVED")).build();
        user = new User();
        user.setFirstName("Иван");
        user.setMiddleName("Иванович");
        user.setLastName("Иванов");
        user.setLogin("Ivan");
        user.setPassword("123123");
        user.setEmail("2mailTest@mail.ru");
        user.setState(State.CHECK);

        user = userService.registerNewUserAccount(user);
    }

    @After
    public void afterTest()
    {
        userService.deleteUser(user);
    }

    @Test
    public void handleMessage()
    {
        listener.handleMessage(message);
        Assert.assertEquals(userService.findByEmail(user.getEmail()).getState().getTitle(), State.APPROVED.getTitle());

    }
}
