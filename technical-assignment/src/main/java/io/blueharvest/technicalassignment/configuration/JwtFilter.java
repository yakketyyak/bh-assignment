package io.blueharvest.technicalassignment.configuration;

import io.blueharvest.technicalassignment.domain.user.entity.UserSessionEntity;
import io.blueharvest.technicalassignment.domain.user.service.SessionService;
import io.blueharvest.technicalassignment.utils.HeaderUtils;
import io.blueharvest.technicalassignment.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final SessionService sessionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = HeaderUtils.extractToken(request);
        if (!StringUtils.equals(token, "")) {
            String sessionId = jwtUtils.extractSessionId(token);
            Optional<UserSessionEntity> optionalWebSession = sessionService.getBySessionId(UUID.fromString(sessionId));
            if (optionalWebSession.isPresent() && Objects.isNull(optionalWebSession.get().getSessionEndTime())) {
                if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                    UsernamePasswordAuthenticationToken authentication = jwtUtils.getAuthentication(token);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
