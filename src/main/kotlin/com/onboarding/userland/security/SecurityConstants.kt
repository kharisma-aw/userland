package com.onboarding.userland.security

object SecurityConstants {
    const val AUTH_LOGIN_URL = "/auth/login"

    const val CONTENT_HEADER = "Content-Type"
    const val TOKEN_HEADER = "Authorization"
    const val TOKEN_PREFIX = "Bearer "

    enum class TokenType(val expiryDate: Long){
        ACCESS_TOKEN(259200000),
        REFRESH_TOKEN(864000000),
        RESET_PASSWORD_TOKEN(300000)
    }
}