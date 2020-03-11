package com.onboarding.userland.service

import com.onboarding.userland.dto.request.RegistrationRequest
import com.onboarding.userland.dto.response.GeneralSuccessResponse
import com.onboarding.userland.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

class AuthenticationService @Autowired constructor(
        private val repository: UserRepository,
        private val encoder: PasswordEncoder
) {
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
}