package br.com.gianlucampos.spring.boot.redis.example.repository;

import br.com.gianlucampos.spring.boot.redis.example.entities.User;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    //DA NO MESMO, so concatenar antes
//    @Cacheable("GET_USER")
    @Cacheable(cacheNames = "LOCALHOST", key = "'getUserById_' + #id")
    User getUserById(Long id);

    //RUIM fazer cache com listagem
    @Cacheable(cacheNames = "LOCALHOST", key = "'getUser'")
    List<User> getUsers();

    @Cacheable(cacheNames = "LOCALHOST", key = "'getUserByIds_' + #ids.hashCode()")
    List<User> getUsersByIds(List<Long> ids);

}
