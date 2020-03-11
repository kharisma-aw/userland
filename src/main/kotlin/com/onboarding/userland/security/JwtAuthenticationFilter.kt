package com.onboarding.userland.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.onboarding.userland.dto.request.LoginRequest
import com.onboarding.userland.dto.response.LoginResponse
import com.onboarding.userland.security.SecurityConstants.AUTH_LOGIN_URL
import com.onboarding.userland.security.SecurityConstants.CONTENT_HEADER
import com.onboarding.userland.security.SecurityConstants.JWT_SECRET
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter(private val authMngr: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {
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
        val expirationDate = Date(System.currentTimeMillis() + 864000000)
        val signingKey = JWT_SECRET.toByteArray()
        val token = createToken(user.username, expirationDate, signingKey)
        response.apply {
            addHeader(CONTENT_HEADER, "application/json")
            val responseBody = LoginResponse(token, expirationDate.toString())
            writer.write(ObjectMapper().writeValueAsString(responseBody))
            flushBuffer()
        }
    }
}