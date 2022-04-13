package pt.ua.deti.codespell.codespellbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pt.ua.deti.codespell.codespellbackend.model.User;

@Service
public class SpringUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public SpringUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getPermissions());
    }

}