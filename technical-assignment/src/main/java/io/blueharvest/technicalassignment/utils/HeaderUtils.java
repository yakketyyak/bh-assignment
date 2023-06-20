package io.blueharvest.technicalassignment.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public final class HeaderUtils {
    private static final String TOKEN_TYPE = "Bearer ";

    public static String extractToken(HttpServletRequest servletRequest) {
        Optional<String> authorization = Optional.ofNullable(servletRequest.getHeader("Authorization"));
        return authorization.filter(s -> s.startsWith(TOKEN_TYPE))
                .map(s -> s.substring(TOKEN_TYPE.length())).orElse("");
    }
}
