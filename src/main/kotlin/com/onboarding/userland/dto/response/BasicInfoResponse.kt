package com.onboarding.userland.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class BasicInfoResponse(
        @JsonProperty("id")
        val id: Long,
        @JsonProperty("fullname")
        val fullname: String,
        @JsonProperty("location")
        val location: String?,
        @JsonProperty("bio")
        val bio: String?,
        @JsonProperty("web")
        val web: String?,
        @JsonProperty("picture")
        val picture: String?,
        @JsonProperty("created_at")
        val createdAt: String
) : BaseResponse()