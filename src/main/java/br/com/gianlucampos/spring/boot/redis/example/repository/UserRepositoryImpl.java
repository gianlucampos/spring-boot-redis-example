package br.com.gianlucampos.spring.boot.redis.example.repository;

import br.com.gianlucampos.spring.boot.redis.example.entities.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public User getUserById(Long id) {
        log.info("Pegando usuario pelo repositório");
        return new User(id, "Gianluca", "123");
    }

    @Override
    public List<User> getUsers() {
        return List.of(
            getUserById(1L),
            getUserById(2L),
            getUserById(3L)
        );
    }

    @Override
    public List<User> getUsersByIds(List<Long> ids) {
        List<User> usersList = getUsers();
        return ids.stream()
            .map(id -> usersList.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst().orElse(null)
            ).collect(Collectors.toList());
    }
}
