package com.example.chatspring.repository;

import com.example.chatspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String username);
    User findUserById(int id);
    List<User>findByName(String name);

}
