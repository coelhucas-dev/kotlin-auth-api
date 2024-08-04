package com.lucascoelho.kotlinauthapi.infra.security

import com.lucascoelho.kotlinauthapi.domain.user.Users
import com.lucascoelho.kotlinauthapi.repositories.UsersRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter(val tokenService: TokenService, val usersRepository: UsersRepository): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = this.recoverToken(request)
        val login = tokenService.validateToken(token)

        if (login != null) {
            val user: Users? = usersRepository.findByLogin(login)
            val authentication = UsernamePasswordAuthenticationToken(user?.username, user?.password, user?.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun recoverToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")
        return authHeader?.removePrefix("Bearer ")
    }

}