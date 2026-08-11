package com.skala.quiz.api;

import com.skala.quiz.domain.Learner;
import com.skala.quiz.domain.Progress;
import com.skala.quiz.repository.LearnerRepository;
import com.skala.quiz.repository.ProgressRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class LearningApiController {
    private final LearnerRepository learners;
    private final ProgressRepository progress;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LearningApiController(LearnerRepository learners, ProgressRepository progress) {
        this.learners = learners;
        this.progress = progress;
    }

    @GetMapping("/users/check")
    public Availability check(@RequestParam String username) {
        String normalized = normalize(username);
        return new Availability(normalized, !learners.existsByUsernameIgnoreCase(normalized));
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserSession register(@RequestBody RegisterRequest request) {
        String username = normalize(request.username());
        String password = validatePassword(request.password());
        if (learners.existsByUsernameIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다.");
        }
        try {
            Learner saved = learners.saveAndFlush(new Learner(username, passwordEncoder.encode(password)));
            return new UserSession(saved.getUsername(), saved.getAccessToken());
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다.");
        }
    }

    @PostMapping("/sessions")
    public UserSession login(@RequestBody RegisterRequest request) {
        String username = normalize(request.username());
        Learner learner = learners.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 맞지 않습니다."));
        if (!passwordEncoder.matches(request.password() == null ? "" : request.password(), learner.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 맞지 않습니다.");
        }
        learner.refreshAccessToken();
        learners.save(learner);
        return new UserSession(learner.getUsername(), learner.getAccessToken());
    }

    @GetMapping("/progress")
    public List<ProgressResponse> load(@RequestHeader("X-Access-Token") String token) {
        Learner learner = authenticate(token);
        return progress.findAllByLearnerOrderByQuestionKey(learner).stream()
                .map(item -> new ProgressResponse(item.getQuestionKey(), item.getAnswer(), item.isPassed(), item.getUpdatedAt()))
                .toList();
    }

    @PutMapping("/progress/{questionKey}")
    public ProgressResponse save(@RequestHeader("X-Access-Token") String token,
                                 @PathVariable String questionKey,
                                 @RequestBody SaveProgressRequest request) {
        Learner learner = authenticate(token);
        if (!questionKey.matches("[a-z0-9-]{1,60}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "올바르지 않은 문제 키입니다.");
        }
        Progress item = progress.findByLearnerAndQuestionKey(learner, questionKey)
                .orElseGet(() -> new Progress(learner, questionKey));
        item.update(request.answer() == null ? "" : request.answer(), request.passed());
        Progress saved = progress.save(item);
        return new ProgressResponse(saved.getQuestionKey(), saved.getAnswer(), saved.isPassed(), saved.getUpdatedAt());
    }

    private Learner authenticate(String token) {
        return learners.findByAccessToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "다시 등록해 주세요."));
    }

    private String normalize(String username) {
        String value = username == null ? "" : username.trim().toLowerCase(Locale.ROOT);
        if (!value.matches("[a-z0-9_]{4,20}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디는 영문 소문자, 숫자, 밑줄 4~20자여야 합니다.");
        }
        return value;
    }

    private String validatePassword(String password) {
        if (password == null || password.length() < 6 || password.length() > 50) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 6~50자여야 합니다.");
        }
        return password;
    }

    public record RegisterRequest(String username, String password) {}
    public record Availability(String username, boolean available) {}
    public record UserSession(String username, String accessToken) {}
    public record SaveProgressRequest(String answer, boolean passed) {}
    public record ProgressResponse(String questionKey, String answer, boolean passed, Instant updatedAt) {}
}
