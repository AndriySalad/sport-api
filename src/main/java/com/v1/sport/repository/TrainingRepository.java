package com.v1.sport.repository;

import com.v1.sport.data.models.Training;
import com.v1.sport.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long>{
    List<Training> findAllByUserId(Long athleteId);

    List<Training> findAllByUserIdAndDateBetween(Long athleteId, String startDateFinal, String endDateFinal);

    Optional<Training> findByIdAndUser(Long trainingId, User user);
}
