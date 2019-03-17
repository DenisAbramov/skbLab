package ru.dabramov.skblab.persistence.model;

/**
 * @author Dabramov
 * Состояния статуса обращения
 */
public enum State
{
    CHECK("На проверке"),
    APPROVED("Одобрено"),
    REJECTED("Отклонено");

    private String title;

    State(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
}
