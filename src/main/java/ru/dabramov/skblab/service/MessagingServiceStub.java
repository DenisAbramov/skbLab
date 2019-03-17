package ru.dabramov.skblab.service;


import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.dabramov.skblab.mail.data.MessageId;
import ru.dabramov.skblab.mail.shared.UserInfo;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class MessagingServiceStub<T> implements MessagingService
{
    @Override
    public <T> MessageId send(Message<T> msg)
    {
        // отсылаем данные на шину, к примеру используя JmsTemplate.send ...
        // заглушка
        return new MessageId(UUID.randomUUID().toString());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Message<T> receive(MessageId messageId, Class<T> messageType) throws TimeoutException
    {
        if(shouldThrowTimeout()) {
            sleep();

            throw new TimeoutException("Timeout!");
        }

        if(shouldSleep()) {
            sleep();
        }

        // синхронно извлекаем данные из шины, используя JmsTemplate.receive ...
        // заглушка
        return (Message<T>) MessageBuilder.withPayload(new UserInfo("Иван", "Иванович", "Иванов", "mail@mail.ru", "APPROVED")).build();
    }

    private static void sleep()
    {
        try {
            Thread.sleep(TimeUnit.MINUTES.toMillis(1));
        } catch (InterruptedException ignore) { }
    }

    private static boolean shouldSleep() {
        return new Random().nextInt(10) == 1;
    }

    private static boolean shouldThrowTimeout() {
        return new Random().nextInt(10) == 1;
    }
}