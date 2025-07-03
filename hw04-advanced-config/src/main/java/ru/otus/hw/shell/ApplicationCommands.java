package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.domain.Student;
import ru.otus.hw.security.InMemoryLoginContext;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent(value = "Application Commands")
@RequiredArgsConstructor
public class ApplicationCommands {

    private final LocalizedIOService ioService;

    private final TestRunnerService testRunnerService;

    private final InMemoryLoginContext loginContext;

    @ShellMethod(value = "Login command. Format: login {firstName} {lastName}", key = {"l", "login"})
    public void login(@ShellOption(defaultValue = "Unknown") String firstName,
                        @ShellOption(defaultValue = "Unknown") String lastName) {
        loginContext.login(firstName, lastName);
        ioService.printFormattedLineLocalized("ApplicationCommands.welcome.message", firstName, lastName);
    }

    @ShellMethod(value = "Run test command", key = {"run"})
    @ShellMethodAvailability(value = "isRunTestCommandAvailable")
    public void runTest() {
        Student currentStudent = new Student(loginContext.getFirstName(), loginContext.getLastName());
        testRunnerService.run(currentStudent);
    }

    private Availability isRunTestCommandAvailable() {
        return loginContext.isUserLoggedIn()
                ? Availability.available()
                : Availability.unavailable("You need to run the command \"login\"");
    }
}
