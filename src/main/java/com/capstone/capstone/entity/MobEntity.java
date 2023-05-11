package com.capstone.capstone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name="mob_table")
@NoArgsConstructor
@AllArgsConstructor
public class MobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto_increment
    @Column
    private Long id;
    @Column
    private String mobName;
    @Column
    private String mobSound;
    @OneToMany(mappedBy = "mob")
    private List<StageMobEntity> stage = new ArrayList<>();
}
