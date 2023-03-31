package br.com.gianlucampos.spring.boot.redis.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringBootRedisExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRedisExampleApplication.class, args);
    }

}
