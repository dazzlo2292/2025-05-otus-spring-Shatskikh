package ru.otus.hw.security;

public interface LoginContext {
    void login(String firstName, String lastName);

    boolean isUserLoggedIn();
}
