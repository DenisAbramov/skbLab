package ru.dabramov.skblab.mail.data;

/**
 * @author Dabramov
 */
public class MessageId
{
    private String uuid;

    public MessageId(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
