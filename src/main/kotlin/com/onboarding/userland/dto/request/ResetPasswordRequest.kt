package com.onboarding.userland.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ResetPasswordRequest(
        @JsonProperty("token")
        val token: String,
        @JsonProperty("password")
        val password: String,
        @JsonProperty("password_confirm")
        val passwordConfirm: String
)