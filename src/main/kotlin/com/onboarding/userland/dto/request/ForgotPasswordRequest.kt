package com.onboarding.userland.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ForgotPasswordRequest(
        @JsonProperty("email")
        val email: String
)