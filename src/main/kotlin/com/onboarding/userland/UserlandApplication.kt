package com.onboarding.userland

import com.onboarding.userland.repository.UserRepository
import com.onboarding.userland.security.UserDetailsServiceImpl
import com.onboarding.userland.service.AuthenticationService
import com.onboarding.userland.service.UserDetailService
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder


@SpringBootApplication
@EnableAutoConfiguration(exclude = [SecurityAutoConfiguration::class])
class UserlandApplication {
	@Bean
	fun authService(repository: UserRepository, encoder: PasswordEncoder): AuthenticationService {
		return AuthenticationService(repository, encoder)
	}

	@Bean
	fun userDetailService(repository: UserRepository): UserDetailService {
		return UserDetailService(repository)
	}

	@Bean
	fun userDetailsServiceImpl(repository: UserRepository): UserDetailsServiceImpl {
		return UserDetailsServiceImpl(repository)
	}
}

fun main(args: Array<String>) {
	runApplication<UserlandApplication>(*args)
}