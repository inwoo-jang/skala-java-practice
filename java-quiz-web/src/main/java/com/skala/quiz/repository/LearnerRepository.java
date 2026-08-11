package com.skala.quiz.repository;

import com.skala.quiz.domain.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LearnerRepository extends JpaRepository<Learner, Long> {
    boolean existsByUsernameIgnoreCase(String username);
    Optional<Learner> findByUsernameIgnoreCase(String username);
    Optional<Learner> findByAccessToken(String accessToken);
}
