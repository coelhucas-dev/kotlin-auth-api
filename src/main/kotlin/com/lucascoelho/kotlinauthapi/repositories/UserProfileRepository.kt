package com.lucascoelho.kotlinauthapi.repositories

import com.lucascoelho.kotlinauthapi.domain.user.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserProfileRepository: JpaRepository<UserProfile, UUID>