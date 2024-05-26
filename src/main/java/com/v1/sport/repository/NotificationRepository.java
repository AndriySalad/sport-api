package com.v1.sport.repository;

import com.v1.sport.data.models.Notification;
import com.v1.sport.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByReceiverAndIsViewed(User user, boolean isViewed);

    List<Notification> findByReceiverId(Long userId);

    List<Notification> findAllByReceiverId(Long id);
}
