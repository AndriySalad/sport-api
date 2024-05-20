package com.v1.sport.repository;

import com.v1.sport.data.models.TrainAdvice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingAdviceRepository extends JpaRepository<TrainAdvice, Long> {
}
