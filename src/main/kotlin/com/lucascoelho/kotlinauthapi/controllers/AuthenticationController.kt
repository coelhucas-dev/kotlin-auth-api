package com.lucascoelho.kotlinauthapi.controllers

import com.lucascoelho.kotlinauthapi.dto.LoginRequestDTO
import com.lucascoelho.kotlinauthapi.dto.RegisterRequestDTO
import com.lucascoelho.kotlinauthapi.dto.UserResponseDTO
import com.lucascoelho.kotlinauthapi.services.AuthenticationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {
    private val logger: Logger = LoggerFactory.getLogger(AuthenticationController::class.java)

    @PostMapping("/login")
    @ResponseBody
    fun login(@RequestBody user: LoginRequestDTO?): ResponseEntity<UserResponseDTO> {
        logger.info("Trying to authenticate user, username=${user?.username}")

        try {
            val token = this.authenticationService.login(user)

            return ResponseEntity.ok().body(
                UserResponseDTO(
                    message = "Successfully logged in",
                    success = true,
                    token = token
                )
            )
        } catch (err: Exception) {
            return ResponseEntity.badRequest().body(
                UserResponseDTO(
                    token = null,
                    message = err.message,
                    success = false
                )
            )
        }
    }

    @PostMapping("/register")
    @ResponseBody
    fun register(@RequestBody user: RegisterRequestDTO?): ResponseEntity<UserResponseDTO> {
        logger.info("Trying to create user, username=${user?.username}")

        try {
            val savedUser = this.authenticationService.register(user!!)
            val location = URI.create("/users/${savedUser.username}")
            val loginRequest = LoginRequestDTO(user.username, user.password)
            val token = this.authenticationService.login(loginRequest)

            return ResponseEntity.created(location).body(
                UserResponseDTO(
                    token = token,
                    message = "User created sucessfuly",
                    success = true
                )
            )
        } catch (err: Exception) {
            return ResponseEntity.badRequest().body(
                UserResponseDTO(
                    token = null,
                    message = err.message,
                    success = false
                )
            )
        }
    }
}