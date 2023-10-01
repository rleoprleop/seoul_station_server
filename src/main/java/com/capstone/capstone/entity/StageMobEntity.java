/*package com.capstone.capstone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="stage_mob_table")
@NoArgsConstructor
@AllArgsConstructor
public class StageMobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto_increment
    @Column
    private Long id;
    @ManyToOne
    @JoinColumn(name="stage_id")
    private StageEntity stage;
    @ManyToOne
    @JoinColumn(name="mob_id")
    private MobEntity mob;
    @Column
    private Long x;
    @Column
    private Long y;
}
*/