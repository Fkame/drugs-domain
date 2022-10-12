package ru.drugsdomain.maindomain.TimeScheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TimeSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeSchedulerApplication.class, args);
	}

}
