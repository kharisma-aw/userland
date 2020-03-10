package com.onboarding.userland.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class BasicInfoRequest(
        @JsonProperty("fullname")
        val fullname: String,
        @JsonProperty("location")
        val location: String?,
        @JsonProperty("bio")
        val bio: String?,
        @JsonProperty("web")
        val web: String?
)