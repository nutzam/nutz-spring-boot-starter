package club.zhcs.nutz.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author mdp 代码生成器
 *
 */
@SpringBootApplication
@EnableTransactionManagement
public class NutzDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NutzDemoApplication.class, args);
    }
}
