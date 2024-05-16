package com.example.lom.services;

import com.example.lom.models.Role;
import com.example.lom.models.User;
import com.example.lom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository  userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User myUser = userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(myUser.getUsername(),
                myUser.getPassword(), mapRolesToAthorities(myUser.getRoles()));
    }

    public User findUserByUsername(String username) throws UsernameNotFoundException
    {
        return userRepository.findByUsername(username);
    }

    public User findUserById(String userId) {
        Optional<User> userFromDb = userRepository.findById(UUID.fromString(userId));
        return userFromDb.orElse(new User());
    }

    private List<? extends GrantedAuthority> mapRolesToAthorities(Set<Role> roles)
    {
        return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.name())).collect(Collectors.toList());
    }

    public void addUser(User user) throws Exception
    {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null)
        {
            throw new Exception("user exist");
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        userRepository.save(user);
    }

    public void updateUser(UUID userId, String lastName, String firstName, String phone) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setLastName(lastName);
            user.setFirstName(firstName);
            user.setPhone(phone);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Не найден пользователь со следующим ID: " + userId);
        }
    }
}
