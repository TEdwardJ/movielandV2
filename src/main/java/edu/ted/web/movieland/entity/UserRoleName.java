package edu.ted.web.movieland.entity;

import javax.persistence.*;

public enum UserRoleName {

    ADMIN("ADMIN"),
    USER("USER");

    UserRoleName(String name) {
        this.name = name;
    }

    @Column(name="role_name")
    private String name;

    public void setName(String name) {
        this.name = name;
    }

}
