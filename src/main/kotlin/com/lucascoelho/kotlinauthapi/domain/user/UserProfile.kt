package com.lucascoelho.kotlinauthapi.domain.user

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "user_profile")
data class UserProfile(
    @Id
    @OneToOne
    @JoinColumn(name = "user_fk", referencedColumnName = "id", nullable = false)
    val user: Users,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val updatedAt: Date,
)
