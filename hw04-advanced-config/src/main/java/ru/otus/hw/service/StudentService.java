package ru.otus.hw.service;

import ru.otus.hw.domain.Student;

public interface StudentService {

    Student loginCurrentStudent(String firstName, String lastName);

    Student determineCurrentStudent();
}
