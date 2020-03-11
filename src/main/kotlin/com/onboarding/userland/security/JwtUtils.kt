package com.onboarding.userland.security

import com.onboarding.userland.security.SecurityConstants.TOKEN_PREFIX
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.*

fun createToken(email: String, expirationDate: Date, key: ByteArray): String {
    return Jwts.builder()
            .setSubject(email)
            .setExpiration(expirationDate)
            .signWith(Keys.hmacShaKeyFor(key))
            .compact()
}

fun parseToken(token: String, key: ByteArray): Jws<Claims> {
    return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(key))
            .build()
            .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
}