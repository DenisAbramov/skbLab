package ru.dabramov.skblab.persistence.model;

import javax.persistence.*;

/**
 * Пользователь
 * @author Dabramov
 */
@Entity
@Table(name = "users")
public class User
{
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String middleName;
	@Column(nullable = false)
	private String lastName;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String login;
	@Column(nullable = false)
	private String password;
	@Column
	private State state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String username) {
		this.email = username;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}