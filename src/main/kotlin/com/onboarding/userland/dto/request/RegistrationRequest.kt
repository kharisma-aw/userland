package com.onboarding.userland.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class RegistrationRequest(
        @JsonProperty("fullname")
        val fullname: String,
        @JsonProperty("email")
        val email: String,
        @JsonProperty("password")
        val password: String,
        @JsonProperty("password_confirm")
        val passwordConfirm: String
): BaseRequest()