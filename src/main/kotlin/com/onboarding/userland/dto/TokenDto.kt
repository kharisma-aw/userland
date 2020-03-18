package com.onboarding.userland.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenDto(
        @JsonProperty("value") val value: String,
        @JsonProperty("type") val type: String,
        @JsonProperty("expired_at") val expiryDate: Long
)