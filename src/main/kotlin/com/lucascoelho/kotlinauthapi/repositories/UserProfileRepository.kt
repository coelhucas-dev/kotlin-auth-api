package com.lucascoelho.kotlinauthapi.repositories

import com.lucascoelho.kotlinauthapi.domain.user_profile.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserProfileRepository: JpaRepository<UserProfile, UUID> {
    @Query("SELECT up FROM UserProfile up WHERE up.user.login = :login")
    fun findByUserLogin(@Param("login") login: String): UserProfile?
}