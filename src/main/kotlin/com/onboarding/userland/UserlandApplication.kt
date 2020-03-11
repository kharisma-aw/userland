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


@SpringBootApplication
@EnableAutoConfiguration(exclude = [SecurityAutoConfiguration::class])
class UserlandApplication {
	@Bean
	fun authService(): AuthenticationService {
		return AuthenticationService()
	}

	@Bean
	fun userDetailService(): UserDetailService {
		return UserDetailService()
	}

	@Bean
	fun userDetailsServiceImpl(repository: UserRepository): UserDetailsServiceImpl {
		return UserDetailsServiceImpl(repository)
	}
}

fun main(args: Array<String>) {
	runApplication<UserlandApplication>(*args)
}