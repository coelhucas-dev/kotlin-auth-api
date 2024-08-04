package com.lucascoelho.kotlinauthapi.services

import com.lucascoelho.kotlinauthapi.domain.user.Users
import com.lucascoelho.kotlinauthapi.domain.user_profile.UserProfile
import com.lucascoelho.kotlinauthapi.repositories.UserProfileRepository
import com.lucascoelho.kotlinauthapi.repositories.UsersRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UsersService(
    private val usersRepository: UsersRepository,
    private val userProfileRepository: UserProfileRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): User {
        val user: Users = usersRepository.findByLogin(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")

        return User(user.username, user.password, mutableListOf())
    }

   fun findUserProfileByUsername(username: String): UserProfile? {
       return this.userProfileRepository.findByUserLogin(username)
   }
}