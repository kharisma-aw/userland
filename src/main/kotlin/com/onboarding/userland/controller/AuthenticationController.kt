package com.onboarding.userland.controller

import com.onboarding.userland.dto.request.ForgotPasswordRequest
import com.onboarding.userland.dto.request.RegistrationRequest
import com.onboarding.userland.dto.request.ResetPasswordRequest
import com.onboarding.userland.dto.response.GeneralSuccessResponse
import com.onboarding.userland.security.SecurityConstants.TokenType
import com.onboarding.userland.service.AuthenticationService
import com.onboarding.userland.service.EmailService
import com.onboarding.userland.service.EmailService.Companion.RESET_PASSWORD_MAIL_TEMPLATE
import com.onboarding.userland.service.EmailService.Companion.SUBJECT_FORGOT_PASSWORD
import com.onboarding.userland.service.JwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.InvalidParameterException
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/auth"], produces = [APPLICATION_JSON_VALUE])
class AuthenticationController @Autowired constructor(
        private val authService: AuthenticationService,
        private val mailService: EmailService,
        private val jwtService: JwtService
) {
    @PostMapping(value = ["/register"], consumes = [APPLICATION_JSON_VALUE])
    fun registerUser(@RequestBody @Valid registrationRequest: RegistrationRequest): GeneralSuccessResponse {
        with(registrationRequest) {
            if (password == passwordConfirm) {
                authService.registerUser(fullname, email, password)
            } else throw InvalidParameterException()
        }
        return GeneralSuccessResponse
    }

    @PostMapping(value = ["/password/forgot"], consumes = [APPLICATION_JSON_VALUE])
    fun forgotPassword(@RequestBody @Valid request: ForgotPasswordRequest): GeneralSuccessResponse {
        val expiryDate = Date(System.currentTimeMillis() + TokenType.RESET_PASSWORD_TOKEN.expiryDate)
        val token = jwtService.createToken(request.email, expiryDate, TokenType.RESET_PASSWORD_TOKEN.name)
        val mailBody = String.format(RESET_PASSWORD_MAIL_TEMPLATE, token)
        mailService.sendMail(request.email, SUBJECT_FORGOT_PASSWORD, mailBody)
        return GeneralSuccessResponse
    }

    @Throws(InvalidParameterException::class)
    @PostMapping(value = ["/password/reset"], consumes = [APPLICATION_JSON_VALUE])
    fun resetPassword(@RequestBody @Valid request: ResetPasswordRequest): GeneralSuccessResponse {
        if (request.password != request.passwordConfirm) throw InvalidParameterException()

        val now = System.currentTimeMillis()
        if (jwtService.getExpiryDate(request.token) < now) throw InvalidParameterException()

        authService.updatePassword(jwtService.getEmail(request.token), request.password)
        return GeneralSuccessResponse
    }
}