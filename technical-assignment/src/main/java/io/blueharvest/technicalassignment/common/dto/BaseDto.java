package io.blueharvest.technicalassignment.common.dto;

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
public abstract class BaseDto {

    private UUID identifier;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
