package com.onboarding.userland.controller

import com.onboarding.userland.dto.request.LoginRequest
import com.onboarding.userland.dto.request.RegistrationRequest
import com.onboarding.userland.dto.response.GeneralSuccessResponse
import com.onboarding.userland.service.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.InvalidParameterException
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/auth"], produces = [APPLICATION_JSON_VALUE])
class AuthenticationController @Autowired constructor(private val service: AuthenticationService) {
    @PostMapping(value = ["/register"], consumes = [APPLICATION_JSON_VALUE])
    fun registerUser(@RequestBody @Valid registrationRequest: RegistrationRequest): GeneralSuccessResponse {
        return with(registrationRequest) {
            if (password == passwordConfirm) {
                service.register(registrationRequest)
            } else throw InvalidParameterException()
        }
    }

    @PostMapping(value = ["/login"], consumes = [APPLICATION_JSON_VALUE])
    fun login(@RequestBody @Valid loginRequest: LoginRequest): GeneralSuccessResponse {
        return service.login(loginRequest)
    }

//    @PostMapping(value = ["/password/forgot"], consumes = [APPLICATION_JSON_VALUE])
//    fun forgotPassword(email: String): GeneralSuccessResponse {
//        return GeneralSuccessResponse
//    }
}