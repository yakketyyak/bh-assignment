package io.blueharvest.technicalassignment.domain.user.repository;

import io.blueharvest.technicalassignment.domain.user.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findAllByAccountId(UUID accountId);
}
