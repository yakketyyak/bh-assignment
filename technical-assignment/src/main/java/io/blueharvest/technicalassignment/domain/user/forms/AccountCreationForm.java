package io.blueharvest.technicalassignment.domain.user.forms;

import io.blueharvest.technicalassignment.common.validator.NoXssContent;
import jakarta.validation.constraints.NotBlank;
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
public class AccountCreationForm {

    @NotBlank
    @NoXssContent
    private String customerID;
    private double initialCredit;
}
