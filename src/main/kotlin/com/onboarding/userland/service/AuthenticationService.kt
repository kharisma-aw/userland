package com.onboarding.userland.service

import com.onboarding.userland.model.User
import com.onboarding.userland.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.sql.Date
import java.time.LocalDate

@Component
class AuthenticationService @Autowired constructor(
        private val repository: UserRepository,
        private val encoder: PasswordEncoder
) {
    fun registerUser(fullname: String, email: String, password: String) {
        val hashedPassword = encoder.encode(password)
        repository.save(
                User(
                        fullname = fullname,
                        email = email,
                        password = hashedPassword,
                        createdAt = Date.valueOf(LocalDate.now())
                )
        )
    }

    fun updatePassword(email: String, password: String) {
        val hashedPassword = encoder.encode(password)
        repository.updatePassword(email, hashedPassword)
    }
}