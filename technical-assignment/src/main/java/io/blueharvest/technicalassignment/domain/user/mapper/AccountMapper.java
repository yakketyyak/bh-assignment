package io.blueharvest.technicalassignment.domain.user.mapper;

import io.blueharvest.technicalassignment.domain.user.dto.AccountDto;
import io.blueharvest.technicalassignment.domain.user.entity.AccountEntity;
import io.blueharvest.technicalassignment.domain.user.forms.AccountCreationForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    @Mapping(target = "identifier", source = "customerID")
    AccountEntity toEntity(AccountCreationForm creationForm);

    AccountDto toDto(AccountEntity entity);
}
