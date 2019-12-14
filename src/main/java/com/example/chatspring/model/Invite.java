package com.example.chatspring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Table(name = "invite")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @ManyToOne
    private User from;

    @ManyToOne
    private User to;

    @Column
    private String text;

    @Column
    private Date sendDate;

    @Column
    @Enumerated(EnumType.STRING)
    private InviteStatus status;

}
