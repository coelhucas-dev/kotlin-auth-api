package com.lucascoelho.kotlinauthapi.services

import com.lucascoelho.kotlinauthapi.dto.LoginRequestDTO
import com.lucascoelho.kotlinauthapi.dto.RegisterRequestDTO
import com.lucascoelho.kotlinauthapi.domain.user.Users
import com.lucascoelho.kotlinauthapi.infra.security.TokenService
import com.lucascoelho.kotlinauthapi.repositories.UsersRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException

@Service
class AuthenticationService(
    private val usersRepository: UsersRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenService: TokenService
) {

    fun login(user: LoginRequestDTO?): String {
        val foundUser = this.usersRepository.findByLogin(user?.username) ?: throw UsernameNotFoundException("User not found")

        val passwordMatched = this.passwordEncoder.matches(user?.password, foundUser.password)

        if (!passwordMatched) throw AuthenticationException("Password not matched")

        val token = tokenService.generateToken(foundUser)

        return token
    }

    fun register(user: RegisterRequestDTO?): Users {
        if (usersRepository.findByLogin(user!!.username) != null) throw Exception("User already exists")

        val newUser = Users(
            userPassword = passwordEncoder.encode(user.password),
            login = user.username,
        )

        return usersRepository.save(newUser)
    }

}