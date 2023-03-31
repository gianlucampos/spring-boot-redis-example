package br.com.gianlucampos.spring.boot.redis.example.controller;

import br.com.gianlucampos.spring.boot.redis.example.entities.User;
import br.com.gianlucampos.spring.boot.redis.example.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("app")
public class ExampleController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World");
    }

    @Cacheable("GET_USER_ID")
    @GetMapping("v1/users/{id}")
    public Integer getUserId(@PathVariable Integer id) {
        return getUserById(id) * 2;
    }


    @GetMapping("v2/users/{id}")
    public ResponseEntity<List<User>> getUser(@PathVariable Long id) {
        var user = userRepository.getUserById(id);
        var list = List.of(user);
        return ResponseEntity.ok(list);
    }

    @GetMapping("v2/users")
    public ResponseEntity<List<User>> getUser() {
        return ResponseEntity.ok(userRepository.getUsers());
    }

    @GetMapping("v2/users/filter-by-ids")
    public ResponseEntity<List<User>> getUserByIds(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(userRepository.getUsersByIds(ids));
    }

    private Integer getUserById(Integer userId) {
        log.info("Chamando método getUserById");
        List<Integer> users = List.of(1, 2, 3, 4, 5);
        return users.stream().filter(u -> u.equals(userId)).findFirst().orElse(0);
    }

}
