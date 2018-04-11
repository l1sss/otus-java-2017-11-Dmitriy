package ru.slisenko.message_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.slisenko.message_system.config.WebSocketConfig;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(new Class<?>[] {Application.class, WebSocketConfig.class}, args);
	}
}
