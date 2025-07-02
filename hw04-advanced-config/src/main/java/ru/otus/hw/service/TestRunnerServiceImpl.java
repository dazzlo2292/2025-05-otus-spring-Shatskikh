package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;

@RequiredArgsConstructor
@Service
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final ResultService resultService;

    private final LocalizedIOService ioService;

    @Override
    public void run(Student student) {
        try {
            var testResult = testService.executeTestFor(student);
            resultService.showResult(testResult);
        } catch (Exception e) {
            ioService.printLineLocalized("TestRunnerService.error.loading.test");
        }
    }
}
