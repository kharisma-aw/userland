package com.onboarding.userland.controller

import com.onboarding.userland.dto.request.BasicInfoRequest
import com.onboarding.userland.dto.request.ChangePasswordRequest
import com.onboarding.userland.dto.response.BasicInfoResponse
import com.onboarding.userland.dto.response.GeneralSuccessResponse
import com.onboarding.userland.security.SecurityConstants.TOKEN_HEADER
import com.onboarding.userland.service.JwtService
import com.onboarding.userland.service.UserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import java.security.InvalidParameterException
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/me"], produces = [APPLICATION_JSON_VALUE])
class UserDetailController @Autowired constructor(
        val userDetailService: UserDetailService,
        val jwtService: JwtService
) {
    @GetMapping
    fun getDetail(@RequestHeader(TOKEN_HEADER) token: String): BasicInfoResponse {
        return userDetailService.getUserDetail(jwtService.getEmail(token))
    }

    @PostMapping
    fun updateBasicInfo(
            @RequestHeader(TOKEN_HEADER) token: String,
            @RequestBody @Valid basicInfo: BasicInfoRequest
    ): GeneralSuccessResponse {
        return userDetailService.updateBasicInfo(jwtService.getEmail(token), basicInfo)
    }

    @PostMapping("/picture")
    fun updatePicture(
            @RequestHeader(TOKEN_HEADER) token: String,
            @RequestBody @Valid url: String
    ): GeneralSuccessResponse {
        return userDetailService.updatePicture(jwtService.getEmail(token), url)
    }

    @PostMapping("/email")
    fun updateEmail(
            @RequestHeader(TOKEN_HEADER) token: String,
            @RequestBody @Valid email: String
    ): GeneralSuccessResponse {
        return userDetailService.updateEmail(jwtService.getEmail(token), email)
    }

    @Throws(InvalidParameterException::class)
    @PostMapping("/password")
    fun changePassword(
            @RequestHeader(TOKEN_HEADER) token: String,
            @RequestBody @Valid request: ChangePasswordRequest
    ): GeneralSuccessResponse {
        return if (userDetailService.validatePassword(jwtService.getEmail(token), request.currentPassword)) {
            userDetailService.changePassword(jwtService.getEmail(token), request.password)
        } else {
            throw InvalidParameterException()
        }
    }
}