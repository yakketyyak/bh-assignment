package io.blueharvest.technicalassignment.configuration;

import io.blueharvest.technicalassignment.domain.user.entity.UserEntity;
import io.blueharvest.technicalassignment.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class InitUser implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        try {
            for (int i = 0; i < 10; i++) {
                this.userRepository.save(
                        UserEntity.builder()
                                .username(RandomStringUtils.randomAlphabetic(5))
                                .name(RandomStringUtils.randomAlphabetic(10))
                                .surname(RandomStringUtils.randomAlphabetic(10))
                                .password(this.passwordEncoder.encode("admin"))
                                .createdAt(LocalDateTime.now(ZoneId.of("UTC")))
                                .updatedAt(LocalDateTime.now(ZoneId.of("UTC")))
                                .build()
                );
            }
            this.userRepository.save(UserEntity.builder()
                    .username("admin")
                    .name("Admin")
                    .surname("Power")
                    .password(this.passwordEncoder.encode("admin"))
                    .createdAt(LocalDateTime.now(ZoneId.of("UTC")))
                    .updatedAt(LocalDateTime.now(ZoneId.of("UTC")))
                    .build());
        } catch (Exception e) {
            //Nothing here
        }

    }
}
