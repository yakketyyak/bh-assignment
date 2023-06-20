package io.blueharvest.technicalassignment.domain.user.service.impl;

import io.blueharvest.technicalassignment.common.exception.BusinessException;
import io.blueharvest.technicalassignment.common.exception.NotFoundException;
import io.blueharvest.technicalassignment.domain.user.dto.TransactionDto;
import io.blueharvest.technicalassignment.domain.user.entity.TransactionEntity;
import io.blueharvest.technicalassignment.domain.user.forms.TransactionCreationForm;
import io.blueharvest.technicalassignment.domain.user.mapper.TransactionMapper;
import io.blueharvest.technicalassignment.domain.user.repository.AccountRepository;
import io.blueharvest.technicalassignment.domain.user.repository.TransactionRepository;
import io.blueharvest.technicalassignment.domain.user.service.TransactionService;
import io.blueharvest.technicalassignment.utils.PricingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;

    @Override
    public void create(TransactionCreationForm creationForm) throws BusinessException {
        try {
            TransactionEntity transaction = this.transactionMapper.toEntity(creationForm);
            transaction.setAccountId(creationForm.getAccount().getIdentifier());
            this.transactionRepository.save(transaction);
            creationForm.getAccount().setBalance(PricingUtils.sumDouble(creationForm.getAccount().getBalance(),
                    creationForm.getBalance()));
            this.accountRepository.save(creationForm.getAccount());
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<TransactionDto> findByAccountId(UUID accountId) throws BusinessException {
        try {
            if (!this.accountRepository.existsById(accountId)) {
                throw new NotFoundException(String.format("Account with id %s not found",
                        accountId));
            }
            List<TransactionEntity> transactions = this.transactionRepository.findAllByAccountId(accountId);
            return this.transactionMapper.toDtos(transactions);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}
