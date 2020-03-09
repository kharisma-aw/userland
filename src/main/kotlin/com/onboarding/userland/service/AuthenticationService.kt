package com.onboarding.userland.service

import com.onboarding.userland.dto.request.LoginRequest
import com.onboarding.userland.dto.request.RegistrationRequest
import com.onboarding.userland.dto.response.GeneralSuccessResponse
import com.onboarding.userland.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import java.security.InvalidParameterException
import javax.persistence.EntityNotFoundException

class AuthenticationService {
    @Autowired
    lateinit var repository: UserRepository
    @Autowired
    lateinit var encoder: PasswordEncoder

    fun register(registrationRequest: RegistrationRequest): GeneralSuccessResponse {
        println("Data received: \n$registrationRequest")
        with(registrationRequest) {
            val hashedPassword = encoder.encode(password)
            val registered = repository.save(
                    com.onboarding.userland.model.User(
                            fullname = this.fullname,
                            email = this.email,
                            password = hashedPassword,
                            createdAt = java.sql.Date.valueOf(java.time.LocalDate.now())
                    )
            )
            println("Registered with user id: ${registered.id}\n" +
                    "Detail:\n$registered")
        }
        return GeneralSuccessResponse
    }

    fun login(loginRequest: LoginRequest): GeneralSuccessResponse {
        val user = repository.findByEmail(loginRequest.email) ?: throw EntityNotFoundException()
        if (encoder.matches(loginRequest.password, user.password)) {
            return GeneralSuccessResponse
        } else {
            throw InvalidParameterException()
        }
    }
}