package com.capstone.capstone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="player_table")
@NoArgsConstructor
@AllArgsConstructor
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto_increment
    @Column
    private Long id;
    @Column(unique = true)
    private String userId;
    @Column
    private String userPassword;
    @Column(unique = true)
    private String nickName;
}
