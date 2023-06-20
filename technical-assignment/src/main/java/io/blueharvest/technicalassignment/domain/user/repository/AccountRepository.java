package io.blueharvest.technicalassignment.domain.user.repository;

import io.blueharvest.technicalassignment.domain.user.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
}
