package io.blueharvest.technicalassignment.domain.user.service;

import io.blueharvest.technicalassignment.domain.user.entity.UserSessionEntity;

import java.util.Optional;
import java.util.UUID;

public interface SessionService {

    String startSession(UUID userId);

    void endSession(UUID sessionId);

    boolean existsById(UUID id);

    Optional<UserSessionEntity> getBySessionId(UUID sessionId);
}
