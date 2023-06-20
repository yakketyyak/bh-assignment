package io.blueharvest.technicalassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestTechnicalAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.from(TechnicalAssignmentApplication::main).with(TestTechnicalAssignmentApplication.class).run(args);
    }

}
