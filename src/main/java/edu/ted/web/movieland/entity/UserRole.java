package edu.ted.web.movieland.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="role")
public class UserRole {

    @Id
    @Column(name="role_id")
    private int id;

    @Column(name="role_name")
    @Enumerated(EnumType.STRING)
    private UserRoleName role;

}
