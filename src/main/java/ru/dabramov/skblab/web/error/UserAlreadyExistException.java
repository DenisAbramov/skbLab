package ru.dabramov.skblab.web.error;

/**
 * Пользователь уже существует
 * @author Dabramov
 */
public final class UserAlreadyExistException extends RuntimeException
{
    private static final long serialVersionUID = 5861310537366287163L;

    public UserAlreadyExistException(final String message)
    {
        super(message);
    }
}
