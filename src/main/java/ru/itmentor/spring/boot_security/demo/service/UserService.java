package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repositories.RoleRepositories;
import ru.itmentor.spring.boot_security.demo.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepositories userRepositories;
    private final RoleRepositories roleRepositories;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepositories userRepositories, RoleRepositories roleRepositories, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepositories = userRepositories;
        this.roleRepositories = roleRepositories;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public List<User> getAllUsers() {
        return userRepositories.findAll();
    }
    @Transactional
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepositories.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepositories.deleteById(id);
    }
    public User getUserById(Long id) {
        Optional<User> getUser = userRepositories.findById(id);

    return getUser.orElse(null);
    }
    @Transactional

        public void updateUser(User user, String[] role) {
            Set<Role> rol = new HashSet<>();
            for (String s : role) {
                if (s.equals("ROLE_ADMIN")) {
                    rol.add(showRole(1L));
                } else {
                    rol.add(showRole(2L));
                }
            }
            user.setRoles(rol);
            userRepositories.save(user);
        }
    //  public void updateUser(Long id, User updateUser){
//    updateUser.setId(id);
//
//    userRepositories.save(updateUser);
//    }

    public Role showRole(Long id) {
        return roleRepositories.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepositories.findUserByUsername(username);
        if(user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return user.get();
    }

}
