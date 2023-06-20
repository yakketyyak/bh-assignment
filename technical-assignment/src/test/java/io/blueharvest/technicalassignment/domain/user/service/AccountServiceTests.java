package io.blueharvest.technicalassignment.domain.user.service;

import io.blueharvest.technicalassignment.AbstractUnitTests;
import io.blueharvest.technicalassignment.common.exception.DuplicateResourceException;
import io.blueharvest.technicalassignment.common.exception.NotFoundException;
import io.blueharvest.technicalassignment.domain.user.dto.AccountDto;
import io.blueharvest.technicalassignment.domain.user.entity.AccountEntity;
import io.blueharvest.technicalassignment.domain.user.forms.AccountCreationForm;
import io.blueharvest.technicalassignment.domain.user.forms.TransactionCreationForm;
import io.blueharvest.technicalassignment.domain.user.mapper.AccountMapper;
import io.blueharvest.technicalassignment.domain.user.repository.AccountRepository;
import io.blueharvest.technicalassignment.domain.user.repository.UserRepository;
import io.blueharvest.technicalassignment.domain.user.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountServiceTests extends AbstractUnitTests {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TransactionService transactionService;
    @Captor
    private ArgumentCaptor<TransactionCreationForm> captor;
    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void createAccountWithoutTransactionOk() {
        String customerID = "2c0db8e0-caa7-4025-9cea-5e6f3f2c9d0c";
        LocalDateTime now = LocalDateTime.now();
        AccountCreationForm creationForm = AccountCreationForm.builder()
                .customerID(customerID)
                .build();
        AccountEntity saved = AccountEntity.builder()
                .identifier(UUID.fromString(customerID))
                .createdAt(now).build();
        AccountDto dto = AccountDto.builder()
                .createdAt(now.toString())
                .identifier(UUID.fromString(customerID)).build();

        when(this.userRepository.existsById(any(UUID.class))).thenReturn(true);
        when(this.accountRepository.existsById(any(UUID.class))).thenReturn(false);
        when(this.accountMapper.toEntity(any(AccountCreationForm.class))).thenReturn(AccountEntity.builder()
                .identifier(UUID.fromString(customerID))
                .build());
        when(this.accountRepository.save(any(AccountEntity.class))).thenReturn(saved);
        when(this.accountMapper.toDto(any(AccountEntity.class))).thenReturn(dto);

        AccountDto response = this.accountService.create(creationForm);

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(dto);
        verify(this.transactionService, never()).create(any(TransactionCreationForm.class));
    }

    @Test
    void createAccountWithTransactionOk() {
        String customerID = "2c0db8e0-caa7-4025-9cea-5e6f3f2c9d0c";
        LocalDateTime now = LocalDateTime.now();
        AccountCreationForm creationForm = AccountCreationForm.builder()
                .customerID(customerID)
                .initialCredit(100)
                .build();
        AccountEntity saved = AccountEntity.builder()
                .identifier(UUID.fromString(customerID))
                .createdAt(now).build();
        AccountDto dto = AccountDto.builder()
                .createdAt(now.toString())
                .identifier(UUID.fromString(customerID)).build();

        when(this.userRepository.existsById(any(UUID.class))).thenReturn(true);
        when(this.accountRepository.existsById(any(UUID.class))).thenReturn(false);
        when(this.accountMapper.toEntity(any(AccountCreationForm.class))).thenReturn(AccountEntity.builder()
                .identifier(UUID.fromString(customerID))
                .build());
        when(this.accountRepository.save(any(AccountEntity.class))).thenReturn(saved);
        when(this.accountMapper.toDto(any(AccountEntity.class))).thenReturn(dto);

        AccountDto response = this.accountService.create(creationForm);

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(dto);
        verify(this.transactionService).create(captor.capture());
        TransactionCreationForm value = captor.getValue();
        assertThat(value.getAccount()).usingRecursiveComparison()
                .isEqualTo(saved);
        assertThat(value.getBalance()).isEqualTo(100);
    }

    @Test
    void createAccountUserNotExists() {
        String customerID = "2c0db8e0-caa7-4025-9cea-5e6f3f2c9d0c";
        AccountCreationForm creationForm = AccountCreationForm.builder()
                .customerID(customerID)
                .build();
        when(this.userRepository.existsById(any(UUID.class))).thenReturn(false);

        assertThrows(NotFoundException.class,
                () -> this.accountService.create(creationForm));
    }

    @Test
    void createAccountDuplicated() {
        String customerID = "2c0db8e0-caa7-4025-9cea-5e6f3f2c9d0c";
        AccountCreationForm creationForm = AccountCreationForm.builder()
                .customerID(customerID)
                .build();
        when(this.userRepository.existsById(any(UUID.class))).thenReturn(true);
        when(this.accountRepository.existsById(any(UUID.class))).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> this.accountService.create(creationForm));
    }
}

