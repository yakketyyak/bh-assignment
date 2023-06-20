package io.blueharvest.technicalassignment.domain.auth.dto;

import io.blueharvest.technicalassignment.common.validator.NoXssContent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @NotBlank
    @NoXssContent
    private String username;
    @NotBlank
    @NoXssContent
    private String password;
}
