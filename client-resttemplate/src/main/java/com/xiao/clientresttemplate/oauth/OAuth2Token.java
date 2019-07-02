package com.xiao.clientresttemplate.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class OAuth2Token {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

}
