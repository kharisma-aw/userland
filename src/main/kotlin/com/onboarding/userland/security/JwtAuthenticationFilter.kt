package com.onboarding.userland.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.onboarding.userland.dto.request.LoginRequest
import com.onboarding.userland.security.SecurityConstants.AUTH_LOGIN_URL
import com.onboarding.userland.security.SecurityConstants.CONTENT_HEADER
import com.onboarding.userland.security.SecurityConstants.TokenType
import com.onboarding.userland.service.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter(
        private val authMngr: AuthenticationManager,
        private val jwtService: JwtService
) : UsernamePasswordAuthenticationFilter() {
    init {
        setFilterProcessesUrl(AUTH_LOGIN_URL)
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val mapper = ObjectMapper()
        val loginRequest = mapper.readValue(request.inputStream, LoginRequest::class.java)
        val authenticationToken = UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
        return authMngr.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse,
                                          filterChain: FilterChain, authentication: Authentication) {
        val user = authentication.principal as User
        val tokenDto = jwtService.createTokenDto(user.username, TokenType.ACCESS_TOKEN)
        response.apply {
            addHeader(CONTENT_HEADER, "application/json")
            writer.write(ObjectMapper().writeValueAsString(tokenDto))
            flushBuffer()
        }
    }
}