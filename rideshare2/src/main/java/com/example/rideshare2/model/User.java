package com.example.rideshare2.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String username;
    private String password;
    private String role;   // ROLE_DRIVER, ROLE_USER

}
