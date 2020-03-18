package com.onboarding.userland.controller

import com.onboarding.userland.dto.TokenDto
import com.onboarding.userland.security.SecurityConstants.TOKEN_HEADER
import com.onboarding.userland.security.SecurityConstants.TokenType
import com.onboarding.userland.service.JwtService
import com.onboarding.userland.service.UserSessionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.InvalidParameterException

@RestController
@RequestMapping(value = ["/me/session"], produces = [MediaType.APPLICATION_JSON_VALUE])
class UserSessionController @Autowired constructor(
        private val sessionService: UserSessionService,
        private val jwtService: JwtService
) {
    @Throws(InvalidParameterException::class)
    @GetMapping(value = ["/access_token"])
    fun getAccessToken(@RequestHeader(TOKEN_HEADER) token: String): ResponseEntity<TokenDto> {
        if (jwtService.isRefreshToken(token)) {
            val email = jwtService.getEmail(token)
            val tokenDto = jwtService.createTokenDto(email,TokenType.ACCESS_TOKEN)
            return ResponseEntity(tokenDto, HttpStatus.OK)
        } else {
            throw InvalidParameterException()
        }

    }

    @GetMapping(value = ["/refresh_token"])
    fun getRefreshToken(@RequestHeader(TOKEN_HEADER) token: String): ResponseEntity<TokenDto> {
        val email = jwtService.getEmail(token)
        val tokenDto = jwtService.createTokenDto(email,TokenType.REFRESH_TOKEN)
        return ResponseEntity(tokenDto, HttpStatus.OK)
    }
}