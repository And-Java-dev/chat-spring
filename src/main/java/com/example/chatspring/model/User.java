package com.example.chatspring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
//    @Email
    private String email;

    @Column
    private String password;

    @ManyToMany
    @JoinTable(name="user_friends",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="friends_id")
    )
    private List<User> friends;

    @ManyToMany
    @JoinTable(name="user_friends",
            joinColumns=@JoinColumn(name="friends_id"),
            inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private List<User> friendOf;

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public void setFriendOf(List<User> friendOf) {
        this.friendOf = friendOf;
    }

    public List<User> getFriends() {
        return friends;
    }

    public List<User> getFriendOf() {
        return friendOf;
    }

    @Column
    @Enumerated(EnumType.STRING)
    private UserType type = UserType.USER;

    @Column(name = "img_path")
    private String ImagePath;

    @Column
    private boolean isEnable;

    @Column
    private String token;

}
