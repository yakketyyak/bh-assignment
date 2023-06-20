package io.blueharvest.technicalassignment.domain.auth.controller;


import io.blueharvest.technicalassignment.common.advice.error.LoginError;
import io.blueharvest.technicalassignment.domain.auth.dto.AuthRequest;
import io.blueharvest.technicalassignment.domain.auth.dto.Token;
import io.blueharvest.technicalassignment.domain.user.service.SessionService;
import io.blueharvest.technicalassignment.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final SessionService sessionService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(LoginError.builder()
                            .error("invalid_grant")
                            .errorDescription("Bad credentials")
                            .build());
        }
        Token token = jwtUtils.generateToken(request.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutAllSession(@RequestHeader("Authorization") String token) {
        String sessionId = jwtUtils.extractSessionId(token.substring("Bearer ".length()));
        sessionService.endSession(UUID.fromString(sessionId));
        return ResponseEntity.accepted().build();
    }
}
