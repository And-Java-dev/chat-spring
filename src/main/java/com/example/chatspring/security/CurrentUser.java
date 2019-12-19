package com.example.chatspring.security;

import com.example.chatspring.model.User;
import lombok.Data;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

@Data
public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(),
                user.isEnable(), true, true, true, Arrays.asList(new SimpleGrantedAuthority(user.getType().name())));

        this.user = user;
    }

}
