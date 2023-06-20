package io.blueharvest.technicalassignment.domain.user.service.impl;

import io.blueharvest.technicalassignment.common.exception.BusinessException;
import io.blueharvest.technicalassignment.common.exception.DuplicateResourceException;
import io.blueharvest.technicalassignment.common.exception.NotFoundException;
import io.blueharvest.technicalassignment.domain.user.dto.AccountDto;
import io.blueharvest.technicalassignment.domain.user.entity.AccountEntity;
import io.blueharvest.technicalassignment.domain.user.forms.AccountCreationForm;
import io.blueharvest.technicalassignment.domain.user.forms.TransactionCreationForm;
import io.blueharvest.technicalassignment.domain.user.mapper.AccountMapper;
import io.blueharvest.technicalassignment.domain.user.repository.AccountRepository;
import io.blueharvest.technicalassignment.domain.user.repository.UserRepository;
import io.blueharvest.technicalassignment.domain.user.service.AccountService;
import io.blueharvest.technicalassignment.domain.user.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserRepository userRepository;
    private final TransactionService transactionService;

    @Override
    public AccountDto create(AccountCreationForm creationForm) throws BusinessException {
        try {
            UUID customerId = UUID.fromString(creationForm.getCustomerID());
            if (!this.userRepository.existsById(customerId)) {
                throw new NotFoundException(String.format("User with id %s not found",
                        creationForm.getCustomerID()));
            }
            if (this.accountRepository.existsById(customerId)) {
                throw new DuplicateResourceException(String.format("Account associated to user %s already exists",
                        creationForm.getCustomerID()));
            }
            AccountEntity saved = this.accountRepository.save(this.accountMapper.toEntity(creationForm));
            if (creationForm.getInitialCredit() > 0d) {
                this.transactionService.create(TransactionCreationForm
                        .builder()
                        .account(saved)
                        .balance(creationForm.getInitialCredit()).build());
            }
            return this.accountMapper.toDto(saved);
        } catch (NotFoundException | DuplicateResourceException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto findById(UUID id) throws BusinessException {
        try {
            AccountEntity account = this.accountRepository.findById(id).orElse(null);
            if (Objects.isNull(account)) {
                return null;
            }
            AccountDto dto = this.accountMapper.toDto(account);
            dto.setTransactions(this.transactionService.findByAccountId(id));
            return dto;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

}
