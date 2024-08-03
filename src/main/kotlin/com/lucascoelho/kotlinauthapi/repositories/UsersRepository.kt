package com.lucascoelho.kotlinauthapi.repositories

import com.lucascoelho.kotlinauthapi.domain.user.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UsersRepository: JpaRepository<Users, UUID> {
    fun findByLogin(login: String?): Users?
}