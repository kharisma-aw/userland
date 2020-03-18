package com.onboarding.userland.service

import com.onboarding.userland.dto.TokenDto
import com.onboarding.userland.security.SecurityConstants.TokenType
import com.onboarding.userland.security.SecurityConstants.TOKEN_PREFIX
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtService @Autowired constructor(env: Environment) {
    private val secret: String = env.getProperty("jwt.secret.key")!!

    fun createToken(email: String, expiryDate: Date, type: String): String {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expiryDate)
                .claim(CLAIM_TOKEN_TYPE, type)
                .signWith(Keys.hmacShaKeyFor(secret.toByteArray()))
                .compact()
    }

    fun createTokenDto(email: String, tokenType: TokenType): TokenDto {
        val expiryDate = Date(System.currentTimeMillis() + tokenType.expiryDate)
        val token = createToken(email, expiryDate, tokenType.name)
        return TokenDto(token, tokenType.name, expiryDate.time)
    }

    fun parseToken(token: String): Jws<Claims> {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.toByteArray()))
                .build()
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
    }

    fun getEmail(token: String): String {
        val parsedToken = parseToken(token)
        return parsedToken.body.subject
    }

    fun getExpiryDate(token: String): Long {
        val parsedToken = parseToken(token)
        return parsedToken.body.expiration.time
    }

    fun isRefreshToken(token: String): Boolean {
        val parsedToken = parseToken(token)
        return parsedToken.body[CLAIM_TOKEN_TYPE]!! == TokenType.REFRESH_TOKEN.name
    }

    companion object {
        private const val CLAIM_TOKEN_TYPE = "type"
    }
}