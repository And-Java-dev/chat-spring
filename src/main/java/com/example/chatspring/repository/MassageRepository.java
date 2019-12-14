package com.example.chatspring.repository;

import com.example.chatspring.model.Massage;
import com.example.chatspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MassageRepository extends JpaRepository<Massage,Integer> {
    List<Massage> findAllByTo(User user);
    List<Massage> findAllByFrom(User user);
}
