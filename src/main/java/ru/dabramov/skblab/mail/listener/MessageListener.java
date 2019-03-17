package ru.dabramov.skblab.mail.listener;

import org.springframework.messaging.Message;


public interface MessageListener<T>
{
    void handleMessage(Message<T> incomingMessage);
}
