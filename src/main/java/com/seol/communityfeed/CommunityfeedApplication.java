package com.seol.communityfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CommunityfeedApplication {

    public static void main(String[] args) {

        SpringApplication.run(CommunityfeedApplication.class, args);
        //System.out.println("Hello");
    }

}
