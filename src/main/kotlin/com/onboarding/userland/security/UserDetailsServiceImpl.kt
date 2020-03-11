package com.onboarding.userland.security

import com.onboarding.userland.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException


class UserDetailsServiceImpl @Autowired constructor(private val repository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = repository.findByEmail(email) ?: throw UsernameNotFoundException(email)
        return User(user.email, user.password, emptyList())
    }
}