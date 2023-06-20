package io.blueharvest.technicalassignment.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.blueharvest.technicalassignment.common.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto extends BaseDto {

    private Double balance;
    private UUID accountId;
}
