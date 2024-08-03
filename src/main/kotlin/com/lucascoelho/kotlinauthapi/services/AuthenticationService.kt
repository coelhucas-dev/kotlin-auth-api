package com.lucascoelho.kotlinauthapi.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.lucascoelho.kotlinauthapi.domain.user.UserRequestDTO
import com.lucascoelho.kotlinauthapi.domain.user.UserResponseDTO
import com.lucascoelho.kotlinauthapi.domain.user.Users
import com.lucascoelho.kotlinauthapi.repositories.UsersRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException

@Service
class AuthenticationService(
    private val usersRepository: UsersRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Value("spring.security.token")
    private val token: String? = null

    fun login(user: UserRequestDTO?): String {
        val foundUser = this.usersRepository.findByLogin(user?.username) ?: throw UsernameNotFoundException("User not found")

        val passwordMatched = this.passwordEncoder.matches(user?.password, foundUser.password)

        if (!passwordMatched) throw AuthenticationException("Password not matched")

        val token = JWT.create().withIssuer("auth-api")
            .withSubject(foundUser.id.toString())
            .sign(Algorithm.HMAC256(token))

        return token
    }

    fun register(user: UserRequestDTO?): Users {
        if (usersRepository.findByLogin(user!!.username) != null) throw Exception("User already exists")

        val newUser = Users(
            userPassword = passwordEncoder.encode(user.password),
            login = user.username,
        )

        return usersRepository.save(newUser)
    }

}