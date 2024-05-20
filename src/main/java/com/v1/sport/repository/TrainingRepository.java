package com.v1.sport.repository;

import com.v1.sport.data.models.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long>{
    List<Training> findAllByUserId(Long athleteId);
}
