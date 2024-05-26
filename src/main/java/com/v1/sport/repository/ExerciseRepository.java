package com.v1.sport.repository;

import com.v1.sport.data.models.Exercise;
import com.v1.sport.data.models.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByIdAndTraining(Long exerciseId, Training training);
}
