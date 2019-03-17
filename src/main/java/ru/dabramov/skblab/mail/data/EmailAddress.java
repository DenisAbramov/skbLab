package ru.dabramov.skblab.mail.data;

/**
 * @author Dabramov
 */
public class EmailAddress
{
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString()
    {
        return getEmail();
    }
}
