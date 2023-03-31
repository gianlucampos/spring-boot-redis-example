package br.com.gianlucampos.spring.boot.redis.example.entities;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Serializable {

    private Long id;
    private String name;
    private String password;

}
