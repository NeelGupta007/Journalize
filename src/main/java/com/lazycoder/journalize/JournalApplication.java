package com.lazycoder.journalize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =SpringApplication.run(JournalApplication.class, args);
		System.out.println(Arrays.toString(context.getEnvironment().getActiveProfiles()));
	}

	@Bean
	public PlatformTransactionManager helperFunction(MongoDatabaseFactory dbfactory) {
		return new MongoTransactionManager(dbfactory);
	}

}
