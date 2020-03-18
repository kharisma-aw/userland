package com.onboarding.userland.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ChangePasswordRequest(
        @JsonProperty("password_current") val currentPassword: String,
        @JsonProperty("password") val password: String,
        @JsonProperty("password_confirm") val passwordConfirm: String
) : BaseRequest()