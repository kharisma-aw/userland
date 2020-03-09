package com.onboarding.userland.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

open class BaseResponse

object GeneralSuccessResponse : BaseResponse() {
    @JsonProperty("success") val success = true
}