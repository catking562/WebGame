package taewookim.WebGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import taewookim.system.MainSystem;

@SpringBootApplication
public class WebGameApplication {

	public static void main(String[] args) {
		new MainSystem().start(); //새로운 스레드를 시작합니다.
		SpringApplication.run(WebGameApplication.class, args);
	}

}
