package com.v1.sport.repository;

import com.v1.sport.data.models.Role;
import com.v1.sport.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);

    List<User> findAllByTrainer(User trainer);

    List<User> findAllByRole(Role role);
}
