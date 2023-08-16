package ru.itmentor.spring.boot_security.demo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "users")
public class User implements UserDetails{
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String lastName;
    @Column
    private int age;
    @Column
    private String username;
    @Column
    private String password;
    @ManyToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn (name = "role_id", foreignKey = @ForeignKey(name = "role_id_fk"))
    private Role role;

    public void setRole(Role role) {
        this.role = role;
    }
    @Transient
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String name, String lastName, int age, String username, String password) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        roles.add(this.getRole());
        return roles;
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
