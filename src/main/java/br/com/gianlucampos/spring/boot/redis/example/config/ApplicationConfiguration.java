package br.com.gianlucampos.spring.boot.redis.example.config;

import br.com.gianlucampos.spring.boot.redis.example.repository.UserRepository;
import br.com.gianlucampos.spring.boot.redis.example.repository.UserRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }

}
