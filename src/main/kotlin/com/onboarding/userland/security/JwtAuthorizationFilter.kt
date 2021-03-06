package com.onboarding.userland.security

import com.onboarding.userland.security.SecurityConstants.TOKEN_PREFIX
import com.onboarding.userland.service.JwtService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthorizationFilter(
        authMngr: AuthenticationManager,
        private val jwtService: JwtService
) : BasicAuthenticationFilter(authMngr) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authentication = getAuthentication(request)
        if (authentication == null) {
            chain.doFilter(request, response)
            return
        }

        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(SecurityConstants.TOKEN_HEADER)
        if (!token.isNullOrEmpty() && token.startsWith(TOKEN_PREFIX)) {
            try {
                val parsedToken = jwtService.parseToken(token)
                val username = parsedToken.body.subject
                if (!username.isNullOrEmpty()) {
                    return UsernamePasswordAuthenticationToken(username, null, emptyList())
                }
            } catch (exception: ExpiredJwtException) {
                log.warn("Request to parse expired JWT : %s failed : %s", token, exception.message)
            } catch (exception: UnsupportedJwtException) {
                log.warn("Request to parse unsupported JWT : %s failed : %s", token, exception.message)
            } catch (exception: MalformedJwtException) {
                log.warn("Request to parse invalid JWT : %s failed : %s", token, exception.message)
            } catch (exception: SignatureException) {
                log.warn("Request to parse JWT with invalid signature : %s failed : %s", token, exception.message)
            } catch (exception: IllegalArgumentException) {
                log.warn("Request to parse empty or null JWT : %s failed : %s", token, exception.message)
            }
        }
        return null
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(JwtAuthorizationFilter::class.java)
    }
}