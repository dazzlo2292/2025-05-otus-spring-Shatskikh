package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent(value = "Application Commands")
@RequiredArgsConstructor
public class ApplicationCommands {

    private final StudentService studentService;

    private final TestRunnerService testRunnerService;

    private Student currentStudent;

    @ShellMethod(value = "Login command. Format: login {firstName} {lastName}", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "Unknown") String firstName,
                        @ShellOption(defaultValue = "Unknown") String lastName) {
        currentStudent = studentService.loginCurrentStudent(firstName, lastName);
        return String.format("Добро пожаловать: %s %s\n", firstName, lastName);
    }

    @ShellMethod(value = "Run test command", key = {"run"})
    public void runTest() {
        testRunnerService.run(currentStudent);
    }
}
