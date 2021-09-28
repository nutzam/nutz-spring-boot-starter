package tech.riemann.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class SpringBootNutzDemoApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringBootNutzDemoApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }
}
