package ru.otus.hw.security;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.otus.hw.domain.Student;

import static java.util.Objects.nonNull;

@Getter
@Component
public class InMemoryLoginContext implements LoginContext{

    private Student currentStudent;

    @Override
    public void login(String firstName, String lastName) {
        currentStudent = new Student(firstName, lastName);
    }

    @Override
    public Student getLoggedUser() {
        return currentStudent;
    }

    @Override
    public boolean isUserLoggedIn() {
        return nonNull(currentStudent);
    }
}
