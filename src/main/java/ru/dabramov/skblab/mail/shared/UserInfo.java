package ru.dabramov.skblab.mail.shared;

import ru.dabramov.skblab.persistence.model.User;

/**
 * @author Dabramov
 * Интеграционные данные, для передачи/получение информации по запросу
 */
public class UserInfo
{
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String status;

    public UserInfo() {}

    public UserInfo(User user)
    {
        this(user.getFirstName(),user.getMiddleName(),user.getLastName(),user.getEmail());
    }

    public UserInfo(String firstName, String middleName, String lastName, String email) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
    }
    public UserInfo(String firstName, String middleName, String lastName, String email, String status) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

