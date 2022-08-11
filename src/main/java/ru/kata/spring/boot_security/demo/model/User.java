package ru.kata.spring.boot_security.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String name;

    @Column(name = "firstname", unique = true)
    private String firstname;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "age")
    private byte age;

    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String name, String firstname, String lastName, byte age, String password, Role... roles) {
        this.name = name;
        this.firstname = firstname;
        this.lastName = lastName;
        this.age = age;
        this.password = password;
        Collections.addAll ( this.roles, roles );
    }

    @Override
    public String getUsername() {
        return name;
    }

    public ArrayList<String> getRolesUser(User user) {
        ArrayList<String> rolesList = new ArrayList<>();
        for(Role role: user.getRoles()) {
            rolesList.add(role.getRole());
        }
        return rolesList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals ( id, user.id ) && age == user.age && name.equals(user.name)
                && firstname.equals(user.firstname)
                && lastName.equals(user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, firstname, lastName, age);
    }
}
