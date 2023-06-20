package io.blueharvest.technicalassignment.domain.user.mapper;

import io.blueharvest.technicalassignment.domain.user.dto.UserDto;
import io.blueharvest.technicalassignment.domain.user.dto.UserLiteDto;
import io.blueharvest.technicalassignment.domain.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto toDto(UserEntity entity);

    UserLiteDto toLiteDto(UserEntity entity);
}
