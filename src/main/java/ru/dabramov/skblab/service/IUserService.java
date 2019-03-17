package ru.dabramov.skblab.service;

import ru.dabramov.skblab.persistence.model.State;
import ru.dabramov.skblab.persistence.model.User;
import ru.dabramov.skblab.web.error.UserAlreadyExistException;

/**
 * @author Dabramov
 */
public interface IUserService
{
	User registerNewUserAccount(User user) throws UserAlreadyExistException;

	void updateUser(String email,  State state);

	void deleteUser(User user);

	User findByEmail(String email);
}
