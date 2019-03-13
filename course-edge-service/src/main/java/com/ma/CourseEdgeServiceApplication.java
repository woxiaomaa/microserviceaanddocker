package com.ma;

import com.ma.course.api.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CourseEdgeServiceApplication {

//	@Reference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
	@Reference(version = "1.0.0")
	private DemoService demoService;

	public static void main(String[] args) {
		SpringApplication.run(CourseEdgeServiceApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner() {
		return args -> {
			System.out.println(demoService.sayHello("xiaoma"));
		};
	}
}
