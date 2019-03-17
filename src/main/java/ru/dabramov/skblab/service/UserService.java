package ru.dabramov.skblab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dabramov.skblab.persistence.dao.UserRepository;
import ru.dabramov.skblab.persistence.model.State;
import ru.dabramov.skblab.persistence.model.User;
import ru.dabramov.skblab.web.error.UserAlreadyExistException;

import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

/**
 * DAO для юзера
 * @author Dabramov
 */
@Service
public class UserService implements IUserService
{
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional(REQUIRES_NEW)
	public User registerNewUserAccount(User user)
	{
		if (emailExist(user.getEmail()))
			throw new UserAlreadyExistException(
					"Аккаунт с таким электронным адресом уже существует: " + user.getEmail());
		// зашифруем пароль, чтобы не хранить в открытом виде
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// При сохранении в БД помечаем статус юзера, как "на проверке"
		user.setState(State.CHECK);
		return repository.save(user);
	}

	@Override
	@Transactional(REQUIRES_NEW)
	public void updateUser(String email, State state)
	{
		User user = repository.findByEmail(email);
		if (user != null) {
			user.setState(state);
			repository.save(user);
		}
	}

	@Override
	public User findByEmail(String email)
	{
		return repository.findByEmail(email);
	}

	@Override
	public void deleteUser(User user)
	{
		repository.delete(user);
	}

	private boolean emailExist(final String email)
	{
		return repository.findByEmail(email) != null;
	}

}