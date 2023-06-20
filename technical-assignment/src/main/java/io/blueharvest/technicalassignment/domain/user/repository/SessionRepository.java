package io.blueharvest.technicalassignment.domain.user.repository;

import io.blueharvest.technicalassignment.domain.user.entity.UserSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<UserSessionEntity, UUID> {

}
