package com.onboarding.userland

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession


@SpringBootApplication
@EnableJdbcHttpSession
class UserlandApplication

fun main(args: Array<String>) {
	runApplication<UserlandApplication>(*args)
}