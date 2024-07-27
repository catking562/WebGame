package taewookim.WebGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import taewookim.system.MainSystem;

@SpringBootApplication
public class WebGameApplication {

	public static void main(String[] args) {
		new MainSystem().start();
		SpringApplication.run(WebGameApplication.class, args);
	}

}
