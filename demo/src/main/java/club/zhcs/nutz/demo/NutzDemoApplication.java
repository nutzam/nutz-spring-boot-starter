package club.zhcs.nutz.demo;

import org.nutz.json.JsonShape;
import org.nutz.json.JsonShape.Type;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

	@Getter
	@AllArgsConstructor
	@JsonShape(value = Type.OBJECTWITHNAME, nameKey = "value")
	public enum Tets {
		A("a", "AA");

		String name;
		String code;
	}

}
