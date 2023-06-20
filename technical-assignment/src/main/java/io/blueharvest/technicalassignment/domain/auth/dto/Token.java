package io.blueharvest.technicalassignment.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Token {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expired_at")
    private long expiredAt;
    @JsonProperty("token_type")
    @Builder.Default
    private String tokenType = "Bearer";
}
