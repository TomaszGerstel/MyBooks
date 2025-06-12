package com.tgerstel.mybooks.domain.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private String username;
    private String password;
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
