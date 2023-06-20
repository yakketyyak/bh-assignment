package io.blueharvest.technicalassignment.domain.user.service.impl;

import io.blueharvest.technicalassignment.domain.user.entity.UserSessionEntity;
import io.blueharvest.technicalassignment.domain.user.repository.SessionRepository;
import io.blueharvest.technicalassignment.domain.user.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    public String startSession(UUID userId) {
        UserSessionEntity session = UserSessionEntity
                .builder()
                .userId(userId)
                .sessionStartTime(LocalDateTime.now())
                .build();
        return sessionRepository.save(session).getIdentifier().toString();
    }

    @Override
    public void endSession(UUID sessionId) {
        sessionRepository.findById(sessionId)
                .ifPresent(session -> {
                    session.setSessionEndTime(LocalDateTime.now());
                    sessionRepository.save(session);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return sessionRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserSessionEntity> getBySessionId(UUID sessionId) {
        return sessionRepository.findById(sessionId);
    }
}
