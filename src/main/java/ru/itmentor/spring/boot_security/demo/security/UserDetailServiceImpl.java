package ru.itmentor.spring.boot_security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repositories.UserRepositories;

import java.util.Optional;
@Service("userDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepositories userRepositories;

    @Autowired
    public UserDetailServiceImpl(UserRepositories userRepositories) {
        this.userRepositories = userRepositories;
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepositories.findUserByUsername(username);
        if(user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return user.get();
    }
}
