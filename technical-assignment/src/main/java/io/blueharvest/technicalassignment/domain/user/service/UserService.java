package io.blueharvest.technicalassignment.domain.user.service;

import io.blueharvest.technicalassignment.common.exception.BusinessException;
import io.blueharvest.technicalassignment.domain.user.dto.UserDto;
import io.blueharvest.technicalassignment.domain.user.dto.UserLiteDto;
import io.blueharvest.technicalassignment.domain.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends UserDetailsService {

    boolean existsById(UUID id);

    UserEntity loadUserByUsername(String username) throws BusinessException;

    UserDto findById(UUID id) throws BusinessException;

    Page<UserLiteDto> findAll(Pageable pageable) throws BusinessException;
}
