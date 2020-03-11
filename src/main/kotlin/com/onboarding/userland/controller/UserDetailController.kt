package com.onboarding.userland.controller

import com.onboarding.userland.dto.request.BasicInfoRequest
import com.onboarding.userland.dto.response.BasicInfoResponse
import com.onboarding.userland.dto.response.GeneralSuccessResponse
import com.onboarding.userland.security.SecurityConstants.JWT_SECRET
import com.onboarding.userland.security.SecurityConstants.TOKEN_HEADER
import com.onboarding.userland.security.parseToken
import com.onboarding.userland.service.UserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/me"], produces = [APPLICATION_JSON_VALUE])
class UserDetailController @Autowired constructor(val service: UserDetailService) {
    @GetMapping
    fun getDetail(@RequestHeader(TOKEN_HEADER) token: String): BasicInfoResponse {
        return service.getUserDetail(getEmail(token))
    }

    @PostMapping
    fun updateBasicInfo(
            @RequestHeader(TOKEN_HEADER) token: String,
            @RequestBody @Valid basicInfo: BasicInfoRequest
    ): GeneralSuccessResponse {
        return service.updateBasicInfo(getEmail(token), basicInfo)
    }

    @PostMapping("/picture")
    fun updatePicture(
            @RequestHeader(TOKEN_HEADER) token: String,
            @RequestBody @Valid url: String
    ): GeneralSuccessResponse {
        return service.updatePicture(getEmail(token), url)
    }

    @PostMapping("/email")
    fun updateEmail(
            @RequestHeader(TOKEN_HEADER) token: String,
            @RequestBody @Valid email: String
    ): GeneralSuccessResponse {
        return service.updateEmail(getEmail(token), email)
    }

    private fun getEmail(token: String): String {
        val parsedToken = parseToken(token, JWT_SECRET.toByteArray())
        return parsedToken.body.subject
    }
}