package io.blueharvest.technicalassignment.domain.user.service;

import io.blueharvest.technicalassignment.common.exception.BusinessException;
import io.blueharvest.technicalassignment.domain.user.dto.AccountDto;
import io.blueharvest.technicalassignment.domain.user.forms.AccountCreationForm;

import java.util.UUID;

public interface AccountService {

    AccountDto create(AccountCreationForm creationForm) throws BusinessException;

    AccountDto findById(UUID id) throws BusinessException;
}
