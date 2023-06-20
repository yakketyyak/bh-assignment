package io.blueharvest.technicalassignment.domain.user.service;

import io.blueharvest.technicalassignment.common.exception.BusinessException;
import io.blueharvest.technicalassignment.domain.user.dto.TransactionDto;
import io.blueharvest.technicalassignment.domain.user.forms.TransactionCreationForm;

import java.util.Set;
import java.util.UUID;

public interface TransactionService {

    void create(TransactionCreationForm creationForm) throws BusinessException;

    Set<TransactionDto> findByAccountId(UUID accountId) throws BusinessException;
}
