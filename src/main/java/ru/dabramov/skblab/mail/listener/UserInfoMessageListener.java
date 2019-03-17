package ru.dabramov.skblab.mail.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import ru.dabramov.skblab.mail.data.EmailAddress;
import ru.dabramov.skblab.mail.data.EmailContent;
import ru.dabramov.skblab.service.SendMailer;
import ru.dabramov.skblab.mail.shared.UserInfo;
import ru.dabramov.skblab.persistence.model.State;
import ru.dabramov.skblab.service.IUserService;

import java.util.concurrent.TimeoutException;

/**
 * @author Dabramov
 * Лисенер для асинхронного получение данных пользователя
 * Предполагаю, что зарегистрирован в каком-нибудь DefaultMessageListenerContainer , если используется ActiveMQ
 */
@Component
public class UserInfoMessageListener implements MessageListener<UserInfo>
{
    private final SendMailer sender;
    private final IUserService userService;

    @Autowired
    public UserInfoMessageListener(SendMailer sender, IUserService userService)
    {
        this.sender = sender;
        this.userService = userService;
    }

    @Override
    /** Предполагаем и допускаем, что внутри этого метода ничего не упадет с ошибкой
     * т.е. есть некий контракт, что в ответе в поле статус будет валидное значение для енума State,
     * будут присутствовать все задействованные данные
     */
    public void handleMessage(Message<UserInfo> incomingMessage)
    {
        final UserInfo info = incomingMessage.getPayload();
        final EmailAddress emailAddress = new EmailAddress();
        emailAddress.setEmail(info.getEmail());

        final EmailContent content = new EmailContent();
        final State state = State.valueOf(info.getStatus());
        content.setText(String.format("Ваша заявка %s", State.APPROVED.equals(state) ? "одобрена" : "отклонена"));

        try
        {
            sender.sendMail(emailAddress,content);
        }
        catch (TimeoutException e)
        {
            // в случае, если не удалось отправить с первого раза, то необходимо сохранить объект UserInfo в БД (к примеру, как json) и передать
            // управление дальнейшими попытками отправить письмо, к примеру, Spring Quartz
        }

        // обновим пользователю состояние статуса
        userService.updateUser(info.getEmail(), state);
    }
}
