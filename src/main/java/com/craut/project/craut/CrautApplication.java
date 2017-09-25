package com.craut.project.craut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@SpringBootApplication
@Import({ SecurityConfiguration.class, WebConfiguration.class })
public class CrautApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrautApplication.class, args);
	}


}
