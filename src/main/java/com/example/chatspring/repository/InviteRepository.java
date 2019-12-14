package com.example.chatspring.repository;

import com.example.chatspring.model.Invite;
import com.example.chatspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite,Integer> {
    @Transactional
    @Modifying
    @Query(value = "update invite set invite.status =:status where invite.id =:id",
            nativeQuery = true)
    void changeInviteStatus(@Param("status")String status, @Param("id") int id);

    List<Invite> findAllByTo(User user);
    Invite findInviteById(int id);

}
