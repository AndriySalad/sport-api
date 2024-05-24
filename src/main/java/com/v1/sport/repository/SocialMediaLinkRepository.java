package com.v1.sport.repository;

import com.v1.sport.data.models.SocialMediaLink;
import com.v1.sport.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialMediaLinkRepository extends JpaRepository<SocialMediaLink, Long> {
    Optional<SocialMediaLink> findByIdAndUser(Long linkId, User user);
}
