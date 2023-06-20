package io.blueharvest.technicalassignment.domain.user.forms;

import io.blueharvest.technicalassignment.domain.user.entity.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreationForm {

    private Double balance;
    private AccountEntity account;
}
