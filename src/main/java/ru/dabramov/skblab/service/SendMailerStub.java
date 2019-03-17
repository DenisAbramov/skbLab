package ru.dabramov.skblab.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.dabramov.skblab.mail.data.EmailAddress;
import ru.dabramov.skblab.mail.data.EmailContent;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class SendMailerStub implements SendMailer
{
    private static final Logger log = LoggerFactory.getLogger(SendMailerStub.class);

    @Override
    public void sendMail(EmailAddress toAddress, EmailContent messageBody) throws TimeoutException
    {
        if(shouldThrowTimeout()) {
            sleep();

            throw new TimeoutException("Timeout!");
        }

        if(shouldSleep()) {
            sleep();
        }
        // логика отправки емейла
        // заглушка, просто пишем в лог

        log.info("Message sent to {}, body {}.", toAddress, messageBody);
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