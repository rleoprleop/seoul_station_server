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
@Table(name="stage_table")
@NoArgsConstructor
@AllArgsConstructor
public class StageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto_increment
    @Column
    private Long id;
    @Column
    private String stageName;
    @ManyToOne
    @JoinColumn(name="background_id")
    private BackgroundEntity background;

    @OneToMany(mappedBy = "stage")
    private List<StageMobEntity> stageMob = new ArrayList<>();
}
