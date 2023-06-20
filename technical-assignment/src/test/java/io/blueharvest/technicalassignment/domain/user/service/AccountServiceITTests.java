package io.blueharvest.technicalassignment.domain.user.service;

import io.blueharvest.technicalassignment.AbstractTests;
import io.blueharvest.technicalassignment.common.exception.DuplicateResourceException;
import io.blueharvest.technicalassignment.common.exception.NotFoundException;
import io.blueharvest.technicalassignment.domain.user.dto.AccountDto;
import io.blueharvest.technicalassignment.domain.user.entity.AccountEntity;
import io.blueharvest.technicalassignment.domain.user.entity.TransactionEntity;
import io.blueharvest.technicalassignment.domain.user.forms.AccountCreationForm;
import io.blueharvest.technicalassignment.domain.user.repository.AccountRepository;
import io.blueharvest.technicalassignment.domain.user.repository.TransactionRepository;
import io.blueharvest.technicalassignment.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AccountServiceITTests extends AbstractTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountService accountService;

    @Test
    void createAccountWithoutTransactionOk() {
        UUID identifier = this.userRepository.findAll().get(1).getIdentifier();
        AccountCreationForm creationForm = AccountCreationForm.builder()
                .customerID(identifier.toString())
                .build();

        AccountDto response = this.accountService.create(creationForm);

        List<TransactionEntity> transactions = this.transactionRepository.findAllByAccountId(identifier);
        assertThat(transactions).isEmpty();
        assertThat(response.getIdentifier()).isEqualTo(identifier);
    }

    @Test
    void createAccountWithTransactionOk() {
        UUID identifier = this.userRepository.findAll().get(2).getIdentifier();
        AccountCreationForm creationForm = AccountCreationForm.builder()
                .customerID(identifier.toString())
                .initialCredit(100)
                .build();
        AccountDto response = this.accountService.create(creationForm);

        List<TransactionEntity> transactions = this.transactionRepository.findAllByAccountId(identifier);

        assertThat(transactions).isNotEmpty();
        assertThat(transactions.size()).isEqualTo(1);
        assertThat(response.getIdentifier()).isEqualTo(identifier);

        TransactionEntity transaction = transactions.get(0);
        assertThat(transaction.getAccountId()).isEqualTo(identifier);
    }

    @Test
    void createAccountUserNotExists() {
        String customerID = "2c0db8e0-caa7-4025-9cea-5e6f3f2c9d0c";
        AccountCreationForm creationForm = AccountCreationForm.builder()
                .customerID(customerID)
                .build();
        assertThrows(NotFoundException.class,
                () -> this.accountService.create(creationForm));
    }

    @Test
    void createAccountDuplicated() {
        UUID identifier = this.userRepository.findAll().get(0).getIdentifier();
        AccountCreationForm creationForm = AccountCreationForm.builder()
                .customerID(identifier.toString())
                .build();
        this.accountRepository.save(AccountEntity.builder().identifier(identifier).build());

        assertThrows(DuplicateResourceException.class,
                () -> this.accountService.create(creationForm));
    }
}

