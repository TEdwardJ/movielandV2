package edu.ted.web.movieland.entity;

import javax.persistence.*;

public enum UserRole {

    ADMIN("ADMIN"),
    USER("USER");

    UserRole(String name) {
        this.name = name;
    }

    @Column(name="role_name")
    private String name;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static UserRole fromCode(String code) {
        if (  "ADMIN".equalsIgnoreCase(code)) {
            return ADMIN;
        }
        if (  "USER".equalsIgnoreCase(code)) {
            return USER;
        }
        throw new UnsupportedOperationException(
                "The code " + code + " is not supported!"
        );
    }
}
