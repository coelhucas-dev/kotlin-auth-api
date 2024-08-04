package com.lucascoelho.kotlinauthapi.infra.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.lucascoelho.kotlinauthapi.domain.user.Users
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Service
class TokenService {

    @Value("api.security.token")
    private val secret: String = ""

    private val algorithm: Algorithm = Algorithm.HMAC256(secret)
    private val defaultIssuer: String = "kotlin-auth-api"

    fun generateToken(user: Users): String {
        try {
            return JWT.create()
                .withIssuer("kotlin-auth-api")
                .withSubject(user.username)
                .withExpiresAt(this.generateExpirationDate())
                .sign(algorithm)
        } catch (err: JWTCreationException) {
            throw RuntimeException("Error while authenticating")
        }
    }

    fun validateToken(token: String?): String? {
        return try {
            JWT.require(algorithm)
                .withIssuer(defaultIssuer)
                .build()
                .verify(token)
                .subject
        } catch (err: JWTVerificationException) {
            null
        }
    }

    private fun generateExpirationDate(): Date {
        val instant: Instant = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"))
        return Date.from(instant)
    }
}