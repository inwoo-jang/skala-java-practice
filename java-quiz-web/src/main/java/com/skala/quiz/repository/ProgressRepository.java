package com.skala.quiz.repository;

import com.skala.quiz.domain.Learner;
import com.skala.quiz.domain.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    List<Progress> findAllByLearnerOrderByQuestionKey(Learner learner);
    Optional<Progress> findByLearnerAndQuestionKey(Learner learner, String questionKey);
}
