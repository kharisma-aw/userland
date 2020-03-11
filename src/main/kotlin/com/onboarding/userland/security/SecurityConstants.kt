package com.onboarding.userland.security

object SecurityConstants {
    const val AUTH_LOGIN_URL = "/auth/login"

    // Signing key for HS256 algorithm
    // You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
    const val JWT_SECRET = "x!A%D*G-JaNdRgUkXp2s5v8y/B?E(H+M"

    const val TOKEN_HEADER = "Authorization"
    const val TOKEN_PREFIX = "Bearer "
    const val TOKEN_TYPE = "JWT"
    const val TOKEN_ISSUER = "secure-api"
    const val TOKEN_AUDIENCE = "secure-app"
}