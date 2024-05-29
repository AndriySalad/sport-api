package com.v1.sport.data.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "exercise")
@Table
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ExerciseType type;

    @Column(name = "measurement")
    private String measurement;

    @Column(name = "sets")
    private Long sets;

    @Column(name = "repetitions")
    private Long repetitions;

    @Column(name = "complited")
    private Boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    private Training training;
}
