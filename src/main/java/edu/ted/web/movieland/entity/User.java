package edu.ted.web.movieland.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode
public class User implements UserDetails {

    @Id
    @Column(name = "usr_id")
    private int id;

    @Column(name = "usr_email")
    private String email;

    @Column(name = "usr_sole")
    @EqualsAndHashCode.Exclude
    private String salt;

    @Column(name = "usr_password_enc")
    @EqualsAndHashCode.Exclude
    private String password;

    @Column(name = "usr_name")
    private String nickname;

    @EqualsAndHashCode.Exclude
    @ManyToMany//(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "usr_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<UserRole> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
