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
import ru.dabramov.skblab.mail.data.MessageId;
import ru.dabramov.skblab.mail.listener.UserInfoMessageListener;
import ru.dabramov.skblab.mail.shared.UserInfo;
import ru.dabramov.skblab.persistence.model.State;
import ru.dabramov.skblab.persistence.model.User;
import ru.dabramov.skblab.service.IUserService;
import ru.dabramov.skblab.service.MessagingService;

import java.util.UUID;
import java.util.concurrent.TimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessagingServiceTest
{
    private Message<UserInfo> message;
    private MessageId messageId;
    private User user;

    @Autowired
    private UserInfoMessageListener listener;
    @Autowired
    private IUserService userService;
    @Autowired
    private MessagingService messagingService;

    @Before
    public void SetUp()
    {
        message = MessageBuilder.withPayload(new UserInfo("Иван", "Иванович", "Иванов", "1mailTest@mail.ru", "APPROVED")).build();
        messageId = new MessageId(UUID.randomUUID().toString());
        user = new User();
        user.setFirstName("Иван");
        user.setMiddleName("Иванович");
        user.setLastName("Иванов");
        user.setLogin("Ivan");
        user.setPassword("123123");
        user.setEmail("1mailTest@mail.ru");
        user.setState(State.CHECK);

        user = userService.registerNewUserAccount(user);
    }

    @After
    public void afterTest()
    {
        userService.deleteUser(user);
    }

    @Test
    public void sendTest()
    {
        Assert.assertNotNull(messagingService.send(message));
    }

    @Test
    public void receiveTest()
    {
        try
        {
            Assert.assertNotNull(messagingService.receive(messageId, UserInfo.class));
        }
        catch (TimeoutException e)
        {
            listener.handleMessage(message);
            Assert.assertEquals(userService.findByEmail(user.getEmail()).getState().getTitle(), State.APPROVED.getTitle());
        }
    }
}
