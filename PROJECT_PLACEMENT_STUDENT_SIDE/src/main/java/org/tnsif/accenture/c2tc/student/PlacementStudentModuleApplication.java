package org.tnsif.accenture.c2tc.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.tnsif.accenture.c2tc.student")
public class PlacementStudentModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlacementStudentModuleApplication.class, args);
    }
}

