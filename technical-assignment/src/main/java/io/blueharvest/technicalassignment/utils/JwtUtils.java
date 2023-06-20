package io.blueharvest.technicalassignment.utils;

import io.blueharvest.technicalassignment.configuration.SecurityProperties;
import io.blueharvest.technicalassignment.domain.auth.dto.Token;
import io.blueharvest.technicalassignment.domain.user.entity.UserEntity;
import io.blueharvest.technicalassignment.domain.user.service.SessionService;
import io.blueharvest.technicalassignment.domain.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUtils {

    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final SecurityProperties properties;
    private final SessionService sessionService;
    private final UserService userService;

    public String extractUsername(String token) {

        return extractClaim(token, (Claims claims) -> claims.get(CustomClaims.USERNAME, String.class));
    }

    public String extractIssuer(String token) {
        return extractClaim(token, Claims::getIssuer);
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractSessionId(String token) {
        return extractClaim(token, (Claims claims) -> claims.get(CustomClaims.SESSION_ID, String.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(KEY).build()
                .parseClaimsJws(token).getBody();
    }

    public Boolean tokenHasNotExpired(String token) {
        return extractExpiration(token).after(new Date());
    }

    public Token generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        UserEntity user = userService.loadUserByUsername(username);

        String sessionId = sessionService.startSession(user.getIdentifier());

        claims.put(CustomClaims.USERNAME, username);
        claims.put(CustomClaims.SESSION_ID, sessionId);
        claims.put(CustomClaims.NAME, user.getName());
        claims.put(CustomClaims.SURNAME, user.getSurname());
        long startDate = System.currentTimeMillis();
        long tokenDuration = Long.sum(startDate, this.properties.getExpirationDate());


        String tokenStringValue = Jwts
                .builder()
                .setClaims(claims)
                .setSubject(String.valueOf(user.getIdentifier()))
                .setIssuedAt(new Date(startDate))
                .setExpiration(new Date(tokenDuration))
                .setIssuer(this.properties.getIssuer())
                .signWith(KEY).compact();


        return Token.builder()
                .accessToken(tokenStringValue)
                .expiredAt(tokenDuration)
                .build();
    }

    private boolean isIssuedByMe(String token) {
        String issuer = extractIssuer(token);
        return this.properties.getIssuer().equals(issuer);
    }

    public Boolean validateToken(String token) {

        try {
            return (tokenHasNotExpired(token) && isIssuedByMe(token));
        } catch (ExpiredJwtException ex) {
            log.warn("token has expired");
            return false;
        } catch (SignatureException ex) {
            log.warn("signature is not valid");
            return false;
        } catch (Exception ex) {
            log.warn("token not valid");
            return false;
        }
    }


    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String username = this.extractUsername(token);
        if (Objects.isNull(username)) {
            throw new IllegalArgumentException("cannot extract username");
        }
        UserEntity userDetails = userService.loadUserByUsername(username);
        if (Objects.nonNull(userDetails) && this.validateToken(token)) {
            return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(),
                    userDetails.getAuthorities());
        }
        throw new IllegalArgumentException("cannot load user with username " + username);
    }

}
