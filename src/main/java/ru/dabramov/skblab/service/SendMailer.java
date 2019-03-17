package ru.dabramov.skblab.service;

import ru.dabramov.skblab.mail.data.EmailAddress;
import ru.dabramov.skblab.mail.data.EmailContent;

import java.util.concurrent.TimeoutException;

public interface SendMailer
{
    void sendMail (EmailAddress toAddress, EmailContent messageBody) throws TimeoutException;
}
