package io.blueharvest.technicalassignment.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.blueharvest.technicalassignment.common.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLiteDto extends BaseDto {

    private String username;
    private String name;
    private String surname;
    private AccountDto account;
}
