package com.lucascoelho.kotlinauthapi.domain.user

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
data class Users(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: UUID,
    val username: String,
    val password: String,
    val lastLogin: Date,
    val createdAt: Date,
    val deletedAt: Date?
)
