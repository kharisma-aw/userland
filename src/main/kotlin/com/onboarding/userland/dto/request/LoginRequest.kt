package com.onboarding.userland.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest(
        @JsonProperty("email")
        val email: String,
        @JsonProperty("password")
        val password: String
) : BaseRequest()