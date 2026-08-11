package com.skala.quiz.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "progress", uniqueConstraints = @UniqueConstraint(name = "uk_progress_learner_question", columnNames = {"learner_id", "question_key"}))
public class Progress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "learner_id", nullable = false)
    private Learner learner;
    @Column(name = "question_key", nullable = false, length = 60)
    private String questionKey;
    @Lob @Column(nullable = false)
    private String answer;
    @Column(nullable = false)
    private boolean passed;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean attempted;
    @Column(nullable = false)
    private Instant updatedAt;

    protected Progress() {}
    public Progress(Learner learner, String questionKey) {
        this.learner = learner;
        this.questionKey = questionKey;
        this.answer = "";
        this.updatedAt = Instant.now();
    }
    public void update(String answer, boolean passed, boolean attempted) {
        this.answer = answer;
        this.passed = passed;
        this.attempted = attempted;
        this.updatedAt = Instant.now();
    }
    public String getQuestionKey() { return questionKey; }
    public String getAnswer() { return answer; }
    public boolean isPassed() { return passed; }
    public boolean isAttempted() { return attempted; }
    public Instant getUpdatedAt() { return updatedAt; }
}
