package com.onboarding.userland.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.onboarding.userland.dto.request.LoginRequest
import com.onboarding.userland.security.SecurityConstants.AUTH_LOGIN_URL
import com.onboarding.userland.security.SecurityConstants.JWT_SECRET
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
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
//        val signingKey = Base64.getDecoder().decode(JWT_SECRET)
        val signingKey = JWT_SECRET.toByteArray()
        val token = Jwts.builder()
                .setSubject(user.username)
                .setExpiration(Date(System.currentTimeMillis() + 864000000))
                .signWith(Keys.hmacShaKeyFor(signingKey))
                .compact()
        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token)
    }
}