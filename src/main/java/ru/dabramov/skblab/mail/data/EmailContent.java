package ru.dabramov.skblab.mail.data;

/**
 * @author Dabramov
 */
public class EmailContent
{
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return getText();
    }
}
