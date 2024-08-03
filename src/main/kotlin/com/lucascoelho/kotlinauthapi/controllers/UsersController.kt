package com.lucascoelho.kotlinauthapi.controllers

import com.lucascoelho.kotlinauthapi.domain.user_profile.UserProfile
import com.lucascoelho.kotlinauthapi.services.UsersService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UsersController(
    private val usersService: UsersService
) {

    @GetMapping("/profile/{username}")
    fun getUserByUsername(@PathVariable username: String): ResponseEntity<UserProfile> {
        val userProfile = usersService.findUserProfileByUsername(username)
        return ResponseEntity.ok().body(userProfile)
    }
}