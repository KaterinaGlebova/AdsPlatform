package ru.skypro.homework.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.UserService;
import java.util.Collection;
import java.util.LinkedList;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Пользователь с адресом %s не найден", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), mapRoleToAuthorities(user.getRole()));
    }
    private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Role role) {
        Collection<SimpleGrantedAuthority> result = new LinkedList<>();
        result.add(new SimpleGrantedAuthority("USER"));
        if (role == Role.ADMIN) {
            result.add(new SimpleGrantedAuthority("ADMIN"));
        }
        return result;
    }
}
