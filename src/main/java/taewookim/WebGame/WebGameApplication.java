package taewookim.WebGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class WebGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebGameApplication.class, args);
	}

}
