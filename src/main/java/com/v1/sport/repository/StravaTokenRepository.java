package com.v1.sport.repository;

import com.v1.sport.data.models.StravaToken;
import com.v1.sport.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StravaTokenRepository extends JpaRepository<StravaToken, Long> {
    Optional<StravaToken> findByUser(User user);

    Optional<StravaToken> findByUserId(Long id);
}
