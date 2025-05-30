package com.igor.bondezam.ToDoTask.config.security.auth;

import com.igor.bondezam.ToDoTask.domain.User;
import com.igor.bondezam.ToDoTask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findUserByEmail(email);
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException("User not found, this user doesn't exist");
        }
        return user;
    }
}
