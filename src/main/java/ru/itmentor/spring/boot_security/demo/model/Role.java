package ru.itmentor.spring.boot_security.demo.model;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private Long id;
    @Column (name = "role")
    private String role;
    public Role() {
    }
    public Role(String role) {
        this.role = role;
    }
    public Role(Long id) {this.id = id;}
    @Override
    public String getAuthority() {
        return role;
    }
}
