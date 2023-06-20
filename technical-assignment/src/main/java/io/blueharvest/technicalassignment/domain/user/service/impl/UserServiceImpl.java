package io.blueharvest.technicalassignment.domain.user.service.impl;

import io.blueharvest.technicalassignment.common.exception.BusinessException;
import io.blueharvest.technicalassignment.common.exception.NotFoundException;
import io.blueharvest.technicalassignment.domain.user.dto.UserDto;
import io.blueharvest.technicalassignment.domain.user.dto.UserLiteDto;
import io.blueharvest.technicalassignment.domain.user.entity.UserEntity;
import io.blueharvest.technicalassignment.domain.user.mapper.UserMapper;
import io.blueharvest.technicalassignment.domain.user.repository.UserRepository;
import io.blueharvest.technicalassignment.domain.user.service.AccountService;
import io.blueharvest.technicalassignment.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final AccountService accountService;

    @Override
    public boolean existsById(UUID id) {
        try {
            return this.userRepository.existsById(id);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public UserEntity loadUserByUsername(String username) throws BusinessException {
        try {
            Optional<UserEntity> user = this.userRepository.findByUsername(username);
            if (user.isEmpty()) {
                log.warn("user {} not found in data base", username);
                throw new UsernameNotFoundException("user provided not found");
            }
            return user.get();
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(UUID id) throws BusinessException {
        try {
            UserEntity user = this.userRepository.findById(id)
                    .orElseThrow(() ->
                            new NotFoundException(String.format("User with provided id %s is not found", id)));
            UserDto dto = this.userMapper.toDto(user);
            dto.setAccount(this.accountService.findById(id));
            return dto;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserLiteDto> findAll(Pageable pageable) throws BusinessException {
        try {
            Page<UserEntity> result = this.userRepository.findAll(pageable);
            List<UserLiteDto> response = result.get()
                    .map(user -> {
                        UserLiteDto dto = this.userMapper.toLiteDto(user);
                        dto.setAccount(this.accountService.findById(user.getIdentifier()));
                        return dto;
                    }).toList();
            return new PageImpl<>(response, pageable, result.getTotalElements());
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}
