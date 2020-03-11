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
    fun registerUser(fullname: String, email: String, password: String) {
        val hashedPassword = encoder.encode(password)
        repository.save(
                com.onboarding.userland.model.User(
                        fullname = fullname,
                        email = email,
                        password = hashedPassword,
                        createdAt = java.sql.Date.valueOf(java.time.LocalDate.now())
                )
        )
    }
}