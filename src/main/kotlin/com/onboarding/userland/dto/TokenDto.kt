package com.onboarding.userland.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenResponse(
        @JsonProperty("value") val value: String,
        @JsonProperty("type") val type: String,
        @JsonProperty("expired_at") val expiryDate: String
)