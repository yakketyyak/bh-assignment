package io.blueharvest.technicalassignment.domain.user.mapper;

import io.blueharvest.technicalassignment.domain.user.dto.TransactionDto;
import io.blueharvest.technicalassignment.domain.user.entity.TransactionEntity;
import io.blueharvest.technicalassignment.domain.user.forms.TransactionCreationForm;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    TransactionEntity toEntity(TransactionCreationForm creationForm);

    Set<TransactionDto> toDtos(List<TransactionEntity> entities);
}


