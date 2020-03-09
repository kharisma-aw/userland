package com.onboarding.userland

import com.onboarding.userland.service.AuthenticationService
import com.onboarding.userland.service.UserDetailService
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@SpringBootApplication
@EnableAutoConfiguration(exclude = [SecurityAutoConfiguration::class])
class UserlandApplication {
	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}

	@Bean
	fun authService(): AuthenticationService {
		return AuthenticationService()
	}

	@Bean
	fun userDetailService(): UserDetailService {
		return UserDetailService()
	}
}

fun main(args: Array<String>) {
	runApplication<UserlandApplication>(*args)
}