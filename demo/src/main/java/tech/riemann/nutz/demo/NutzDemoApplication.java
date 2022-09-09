package tech.riemann.nutz.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class NutzDemoApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(NutzDemoApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }
}
